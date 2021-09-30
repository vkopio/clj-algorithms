(ns vkopio.dag
  (:require [clojure.set :refer [difference union]])
  (:gen-class))

(def dag-a
  "A map of verices with a set of out-neighbors."
  {:a #{:c}
   :b #{:d :e}
   :c #{:e :d}
   :d #{:e}
   :e #{}})

(defn vertices-without-in-neighbors
  "Returns vertices from a DAG which do not have in-neighbors. Returns nil if
   there are no vertices for convenience."
  [dag]
  (let [all-vertices         (-> dag keys set)
        in-neighbor-vertices (apply union (vals dag))]
    (-> all-vertices (difference in-neighbor-vertices) vec not-empty)))

(defn topological-sort
  "Given a DAG as an input, returns a vector of vertices topologically sorted.
   Returns nil if the graph contains cycles."
  [dag]
  (loop [remaining-dag     dag
         topological-order []]
    (if-let [zero-degree-vertices (vertices-without-in-neighbors remaining-dag)]
      (recur (apply dissoc remaining-dag zero-degree-vertices)
             (into topological-order zero-degree-vertices))
      (when (empty? remaining-dag)
        topological-order))))

(defn -main [& args]
  (println (topological-sort dag-a)))
