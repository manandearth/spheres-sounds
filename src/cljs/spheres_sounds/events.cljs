(ns spheres-sounds.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx subscribe debug]]
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
   (update-in db [:spheres sphere :vis] not)))

;; (reg-event-db
;;  :invisible
;;  (fn [db [_ sphere]]
;;    (update-in db [:spheres sphere :vis] not)))




;; (reg-event-db
;;  :select-attribute
;;  (fn [db [_ attr]]
;;    (assoc db :selected-attr attr)))

(reg-event-fx
 :select-attribute
 (fn [cofx [_ attr]]
   {:db (assoc (:db cofx) :selected-attr attr)
    }))


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
 :update-freq-rate! [debug]
 (fn [db [_ rate]] 
   (assoc db :freq-rate rate)))

;;TODO update-freq-rate! takes the rate from the subscription:
;;calc-freq-rate -- this needs to be updated with the interpolate function. Also in the view 'sliders' the two rate subscriptions seem redundant. --> the idea is to have a correct interpolation and perhaps not use a fixed rate as this is what's confusing. -->>> actually a fixed number rate is not possible so correct it...!!!!!!
