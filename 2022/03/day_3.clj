(ns day-3
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)))

(defn letter-range
  [start end]
  (->> (map char (range (int start) (inc (int end))))
       (map str)))

(def priority
  (merge (zipmap (letter-range \a \z)
                 (range 1 27))
         (zipmap (letter-range \A \Z)
                 (range 27 53))))

(defn part-one []
  (->> (parse-input)
       (map seq)
       (map #(split-at (/ (count %) 2) (map str %)))
       (map (fn [[l r]] (cset/intersection (set l) (set r))))
       (map first) ; intersection contains only one item
       (map priority)
       (apply +)))

(defn find-badge
  [group]
  (->> (map set group)
       (apply cset/intersection)
       (first)
       (str)))

(defn part-two []
  (->> (parse-input)
       (map seq)
       (partition 3)
       (map find-badge)
       (map priority)
       (apply +)))

(println "part one:" (part-one))

(println "part two:" (part-two))
