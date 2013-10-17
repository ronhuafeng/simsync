(ns simsync.core
  (use simsync.others))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))



(defn make-basic-block
  []
  { :type 'basic-block
    :name "sync-b1"
    :processes [p1 p2]
    :environment env
    })

(defn make-process
  []
  { :type 'process
    :name "sync-p1"
    :environment env
    :transitions []})

(defn make-transition
  []
  { :type 'transition
    :name "t1"
    :guard nil
    :action nil
    :source nil
    :target nil
    :env nil})

(defn make-port
  []
  { :type ['input-port 'output-port 'relay-port]
    :name "port-1"
    :source nil ;; output-port of a basic-block does not have a source, so this attribute is nil.
    :env nil
    })

(defn get-input
  [port]
  (case (:type port)
    (input-port, relay-port)
    (get-input (:source port))
    output-port
    (let [get-value ((:env port) 'get)]
      (get-value
        (:name port)))))

(defn make-connection
  []
  { :type 'connection
    :name "connection-1"
    :source nil
    :target nil})

(defn transition-enable?
  [t]
  (if (true?
        (compute!
          (:guard t)
          (:env t)))
    true
    false))

(defn get-enabled-transition
  [process]
  (let [transitions         (:transitions process)
        enabled-transitions (filter
                              #(transition-enable? t)
                              transitions)]
    (if (empty? enabled-transitions)
      nil
      (top-priority
        enabled-transitions
        (get-priority-table process)))))

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
      (fire-transition! t))))

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
          (:name p)
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







