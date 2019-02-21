(ns spheres-sounds.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx reg-fx subscribe debug]]
   [spheres-sounds.db :as db]
   [spheres-sounds.audio :as audio]))

(reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
 :select-system
 (fn [db [_ sys]]
   (assoc db :selected-system sys)))


;;TOGGLE VISIBILITY OF SPHERE BY NEGATING LOGIC: 'not'
(reg-event-db
 :visible
 (fn [db [_ sphere]]
   (update-in db [:spheres sphere :vis] not)))

(reg-event-fx
 :select-attribute
 (fn [cofx [_ attr]]
   {:db (assoc (:db cofx) :selected-attr attr)
    }))


;;TODO Need to have this toggling (assoc a sphere to vis or dissoc)
;; (reg-event-db
;;  :toggle-sphere
;;  (fn [db [_ sphere]]
;;     (assoc-in db [:spheres sphere :vis] true)))

;;EFFECTS FOR AUDIO, USED DOWN IN the even-fx :audio and :chord
(reg-fx
 :play-note!
 (fn [[adshr freq]]
   (audio/play-note! adshr freq)))

(reg-fx
 :play-chord!
 (fn [[adshr freqs]]
   (audio/play-chord! adshr freqs)))


;;TOGGLE GLOBAL/LOCAL -> SWITCH IN DB NEGATING LOGIC: 'not' 
(reg-event-db
 :toggle-global
 (fn [db _]
   (update db :global not))) ;toggles true/false hypothetically


;;FOLLOWING TWO EFFECTS USE :playnote! and :play-chord! REGISTERED ABOVE.
(reg-event-fx
 :audio
 (fn [cofx [_ adshr freq body]]
                                        ;(map audio/note-p3 v)
   {:play-note! [adshr freq]
    :dispatch [:press! body]}
   ))

(reg-event-fx
 :chord
 (fn [cofx [_ adshr freqs]]
   {:play-chord! [adshr freqs]}))

;;SET THE SYNTH ENVELOPE THAT REQUIRES A 5 INT VECTOR
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


(reg-event-db
 :press!
 (fn [db [_ pressed]]
   (assoc db :pressed pressed)))


;;TODO update-freq-rate! takes the rate from the subscription:
;;calc-freq-rate -- this needs to be updated with the interpolate function. Also in the view 'sliders' the two rate subscriptions seem redundant. --> the idea is to have a correct interpolation and perhaps not use a fixed rate as this is what's confusing. -->>> actually a fixed number rate is not possible so correct it...!!!!!!
