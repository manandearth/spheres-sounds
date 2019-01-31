(ns spheres-sounds.audio
  (:require [cljs-bach.synthesis :refer [connect-> percussive adsr adshr sine square sawtooth add gain high-pass low-pass white-noise
                                         triangle constant envelope run-with destination current-time]]
            [spheres-sounds.db :refer [default-db]]
            [re-frame.core :refer [reg-event-fx dispatch]]))

(defonce context (cljs-bach.synthesis/audio-context)) ;this went to views.

(defn note-p1
  ([]
   (note-p1 0))
  ([freq]
   (connect->
    (sine freq)
    (gain 0.1)
    (adshr 1 1 1 1 1))))

(defn note-p2 [freq] (-> (note-p1 freq)
               (connect-> destination)
               (run-with context (current-time context) 6)))


(defn note-p3
  [& freq]
  (map note-p2 freq))

;(note-p3 100 200 121 13 92 87 123)


;; (reg-event-fx
;;  :audio
;;  (fn [cofx [_ v]]
;;    (map note-p3 v)))

;;the following is a new synth-handler for envelope..:
(defn play-note-handler
  ([[a d s h r]]
   (play-note-handler [a d s h r] 0))
  ([[a d s h r] freq]
   (connect->
    (sine freq)
    (gain 0.1)
    (adshr a d s h r))))

(defn play-note! [env freq]
  (let [[a d s h r] env]
    (-> (play-note-handler [a d s h r] freq)
        (connect-> destination)
        (run-with context (current-time context) (+ a d s h r)))))

(defn play-chord! [env freqs]
  (let [[a d s h r] env]
    (doseq [freq freqs]
      (-> (play-note-handler [a d s h r] freq)
          (connect-> destination)
          (run-with context (current-time context) (+ a d s h r)))))  )



;; (tester2 [0 0 2 0 1] 300.32452)
;; (tester2 [1 5 1 1 1] 440)

(play-chord! [0 1 0 1 0] [440 400 500 1200])
