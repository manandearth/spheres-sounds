(ns spheres-sounds.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]
   [spheres-sounds.db :as db]
   [spheres-sounds.audio :as audio]
   ))

(reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
 :select-system
 (fn [db [_ sys]]
   (assoc db :selected-system sys)))

(reg-event-db
 :visible
 (fn [db [_ sphere]]
   (assoc-in db [:spheres sphere :vis] true)))

(reg-event-db
 :invisible
 (fn [db [_ sphere]]
   (assoc-in db [:spheres sphere :vis] false)))

(reg-event-db
 :select-attribute
 (fn [db [_ attr]]
   (assoc db :selected-attr attr)))


;;TODO Need to have this toggling (assoc a sphere to vis or dissoc)
(reg-event-db
 :toggle-sphere
 (fn [db [_ sphere]]
    (assoc-in db [:spheres sphere :vis] true)))

(reg-event-fx
 :audio
 (fn [cofx [_ adshr freq]]
   ;(map audio/note-p3 v)
   (audio/play-note! adshr freq)
   ))

(reg-event-fx
 :chord
 (fn [cofx [_ adshr freqs]]
   (audio/play-chord! adshr freqs)))

;;set the synth-envelope
(reg-event-db
 :set-envelope!
 (fn [db [_ v]]
   (assoc db :envelope v)))




;; (reg-event-fx
;;  :apo-sound
;;  (fn [cofx [_ ]])
;;  )

;; (reg-event-db
;;  :visible
;;  (fn [db [_ k]]
;;    (assoc-in db [:spheres k :vis] false)))

;; (reg-event-db
;;  :invisible
;;  (fn [db [_ k]]
;;    (assoc-in db [:spheres k :vis] true)))

;; (reg-event-db
;;  :x-selected
;;  (fn [db [_ attr]]
;;    (assoc db :x-selected attr)))

;; (reg-event-db
;;  :y-selected
;;  (fn [db [_ attr]]
;;    (assoc db :y-selected attr)))

;; (reg-event-db
;;  :sys-selected
;;  (fn [db [_ sys]]
;;    (assoc db :sys-selected sys)))
