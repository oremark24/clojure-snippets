(ns clojure-snippets.euler
  (:require [clojure.math.numeric-tower :as numeric]
            [clojure-snippets.math :as math]))

;; helper

(defn int-exp 
  "integer number via exponential notation"
  [m e]  
  (* m (numeric/expt 10 e)))

;; 1

(defn solve-1 
  ([] (solve-1 1000))
  ([n] 
    (reduce + (for [k (range 3 n)
                    :when (or (zero? (mod k 3))
                              (zero? (mod k 5)))]
                k))))

(defn solve-1-sample 
  ([] (solve-1-sample 1000))
  ([n]
    (let [sum-div-by (fn [d]
                       (let [k (quot (dec n) d)]
                         (quot (* d k (inc k)) 2)))]
      (+ (sum-div-by 3)
         (sum-div-by 5)
         (- (sum-div-by 15))))))

;; 2
;; Every third Fibonacci number is even: 2, 8, 34, 144, ...
;; fk = 4 * fk-3 + fk-6 

(defn solve-2 
  ([] (solve-2 (int-exp 4 6)))
  ([n]
    (->> 
      ((math/make-fib 1 4) 2 8)
      (take-while (fn [a] (>= n a)))
      (reduce +))))

;; 7
;; The nth prime is about n * log(n).
;; Thus, take 2n * log(n) as upper bound

(defn solve-7
  ([] (solve-7 (inc (int-exp 1 4))))
  ([n]
    (nth (math/primes (numeric/round (* 2 n (Math/log n)))) 
         (dec n))))

;; 10

(defn solve-10
  ([] (solve-10 (int-exp 2 6)))
  ([n] (reduce + (math/primes (dec n)))))

;; 15
;; make 2n decisions: right or down
;; choose n down-decisions from all decisions

(defn solve-15 
  ([] (solve-15 20))
  ([n] (math/binom (* 2 n) n)))

;; 351
;; TODO

(defn solve-351 []
  (let [pred (fn [n m] (> 10000 (+ n m)))]
    (count (math/coprimes pred))))

(defn solve-351-async []
  (let [pred (fn [n m] (> 10000 (+ n m)))
        odd (future (count (math/coprimes 1 2 pred)))
        even (future (count (math/coprimes 1 3 pred)))]
    (+ @odd @even)))
     