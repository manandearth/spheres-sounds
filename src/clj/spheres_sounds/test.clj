(ns spheres-sounds.test
  (:require  [clojure.test :refer [is are testing with-test deftest run-tests]]))

;;just a mock test, at the moment there is nothing clj to test.
(deftest random-tests
  (testing
      (is (= 2 (+ 1 1)))
    (is (.startsWith "abcd" "ab"))
    (is (= 4 (+ 2 2)))))



