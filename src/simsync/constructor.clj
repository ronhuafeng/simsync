(ns simsync.constructor
  (use simsync.core)
  (:gen-class
   :methods [
              ^:static [ getEnvironment
                         [clojure.lang.PersistentArrayMap]
                         Object]
              ^:static [ makeBlock
                         [String clojure.lang.PersistentVector clojure.lang.PersistentVector clojure.lang.PersistentVector]
                         clojure.lang.PersistentArrayMap]
              ^:static [ makeBasicBlock
                         [String clojure.lang.PersistentVector clojure.lang.PersistentVector clojure.lang.PersistentVector Object]
                         clojure.lang.PersistentArrayMap]
              ^:static [ makeProcess
                         [String
                          clojure.lang.PersistentVector ;; places
                          clojure.lang.PersistentArrayMap ;; init place
                          clojure.lang.PersistentVector ;; transitions
                          clojure.lang.PersistentVector ;; priorities
                          Object ;; env
                          ]
                         clojure.lang.PersistentArrayMap]
              ^:static [ makeTransition
                         [String
                          clojure.lang.PersistentArrayMap ;; source
                          clojure.lang.PersistentArrayMap ;; target
                          String ;; guard
                          String ;; action
                          ]
                         clojure.lang.PersistentArrayMap]
              ^:static [ makePort
                         [String
                          String ;; port-type
                          clojure.lang.PersistentArrayMap ;; source
                          Object ;; env
                          ]
                         clojure.lang.PersistentArrayMap]
              ^:static [ makePlace
                         [String]
                         clojure.lang.PersistentArrayMap]
              ^:static [ cycleBlock
                         [clojure.lang.PersistentArrayMap]
                         void]
              ^:static [ toKeyword
                         [String]
                         clojure.lang.Keyword]
              ^:static [ getSnapshot
                         [clojure.lang.PersistentArrayMap]
                         clojure.lang.PersistentArrayMap]
              ^:static [ restoreSnapshot
                         [clojure.lang.PersistentArrayMap clojure.lang.PersistentArrayMap]
                         void]
              ^:static [ printBlock
                         [clojure.lang.PersistentArrayMap]
                         void]
              ^:static [ getBasicBlockOutputs
                         [clojure.lang.PersistentArrayMap]
                         clojure.lang.LazySeq]
              ^:static [ getBasicBlockInputs
                         [clojure.lang.PersistentArrayMap]
                         clojure.lang.LazySeq]
              ^:static [ getBasicBlockVariables
                         [clojure.lang.PersistentArrayMap]
                         clojure.lang.LazySeq]
              ^:static [ setBasickBlockEnvValue
                         [clojure.lang.PersistentArrayMap String Object]
                         void]
              ^:static [ getBasickBlockEnvValue
                         [clojure.lang.PersistentArrayMap String]
                         clojure.lang.PersistentArrayMap]]))
;; [block-name input-ports output-ports block-list]

(defn -makeBlock
  [block-name input-ports output-ports block-list]
  (make-block block-name input-ports output-ports block-list))
(defn -makeBasicBlock
  [block-name input-ports output-ports process-list env]
  (make-basic-block block-name input-ports output-ports process-list env))
(defn -makeProcess
  [process-name places init-place transitions priorities env]
  (make-process process-name places init-place transitions priorities env))
(defn -makeTransition
  [transition-name source target guard action]
  (make-transition transition-name source target guard action))
(defn -makePort
  [port-name port-type source env]
  (make-port port-name port-type source env))
(defn -makePlace
  [place-name]
  (make-place place-name))
(defn -cycleBlock
  [block]
  (top-cycle! block))
(defn -toKeyword
  [attr]
  (keyword attr))
(defn -getEnvironment
  [value-map]
  (get-env value-map)
  )

(defn -getSnapshot
  [block]
  (get-snapshot block))
(defn -restoreSnapshot
  [block snapshot]
  (restore-snapshot! block snapshot))

(defn -printBlock
  [block]
  (print-block! block))
(defn -getBasicBlockOutputs
  [block]
  {:pre [(= 'basic-block (:type block))]}
  (map
    (fn [p]
      {
       "name" (:name p)
       "type" (cond
                (number? (get-input p))
                "int"
                (instance? Boolean (get-input))
                "bool")
       "value" (str (get-input p))
        })
    (:output-ports block)))
(defn -getBasicBlockInputs
  [block]
  {:pre [(= 'basic-block (:type block))]}
  (map
    (fn [p]
      {
        "name" (:name p)
        "type" (cond
                 (number? (get-input p))
                 "int"
                 (instance? Boolean (get-input p))
                 "bool")
        "value" (str (get-input p))
        })
    (:input-ports block)))

(defn -getBasicBlockVariables
  [block]
  {:pre [(= 'basic-block (:type block))]}
  (let [env (:env block)
        value (env 'value)]
    (map
      (fn [k, v]
        {
          "name" (name k)
          "type" (cond
                   (number? v)
                   "int"
                   (instance? Boolean v)
                   "bool")
          "value" (str v)
          })
      (keys (value))
      (vals (value)))))

(defn -setBasickBlockEnvValue
  [block port-name port-value]
  {:pre [(= 'basic-block (:type block))]}
  (let [env (:env block)
        setter (env 'set)]
    (setter (keyword port-name) port-value)))
(defn -getBasickBlockEnvValue
  [block port-name]
  {:pre [(= 'basic-block (:type block))]}
  (let [env (:env block)
        getter (env 'get)
        v (getter (keyword port-name))]
    {
      "name" port-name
      "type" (cond
               (number? v)
               "int"
               (instance? Boolean v)
               "bool")
      "value" (str v)
      }))

