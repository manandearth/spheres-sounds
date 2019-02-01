(ns spheres-sounds.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx subscribe]]
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

(reg-event-db
 :toggle-global
 (fn [db _]
   (update db :global not))) ;toggles true/false hypothetically

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

(reg-event-db
 :update-freq-range!
 (fn [db [_ k v]]
   (assoc-in db [:freq-range k] v)))

(reg-event-db
 :update-freq-rate!
 (fn [db [_ rate]] 
   (assoc db :freq-rate rate)))

