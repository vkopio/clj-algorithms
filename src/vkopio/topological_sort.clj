(ns vkopio.topological-sort
  (:require [clojure.set :refer [difference union]])
  (:gen-class))

(def a-dag
  "A map of verices with a set of out-neighbors."
  {:a #{:c}
   :b #{:d :e}
   :c #{:e :d}
   :d #{:e}
   :e #{}})

(defn get-zero-degree-vertex
  "Returns a vertex from a DAG that has no in-neighbors."
  [dag]
  (let [vertices                   (set (keys dag))
        vertices-with-in-neighbors (apply union (vals dag))]
    (first (difference vertices vertices-with-in-neighbors))))

(defn topo-sort
  "Given a DAG as an input, returns a vector of vertices topologically sorted."
  [dag]
  (loop [remaining-dag     dag
         topological-order []]
    (if-let [next-vertex (get-zero-degree-vertex remaining-dag)]
      (recur (dissoc remaining-dag next-vertex)
             (conj topological-order next-vertex))
      topological-order)))

(defn -main [& args]
  (println (topo-sort a-dag)))
