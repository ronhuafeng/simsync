(ns simsync.util
  (use simsync.parser))

(defn make-priority
  [low high guard-string]
  { :low low
    :high high
    :guard? (build-AST (str guard-string ";"))})

(defn compute-priority-table
  [priority-list]
  "Assume all guards to be true, i.e. the priority-list is a filtered result of guards."
  (let [ get-table-item (fn [table x y]
                          (get
                            (get table x)
                            y))
         assoc-table-item (fn [table x y value]
                            (assoc
                              table
                              x
                              (assoc
                                (get table x)
                                y
                                value)))
         init-table (reduce
                      (fn [table priority-item]
                        (assoc-table-item
                          table
                          (:low priority-item)
                          (:high priority-item)
                          true))
                      {}
                      priority-list)
         index-set (set (into
                          (map :low priority-list)
                          (map :high priority-list)))
         ;; build a lazy seq to serve reduce method.
         update-list (for [ m index-set
                            x index-set]
                       [m x])]


    ;; algorithm: Improved-Transitive-Closure (A)
    ;;
    ;; input: A
    ;; output: updated A
    ;;
    ;; begin
    ;;   for m := 1 to n do
    ;;     for x := 1 to n do
    ;;       if A[x, m] then
    ;;         for y := 1 to n do
    ;;           if A[m, y] then A[x, y] := true
    ;; end
    (reduce
      (fn [table update-item]
        (let [[m x] update-item]
          (if (true?
                (get-table-item table x m))
            (reduce
              (fn [t y]
                (if (true?
                      (get-table-item t m y))
                  (assoc-table-item t x y true)
                  t))
              table
              index-set)
            table)))
      init-table
      update-list)))

(defn top-priorities
  [candidates priority-table]
  (let [get-table-item (fn [table x y]
                         (get
                           (get table x)
                           y))]
    (if (empty? priority-table)
      candidates
      (filter
        (fn [x]
          (not
            (some
              (fn [y]
                (true?
                  (get-table-item priority-table x y)))
              candidates)))
        candidates))))