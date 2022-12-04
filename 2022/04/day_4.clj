(ns day-4
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn parse-int
  [x]
  (try (Integer/parseInt x)
       (catch NumberFormatException e
         nil)))

(defn str->range
  [s]
  (let [[start end] (->> (string/split s #"-")
                         (map parse-int))]
    (range start (inc end))))

(defn compute-ranges
  [input]
  (->> (string/split input #",")
       (map str->range)))

(defn full-overlap?
  [[x y]]
  ;; if either of them is a subset, at least one of them fully contains the other
  (or (cset/subset? (set x) (set y))
      (cset/subset? (set y) (set x))))

(defn count-full-overlaps
  [ranges]
  (->> (map full-overlap? ranges)
       (filter true?)
       (count)))

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)))

(defn part-one []
  (->> (parse-input)
       (map compute-ranges)
       (count-full-overlaps)))

(defn intersects?
  [[x y]]
  (-> (cset/intersection (set x) (set y))
      (count)
      (pos?)))

(defn count-intersects
  [ranges]
  (->> (map intersects? ranges)
       (filter true?)
       (count)))

(defn part-two []
  (->> (parse-input)
       (map compute-ranges)
       (count-intersects)))

(println "part one:" (part-one))
(println "part two:" (part-two))
