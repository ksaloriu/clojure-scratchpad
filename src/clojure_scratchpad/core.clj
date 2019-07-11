(ns clojure-scratchpad.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  )

;; conditionals 
(if true "return this" "not this")
(if false "returns nil")

(if true (do (println "print this!") "return that!"))
(when true (println "print this!") (println "print that!") "return!")

(if nil "..." "nil is not true...")
(if "" "empty string true!" "empty string false!")

(def my-number 3)
(println my-number)

;; ratios
(+ 1/2 1/3)

;; hash maps
{"key" "value" "another key" "another value"}
(hash-map :key1 "value1" :key2 "value2")

(get {:a 1 :b 2} :a)
(get {:a 1 :b 2} :c)
(get {:a 1 :b 2} :c 3)
({:a 1 :b 2 :c 3} :c)
(:a {:a 1 :b 2 :c 3})
(get-in {:a 1 :b {:c "value"}} [:b :c])

;; vectors
[1 2 3 4]
(get [1 2 3 4] 0)
[1 2 3 "4"]
(vector 1 2 3 4)
(conj [1 2 3] 4)

;; lists
'(1 2 3 4)
(nth '(1 2 3 4) 0)
(list 1 2 "3" 4)

;; hash sets
#{:A :B :C}
;; #{:A :B :C :C}
(hash-set :A :B :C :C)
(set [1 2 3 4 4])

(contains? #{:A :B :C} :A)
(:A #{:A :B :C})

;; functions
(defn my-func
  "docstring"
  []
  (println "my-func!"))











