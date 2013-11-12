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
              ^:static [ printBlock
                         [clojure.lang.PersistentArrayMap]
                         void]]))
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
(defn -printBlock
  [block]
  (print-block! block))
