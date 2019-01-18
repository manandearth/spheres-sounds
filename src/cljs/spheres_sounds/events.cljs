(ns spheres-sounds.events
  (:require
   [re-frame.core :refer [reg-event-db]]
   [spheres-sounds.db :as db]
   ))

(reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
 :select-system
 (fn [db [_ sys]]
   (assoc db/default-db :selected-system sys)))

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