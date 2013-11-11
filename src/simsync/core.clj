(ns simsync.core
  (use simsync.parser)
  (use simsync.util))

(defn get-env
  [variable-map]
  (set-environment
    (atom variable-map)))

(defn make-block
  [block-name input-ports output-ports block-list]
  { :type 'block
    :name block-name
    :input-ports input-ports
    :output-ports output-ports
    :sub-blocks block-list}
  )

(defn make-basic-block
  [block-name input-ports output-ports process-list env]
  (let [result { :type 'basic-block
                 :name block-name
                 :input-ports input-ports
                 :output-ports output-ports
                 :processes process-list
                 :env env
                 }]
    (do
      (doseq [port (:input-ports result)]
        (reset! (:env port) env))
      (doseq [port (:output-ports result)]
        (reset! (:env port) env))

      result)))

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
    :env (atom env)})
(defn make-place
  [place-name]
  { :type 'place
    :name place-name})

(defn get-input
  [port]
  (case (:type port)
    (input-port, relay-port)
    (get-input @(:source port))
    output-port
    (let [get-value (@(:env port) 'get)]
      (get-value
        (keyword (:name port))))))


(defn transition-enable?
  [t]
  (if (true?
        (compute!
          (:guard t)
          (:env t)))
    true
    false))

(defn get-priority-table
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
      (first
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

(defn tick-block!
  [block]
  (case (:type block)
    block
    (doseq [b (:sub-blocks block)]
      (tick-block! b))

    basic-block
    (processes-advance! block)))

(defn top-cycle!
  [top-block]
  {:pre [(not
           (some nil?
             (map get-input
               (:input-ports top-block))))]}
  (do
    (update-inputs! top-block)
    (tick-block! top-block)))



(defn get-snapshot
  [block]
  (case (:type block)
    block
    {
     :sub-blocks (map get-snapshot
                   (:sub-blocks block))
      }
    basic-block
    {
      :env-value ((:env block) 'value)
      :processes (map (fn [p]
                              @(:current-place p))
                         (:processes block))
      }))

(defn restore-snapshot!
  [block snapshot]
  (case (:type block)
    block
    (doseq [b (:sub-blocks block)
            b-snapshot (:sub-blocks snapshot)]
      (restore-snapshot! b b-snapshot))
    basic-block
    (do
      (doseq [p (:processes block)
              p-current-place (:processes snapshot)]
        (reset! (:current-place p)
          p-current-place))
      (((:env block) 'reset) (:env-value snapshot)))))





