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

;; or returns first truthy value or nil if all falsey
(or false nil "foo")
(or false nil)

;; and returns first falsey value or last value if all truthy
(and "foo" "bar" nil)
(and "foo" "bar" "zam")

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
(assoc {:a 1 :b 2} :a 2)
(assoc {:a 1 :b 2} :c 2)

;; vectors
[1 2 3 4]
(get [1 2 3 4] 0)
([1 2 3 4] 0)
[1 2 3 "4"]
(vector 1 2 3 4)
(conj [1 2 3] 4)
(assoc [1 2 3 4] 0 3)

;; lists
'(1 2 3 4)
(nth '(1 2 3 4) 0)
(list 1 2 "3" 4)
(cons 1 '(2 3))
(cons 1 [2 3])

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

(defn multi-arity
  ([one] one)
  ([one two] (str one " + " two)))

(defn rest-param
  [& rest]
  (map inc rest))

(defn destructure-vec
  [[first second & rest]]
  (println first)
  (println second))

;; anonymous functions
(map (fn [x] (* x x)) [1 2 3 4 5])
(map #(* % %) [1 2 3 4 5])

;; let
(let [x 1] (* 2 x))

;; sequence abstraction
;; ISeq: need to implement first, rest and cons
(cons 1 ())
(cons 1 (cons 2 ()))

(map inc '(1 2 3 4 5))

(map inc [1 2 3 4 5])
;; same as:
(map inc (seq [1 2 3 4 5]))
(seq [1 2 3 4 5])

;; works for maps, turn to list of 2-element vectors:
(seq {"a" 1 "b" 2 "c" 3})
(map str {"a" 1 "b" 2 "c" 3})

;; into to transform back to original type:
(into [] (map inc [1 2 3 4 5]))
;; works for other types as well:
(into #{} [1 2 2 2 2 3 4 5])
(into #{1 2} [3 4 5 6])
(into {} [[1 1] [2 2]])

;; conj, like into but for argumetns not sequences
(conj [] 1 2 3 4 5)

;; common sequence functions
(take 2 [1 2 3 4])
(drop 2 [1 2 3 4])

(take-while #(< % 3) [1 2 3 4 1 2 3 4])
(drop-while #(< % 3) [1 2 3 4 1 2 3 4])

(filter #(= (mod % 2) 0) [1 2 3 4 5 6])
(some #(> % 2) [1 2 3])
(some #(> % 3) [1 2 3])

(sort [2 1 3])
(sort-by count ["aaa" "bb" "c"])
(concat [1 2 3] [4 5 6])

;; loop and recur

(defn factorial [n]
  (loop [current n
         result 1]
    (if (= current 1) result
        (recur
         (dec current)
         (* result current)))))

;; threading macros

(-> 1
    (inc)
    (+ 100)
    (/ 3))

(->> "zam"
     (str " bar ")
     (str "foo "))

;; lazy sequences

;; repeat creates infinite lazy sequence
(take 5 (repeat 1))

(defn costly-inc
  [n]
  (Thread/sleep 1000)
  (inc n))
;; costly-inc not evaluated, lazy sequence
(time (map costly-inc [1 2 3 4 5]))
;; costly-inc evaluated for *all* list elements, not just first, due to batching
(time (first (map costly-inc [1 2 3 4 5])))
;; this seems to run costly-inc only once (?)
(time (first (map costly-inc (take 1000 (repeat 1)))))

(take 5 (repeatedly (fn [] (rand-int 10))))

;; Higher-order functions

(apply max [1 2 3 4 5])
(apply + [1 2 3 4 5])

(every? true? [true true true])
(every? true? [true true false])
(some true? [true true false])

(ns-name *ns*)
(ns-interns *ns*)

;; dynamic scope

(def ^:dynamic *special-var* "foo")
(defn print-var [] (println *special-var*))
(print-var)
(binding [*special-var* "bar"]
  (print-var))

(eval (read-string "(+ 1 2 3)"))

;; reader macro examples
(read-string "#(+ 1 %)")
(read-string "'(1 2 3)")
(read-string "@variable")

;; macros

(defmacro ignore-last
  [function-call]
  (butlast function-call))
(ignore-last (+ 1 2 3))
(ignore-last (+ 1 2 "ignored!"))
(macroexpand '(ignore-last (+ 1 2 3)))

(defmacro infix
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))
(infix (1 + 2))

(defmacro infix-2
  [[operand1 operator operand2]]
  (list operator operand1 operand2))
(infix-2 (1 + 2))

;; quote and syntax quote
'(+ 1 2)
(quote (+ 1 2))
'undefined

'(+ 1 2)
;; syntax quote contains namespace for +
`(+ 1 2)
;; unquote with ~
`(+ 1 2 ~(inc 2))
;; unquote splicing
`(+ ~(list 1 2 3))
`(+ ~@(list 1 2 3))

;; futures

(let [result (future (println "prints only once!") (+ 1 1))]
  (println "deref: " (deref result))
  (println "@: " @result))

;; deref timeout
(deref (future (Thread/sleep 1000) 1) 200 2)
(deref (future (Thread/sleep 1000) 1) 2000 2)

(realized? (future (Thread/sleep 1000)))

;; delay
(def delayed
  (delay (println "evaluated") "result"))
(force delayed)
@delayed

;; atoms

(def foo (atom {:name "foo" :age 100}))
@foo
(swap! foo update-in [:age] + 1)
@foo
(reset! foo {:name "foo" :age 110})
@foo

;; exceptions

(try (throw (RuntimeException. "runtime error!"))
     (catch Exception e
       (println "catch"))
     (finally
       (println "finally")))

;; metadata

(def data (with-meta {:key "value"} {:tag "metadata"}))
(meta data)

;; does not work:
;; (def num (with-meta 1 {:tag "value"}))
