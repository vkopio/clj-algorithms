(ns vkopio.dag-test
  (:require [clojure.test :refer :all]
            [vkopio.dag :refer [topological-sort]]))

(def dag-1
  {:a #{:c}
   :b #{:d :e}
   :c #{:e :d}
   :d #{:e}
   :e #{}})

(def dag-2
  {:a #{:e}
   :b #{:e}
   :c #{:e :d}
   :d #{:e}
   :e #{}})

(def dg-1
  {:a #{:c}
   :b #{:d :e}
   :c #{:e :d}
   :d #{:e}
   :e #{:a}})

(deftest topological-sort-test
  (testing "returns a correct topological sort of dag-1"
    (let [sorted (topological-sort dag-1)]
      (is (= #{:a :b} (->> sorted (take 2) set)))
      (is (= :c (nth sorted 2)))
      (is (= :d (nth sorted 3)))
      (is (= :e (last sorted)))))

  (testing "returns a correct topological sort of dag-2"
    (let [sorted (topological-sort dag-2)]
      (is (= #{:a :b :c} (->> sorted (take 3) set)))
      (is (= :d (nth sorted 3)))
      (is (= :e (last sorted)))))

  (testing "returns nil if the graph is not a DAG"
    (is (nil? (topological-sort dg-1)))))
