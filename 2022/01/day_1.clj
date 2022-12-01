(ns day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn parse-int
  [x]
  (try (Integer/parseInt x)
       (catch NumberFormatException e
         nil)))

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)
       (map parse-int)
       (partition-by nil?)
       (remove #(= % '(nil)))
       (map #(apply + %))))

;;

(defn part-one []
  (->> (parse-input)
       (apply max)))

(defn part-two []
  (->> (parse-input)
       (sort-by -)
       (take 3)
       (apply +)))
