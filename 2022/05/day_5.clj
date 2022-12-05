(ns day-5
  (:require [clojure.string :as string]))

(defn parse-int
  [x]
  (try (Integer/parseInt x)
       (catch NumberFormatException e
         nil)))

(defn parse-input []
  (->> (slurp "input.txt")
       (string/split-lines)))

(defn count-stacks
  [input]
  (->> (first input)
       (partition 3 4)
       (count)))

(defn parse-stack-line
  [line]
  (->> (partition 3 4 line)
       (map #(apply str %))
       (mapv #(when-not (string/blank? %) (str (nth % 1))))))

;; rotate the matrix
(defn transpose
  [m]
  (apply mapv vector m))

(defn parse-stacks
  [input]
  (->> (mapv parse-stack-line input)
       (transpose)
       (mapv #(remove nil? %))))

(defn parse-move
  [move]
  (let [c (string/split move #" ")]
    (->> {:move (parse-int (nth c 1))
          :from (parse-int (nth c 3))
          :to (parse-int (nth c 5))})))

(defn parse-moves
  [moves]
  (map parse-move moves))

(defn parse-instructions
  []
  (let [input (parse-input)
        stacks (count-stacks input)]
    {:start (parse-stacks (take (dec stacks) input))
     :moves (parse-moves (drop (inc stacks) input))}))

(defn part-one []
  (let [{:keys [start moves]} (parse-instructions)
        state (atom start)]
    (doseq [{:keys [:move :from :to]} moves]
      (let [from-row (nth @state (dec from))
            to-row (nth @state (dec to))
            [moving remaining] (split-at move from-row)
            ;; crates are moved one at a time but I can't be bothered so I just flip the moving
            ;; list :')
            flip-moving (reverse moving)
            added (concat flip-moving to-row)]
        ;; remove items from the from-row
        ;; = swap the from-row with the remaining items
        (swap! state assoc (dec from) remaining)
        ;; add the items to the to-row
        ;; = swap the to-row with added
        (swap! state assoc (dec to) added)))
    ;; we only want the top crates
    (map first @state)))

(part-one)
