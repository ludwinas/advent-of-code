(ns day-1
  (:require [clojure.string :as string]))

(def opponent-key
  {"A" :rock
   "B" :paper
   "C" :scissors})

(def my-key
  {"X" :rock
   "Y" :paper
   "Z" :scissors})

(def result-key
  {"X" :lose
   "Y" :draw
   "Z" :win})

(def shape-scores
  {:rock 1
   :paper 2
   :scissors 3})

(def result-score
  {:lose 0
   :draw 3
   :win 6})

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)
       (map #(string/split % #" "))))

(defn outcome
  [op-choice my-choice]
  (let [o (opponent-key op-choice)
        m (my-key my-choice)]
    (case [o m]
      [:rock :scissors] 0
      [:rock :paper] 6
      [:rock :rock] 3
      [:paper :rock] 0
      [:paper :scissors] 6
      [:paper :paper] 3
      [:scissors :paper] 0
      [:scissors :rock] 6
      [:scissors :scissors] 3)))

(defn intended-result
  [op-choice result]
  (let [o (opponent-key op-choice)
        r (result-key result)]
    (case [o r]
      [:rock :win] :paper
      [:rock :lose] :scissors
      [:rock :draw] :rock
      [:paper :draw] :paper
      [:paper :win] :scissors
      [:paper :lose] :rock
      [:scissors :draw] :scissors
      [:scissors :win] :rock
      [:scissors :lose] :paper)))

(defn part-one-score-round
  [op-choice my-choice]
  (+ (-> my-choice (my-key) (shape-scores))
     (outcome op-choice my-choice)))

(defn part-two-score-round
  [op-choice result]
  (let [my-choice (intended-result op-choice result)]
    (+ (shape-scores my-choice)
       (result-score (result-key result)))))

(defn part-one
  []
  (->> (parse-input)
       (map (fn [[o m]]
              (part-one-score-round o m)))
       (apply +)))

(defn part-two
  []
  (->> (parse-input)
       (map (fn [[o r]]
              (part-two-score-round o r)))
       (apply +)))

(println "part one:" (part-one))
(println "part two:" (part-two))
