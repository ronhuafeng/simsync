(ns simsync.core
  (use simsync.parser)
  (use simsync.util))

(defn get-env
  [variable-map]
  (set-environment
    (atom variable-map)))

(defn get-snapshot
  [block]
  (case (:type block)
    block
    {
      :sub-blocks (vec (map get-snapshot
                         (:sub-blocks block)))
      }
    basic-block
    {
      :env-value (((:env block) 'value))
      :processes (vec (map (fn [p]
                             @(:current-place p))
                        (:processes block)))
      }))

(defn restore-snapshot!
  [block snapshot]
  (case (:type block)
    block
    #_(doseq [b (:sub-blocks block)
              b-snapshot (:sub-blocks snapshot)]
        (restore-snapshot! b b-snapshot))
    (doall
      (map
        #(restore-snapshot! %1 %2)
        (:sub-blocks block)
        (:sub-blocks snapshot)))
    basic-block
    (do
      #_(doseq [p (:processes block)
                p-current-place (:processes snapshot)]
          (reset! (:current-place p)
            p-current-place))
        (doall
          (map
            #(reset! (:current-place %1) %2)
            (:processes block)
            (:processes snapshot)))
        (((:env block) 'reset) (:env-value snapshot)))))

(defn make-block
  [block-name input-ports output-ports block-list]
  { :type 'block
    :name block-name
    :input-ports input-ports
    :output-ports output-ports
    :sub-blocks block-list
    :rst-port (atom nil)}
  )

(defn make-basic-block
  [block-name input-ports output-ports process-list env]
  (let [result { :type 'basic-block
                 :name block-name
                 :input-ports input-ports
                 :output-ports output-ports
                 :processes process-list
                 :env env
                 :rst-port (atom nil)
                 }]
    (do
      (doseq [port (:input-ports result)]
        (reset! (:env port) env))
      (doseq [port (:output-ports result)]
        (reset! (:env port) env))

      (assoc
        result
        :init-state                   ;; to memorize the initial state of basic block
        (get-snapshot
          result)))))

(defn make-process
  [process-name places init-place transitions priorities env]
  { :type 'process
    :name process-name
    :env env
    :priorities priorities
    :transitions (map
                   #(assoc % :env env)
                   transitions)
    :places places
    :current-place (atom init-place)})

(defn make-transition
  [transition-name source target guard action]
  { :type 'transition
    :name transition-name
    :guard (build-AST (str guard ";"))
    :action (build-AST action)
    :source source
    :target target})

(defn make-port
  [port-name port-type source env]
  { :type (case port-type
            "input-port"  'input-port
            "output-port" 'output-port
            "relay-port"  'relay-port)
    :name port-name
    :source (atom source) ;; output-port of a basic-block does not have a source, so this attribute is nil.
    :env (atom env)
    :action (atom "")})

(defn set-port-action!
  [port action]
  (reset! (:action port) action))

(defn make-place
  [place-name]
  { :type 'place
    :name place-name})

(defn get-input
  [port]
  (case (:type port)
    (input-port, relay-port)
    (let [source @(:source port)]
      (if (nil? source)
        nil
        (let [raw-source-value (get-input source)
              tmp-env (get-env
                        {(keyword (:name source)) raw-source-value})

              tmp-ast (build-AST @(:action port))

              result (compute!
                       tmp-ast
                       tmp-env)]
          ;; get data converted by connection action
          (if (nil? result)
            raw-source-value
            result) )))
    output-port
    (let [get-value (@(:env port) 'get)]
      (get-value
        (keyword (:name port))))
    nil))


(defn transition-enable?
  [t]
  (if (true?
        (compute!
          (:guard t)
          (:env t)))
    true
    false))

#_(defn get-priority-table
  [process]
  {:pre [(= (:type process) 'process)]}
  (compute-priority-table
    (:priorities process)))

(defn get-enabled-transition
  [process]
  (let [possible-transitions (filter
                               #(= (:source %) @(:current-place process))
                               (:transitions process))
        enabled-transitions  (filter
                               #(transition-enable? %)
                               possible-transitions)]
    (if (empty? enabled-transitions)
      nil
      (first enabled-transitions)
      #_(first
        (top-priorities
          enabled-transitions
          (get-priority-table process))))))

(defn fire-transition!
  [t]
  "Should catch written-a-variable-twice-exception"
  (compute!
    (:action t)
    (:env t)))

(defn processes-advance!
  [basic-block]
  {:pre [(= 'basic-block (:type basic-block))]}
  (doseq [p (:processes basic-block)]
    (if-let [t (get-enabled-transition p)]
      (do
        (fire-transition! t)
        (reset!
          (:current-place p)
          (:target t))))))

(defn update-inputs!
  [block]
  (case (:type block)
    block
    (doseq [b (:sub-blocks block)]
      (update-inputs! b))
    basic-block
    ;; p is an input port of basic-block
    ;; get-input will trace the source of p, and get a value from that source-port
    (doseq [p (:input-ports block)]
      (let [ set-value! ((:env block) 'set)
             value (get-input p)]
        (set-value!
          (keyword (:name p))
          value)))))


(defn reset-block!
  [block]
  {:pre [(or
           (= 'block (:type block))
           (= 'basic-block (:type block)))]}
  (case (:type block)
    block
    (doseq [b (:sub-blocks block)]
      (reset-block! b))
    basic-block
    (restore-snapshot!
      block
      (:init-state block))))

(defn tick-block!
  [block]
  {:pre [(or
           (= 'block (:type block))
           (= 'basic-block (:type block)))]}
  (case (:type block)
    block
    (if (= 1
          (get-input @(:rst-port block)))
      (reset-block! block)
      (doseq [b (:sub-blocks block)]
        (tick-block! b)))

    basic-block
    ;; basic block should check clk port first
    (if (= 1
          (get-input @(:rst-port block)))
      (reset-block! block)
      (processes-advance! block))))

(defn top-cycle!
  [top-block]
  (do
    (update-inputs! top-block)
    (tick-block! top-block)))

(defn print-block!
  [block]
  (case (:type block)
    block
    (doseq [b (:sub-blocks block)]
      (println (:name block))
      (print-block! b)
      (println))
    basic-block
    (do
      (println (:name block) "\t->>")
      (doseq [p (:output-ports block)]
        (print (:name p) " :\t" (get-input p))))))


(defn get-current-places
  [block]
  {:pre [(or
           (= 'block (:type block))
           (= 'basic-block (:type block)))]}
  (case (:type block)
    block
    (reduce into
      []
      (map
        #(get-current-places %)
        (:sub-blocks block)))
    basic-block
    (vec
      (map
        #(deref (:current-place %))
        (:processes block)))))



