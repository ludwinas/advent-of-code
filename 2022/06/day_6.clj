(ns day-6
  (:require [clojure.string :as string]))

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)
       (first)
       (map str)))

(defn find-start-packet
  [input start-size]
  (loop [acc []
         input-list input]
    (if (or (empty? input-list)
            (= start-size (-> (take-last start-size acc) (set) (count))))
      (count acc)
      (recur (conj acc (first input-list))
             (rest input-list)))))

(defn part-one []
  (find-start-packet (parse-input) 4))

(defn part-two []
  (find-start-packet (parse-input) 14))

(println "part-one" (part-one))

(println "part-two" (part-two))
