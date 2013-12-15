(ns simsync.core-test
  (:require [clojure.test :refer :all]
            [simsync.core :refer :all]
            [simsync.parser :refer :all]))

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

(deftest simple-test-2-process
  (testing "simple test, mock an object."
    (let [ basic-block1-env (get-env
                              {:I1 0
                               :O1 2})
           simulation-env (get-env
                            {:S1 5})
           S1 (make-port "S1" "output-port" nil simulation-env)
           I1 (make-port "I1" "input-port" S1 basic-block1-env)
           O1 (make-port "O1" "output-port" nil basic-block1-env)
           I2 (make-port "I2" "input-port" S1 basic-block1-env)
           O2 (make-port "O2" "output-port" nil basic-block1-env)

           Init (make-place "Init")
           Run (make-place "Run")
           Init2 (make-place "Init2")
           Run2 (make-place "Run2")

           T1 (make-transition "T1" Init Run "I2>0" "O1=I1+1;")
           T2 (make-transition "T2" Run Init "true" "O1=I1-1;")
           T3 (make-transition "T3" Init2 Run2 "true" "O2=I2+3;")
           T4 (make-transition "T4" Run2 Init2 "true" "O2=I2-3;")


           Process1 (make-process "Process1" [Init Run] Init [T1 T2] [] basic-block1-env)
           Process2 (make-process "Process2" [Init2 Run2] Init2 [T3 T4] [] basic-block1-env)

           Basic-block1 (make-basic-block "Basic-block1" [I1 I2] [O1 O2] [Process1 Process2] basic-block1-env)]
      (do
        (is (= 5 (get-input S1)))
        (is (= 5 (get-input I1)))
        (is (= 2 (get-input O1)))

        (is (= 0 ((basic-block1-env 'get) :I1)))
        (update-inputs! Basic-block1)
        (is (= 5 ((basic-block1-env 'get) :I1)))

        (is (= Init @(:current-place Process1)))
        (is (= Init2 @(:current-place Process2)))
        (tick-block! Basic-block1)
        (is (= Run @(:current-place Process1)))
        (is (= Run2 @(:current-place Process2)))
        (is (= 8 ((basic-block1-env 'get) :O2)))

        (is (= 6 (get-input O1)))))))

(deftest compound-test
  (testing "compound test, mock an object."
    (let [ basic-block1-env (get-env
                              {:I1 0
                               :I2 0
                               :O1 2
                               :O2 3})
           basic-block2-env (get-env
                              {:I3 0
                               :O3 0})
           simulation-env (get-env
                            {:S1 5})
           S1 (make-port "S1" "output-port" nil simulation-env)

           In1 (make-port "In1" "relay-port" S1 nil)
           In2 (make-port "In2" "relay-port" S1 nil)


           I1 (make-port "I1" "input-port" In1 basic-block1-env)
           O1 (make-port "O1" "output-port" nil basic-block1-env)
           I2 (make-port "I2" "input-port" In2 basic-block1-env)
           O2 (make-port "O2" "output-port" nil basic-block1-env)
           I3 (make-port "I3" "input-port" O1 basic-block2-env)
           O3 (make-port "O3" "output-port" nil basic-block2-env)

           Out1 (make-port "Out1" "relay-port" O3 nil)

           Init (make-place "Init")
           Run (make-place "Run")
           Init2 (make-place "Init2")
           Run2 (make-place "Run2")

           Always (make-place "Always")

           T1 (make-transition "T1" Init Run "I2>0" "O1=I1+1;")
           T2 (make-transition "T2" Run Init "true" "O1=I1-1;")
           T3 (make-transition "T3" Init2 Run2 "true" "O2=I2+3;")
           T4 (make-transition "T4" Run2 Init2 "true" "O2=I2-3;")
           T5 (make-transition "T5" Always Always "true" "O3=I3*2;")


           Process1 (make-process "Process1" [Init Run] Init [T1 T2] [] basic-block1-env)
           Process2 (make-process "Process2" [Init2 Run2] Init2 [T3 T4] [] basic-block1-env)
           Process3 (make-process "Process3" [Always] Always [T5] [] basic-block2-env)


           Basic-block1 (make-basic-block "Basic-block1" [I1 I2] [O1 O2] [Process1 Process2] basic-block1-env)
           Basic-block2 (make-basic-block "Basic-block2" [I3] [O3] [Process3] basic-block2-env)


           Block3 (make-block "Block3" [In1 In2] [Out1] [Basic-block1 Basic-block2])]
      (do
        (is (= 5 (get-input S1)))
        (is (= 5 (get-input I1)))
        (is (= 2 (get-input O1)))
        (is (= 0 (get-input O3)))

        (is (= 0 ((basic-block1-env 'get) :I1)))
        (is (= 0 ((basic-block2-env 'get) :I3)))
        (update-inputs! Block3)
        (is (= 5 ((basic-block1-env 'get) :I1)))
        (is (= 2 ((basic-block2-env 'get) :I3)))

        (is (= Init @(:current-place Process1)))
        (is (= Init2 @(:current-place Process2)))

        (let [snapshot (get-snapshot Block3)]
          (do

            (is (= Init @(:current-place Process1)))
            (is (= Init2 @(:current-place Process2)))
            (is (= 3 ((basic-block1-env 'get) :O2)))
            (is (= 0 ((basic-block2-env 'get) :O3)))


            (tick-block! Block3)
            (is (= Run @(:current-place Process1)))
            (is (= Run2 @(:current-place Process2)))
            (is (= 8 ((basic-block1-env 'get) :O2)))
            (is (= 4 ((basic-block2-env 'get) :O3)))
            (is (= 6 (get-input O1)))

            (tick-block! Block3)
            (restore-snapshot! Block3 snapshot)

            (is (= Init @(:current-place Process1)))
            (is (= Init2 @(:current-place Process2)))) )



        ))))

(deftest compound-sequence-test
  (testing "compound test, mock an object."
    (let [ basic-block1-env (get-env
                              {:I1 0
                               :I2 0
                               :O1 0
                               :O2 0})
           basic-block2-env (get-env
                              {:I3 0
                               :O3 0})
           simulation-env (get-env
                            {:S1 0})
           S1 (make-port "S1" "output-port" nil simulation-env)

           In1 (make-port "In1" "relay-port" S1 nil)
           In2 (make-port "In2" "relay-port" S1 nil)


           I1 (make-port "I1" "input-port" In1 basic-block1-env)
           O1 (make-port "O1" "output-port" nil basic-block1-env)
           I2 (make-port "I2" "input-port" In2 basic-block1-env)
           O2 (make-port "O2" "output-port" nil basic-block1-env)
           I3 (make-port "I3" "input-port" O1 basic-block2-env)
           O3 (make-port "O3" "output-port" nil basic-block2-env)

           Out1 (make-port "Out1" "relay-port" O3 nil)

           Init (make-place "Init")
           Run (make-place "Run")
           Init2 (make-place "Init2")
           Run2 (make-place "Run2")

           Always (make-place "Always")

           T1 (make-transition "T1" Init Run "I2>0" "O1=I1+1;")
           T2 (make-transition "T2" Run Init "true" "O1=I1-1;")
           T3 (make-transition "T3" Init2 Run2 "true" "O2=I2+3;")
           T4 (make-transition "T4" Run2 Init2 "true" "O2=I2-3;")
           T5 (make-transition "T5" Always Always "true" "O3=I3+1;")


           Process1 (make-process "Process1" [Init Run] Init [T1 T2] [] basic-block1-env)
           Process2 (make-process "Process2" [Init2 Run2] Init2 [T3 T4] [] basic-block1-env)
           Process3 (make-process "Process3" [Always] Always [T5] [] basic-block2-env)


           Basic-block1 (make-basic-block "Basic-block1" [I1 I2] [O1 O2] [Process1 Process2] basic-block1-env)
           Basic-block2 (make-basic-block "Basic-block2" [I3] [O3] [Process3] basic-block2-env)


           Block3 (make-block "Block3" [In1 In2] [Out1] [Basic-block1 Basic-block2])]
      (do
        (doseq [i (range 1 10)]
          (do
            ((simulation-env 'set)
             :S1
             i)
            (print "S1: " (get-input S1) "\t")
            (top-cycle! Block3)
            (println "O3: " (get-input O3))))
        ))))


(deftest expression-test
  (testing
    (let [tmp-env (get-env
                    {(keyword "out") 0})

          tmp-ast (build-AST "out + 1;")

          result (compute!
                   tmp-ast
                   tmp-env)]
      (is (= 1 result)))))

