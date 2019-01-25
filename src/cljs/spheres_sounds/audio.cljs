(ns spheres-sounds.audio
  (:require [cljs-bach.synthesis :refer [connect-> percussive adsr adshr sine square sawtooth add gain high-pass low-pass white-noise
                                         triangle constant envelope run-with destination current-time]]
            [spheres-sounds.db :refer [default-db]]
            [re-frame.core :refer [reg-event-fx dispatch]]))

(defonce context (cljs-bach.synthesis/audio-context)) ;this went to views.

(defn high-hat
  "An imitation high-hat, made with white noise."
  [decay]
  (connect->
   white-noise
   (percussive 0.01 decay)
   (high-pass 3000)
   (low-pass 4500)
   (gain 0.1)))

;; (-> (high-hat 10)
;;     (connect-> destination)
;;     (run-with context (current-time context) 1.0))

(defn ping [freq]
  (connect->
    (sawtooth freq)         ; Try a sawtooth wave.
    ;(percussive 1 7) ; Try varying the attack and decay.
    (gain 0.1)
    ;(adshr 1 3 0 1 3)
    ))          ; Try a bigger gain.

;; (-> (ping 440)
;;     (connect-> destination)
;;     (run-with context (current-time context) 3))

(defn pings
  [freq1 freq2 freq3 freq4 freq5]
  (connect->
   (add
    (sine freq1)
    (sine freq2)
    (sine freq3)
    (sine freq4)
    (sine freq5)) ; What happens if the two frequencies are further apart? Why?
   (gain 0.1)
   (adshr 1 3 2 1 1)))


;; (-> (pings 9 78 540 198 170)
;;     (connect-> destination)
;;     (run-with context (current-time context)6))

(defn pops
  [& freqs]
  (let [v [freqs]]
    ))
;(pops 10 "c" 30)

(defn ding
  ([]
   (ding 0))
  ([freq]
   (connect->
    (sine freq)
    (gain 0.1)
    (adshr 1 1 1 1 1))))

(defn dang [freq] (-> (ding freq)
               (connect-> destination)
               (run-with context (current-time context) 6)))


;; (dings 100 200 121 13 92 87 123)

(defn dings
  [& freq]
  (map dang freq))

(reg-event-fx
 :audio
 (fn [cofx [_ v]]
   (map dings v)))


;; (dings 100 200)


                                        ;[freq1 freq2]

;; (connect->
;;  (add
;;   (sine freq1);(gain 0.1)
;;   (sine freq2))
;;  (gain 0.2)
;;  (adshr 1 1 1 1 1))
;; (-> (dings 280 290)
;;     (connect-> destination)
;;     (run-with context (current-time context) 6))