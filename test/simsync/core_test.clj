(ns simsync.core-test
  (:require [clojure.test :refer :all]
            [simsync.core :refer :all]))

(deftest simple-test
  (testing "simple test, mock an object."
    (let [ basic-block1-env (get-env
                              {:I1 0
                               :O1 2})
           simulation-env (get-env
                            {:S1 5})
           S1 (make-port "S1" "output-port" nil simulation-env)
           I1 (make-port "I1" "input-port" S1 basic-block1-env)
           O1 (make-port "O1" "output-port" nil basic-block1-env)

           Init (make-place "Init")
           Run (make-place "Run")

           T1 (make-transition "T1" Init Run "true" "O1=I1+1;")
           T2 (make-transition "T2" Run Init "true" "O1=I1-1;")

           Process1 (make-process "Process1" [Init Run] Init [T1 T2] [] basic-block1-env)

           Basic-block1 (make-basic-block "Basic-block1" [I1] [O1] [Process1] basic-block1-env)]
      (do
        (is (= 5 (get-input S1)))
        (is (= 5 (get-input I1)))
        (is (= 2 (get-input O1)))

        (is (= 0 ((basic-block1-env 'get) :I1)))
        (update-inputs! Basic-block1)
        (is (= 5 ((basic-block1-env 'get) :I1)))

        (is (= Init @(:current-place Process1)))
        (tick-block! Basic-block1)
        (is (= Run @(:current-place Process1)))

        (is (= 6 (get-input O1)))))))
