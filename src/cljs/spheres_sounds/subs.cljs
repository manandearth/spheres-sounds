(ns spheres-sounds.subs
  (:require
   [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::spheres
 (fn [db]
   (vals (:spheres db))))

(reg-sub
 ::systems
 (fn [db]
   (:systems db)))

(reg-sub
 ::selected-system
 (fn [db]
   (:selected-system db)))

(reg-sub
 ::selected-system-attr
 (fn [db]
   (get-in db [:spheres (:selected-system db)])))
