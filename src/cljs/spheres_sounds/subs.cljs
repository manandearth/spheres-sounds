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

;; (reg-sub
;;  ::sorted-selected
;;  :<- [::spheres]
;;  (fn [spheres _]
;;    (sort-by :apoapsis
;;              (concat
;;               (list (assoc
;;                      (first
;;                       (filter
;;                        #(= (:name %) @(subscribe [::selected-system])) spheres))
;;                      :periapsis 0 :apoapsis 0 ))
;;               (filter
;;                #(contains?
;;                  (set (:satelites @(subscribe [::selected-system-attr])))
;;                  (:name %)) spheres))))
;; )


(reg-sub
 ::sorted-spheres
 :<- [::spheres]
 :<- [::selected-system]
 (fn [[spheres parent] _]
   (sort-by :apoapsis (concat
                       (list (assoc (first (filter #(= parent (:name %)) spheres)) :periapsis 0 :apoapsis 0))
                       (filter #(= parent (:parent %))  spheres)))))

(reg-sub
 ::selected-attr
 (fn [db]
   (:selected-attr db)))

(reg-sub
 ::toggled-attr
 :<- [::selected-attr]
 :<- [::sorted-spheres]
 (fn [[attr spheres] _]
   (map attr (filter :vis spheres))))

(def non-numeric-keys [:parent :satelites :form :name :vis])

(reg-sub
 ::attributes
 :<- [::spheres]
 (fn [spheres _]
   (remove (set non-numeric-keys) (keys (first spheres)))))

(reg-sub
 ::global
 (fn [db]
   (:global db)))

(reg-sub
 ::freq-range
 (fn [db]
   (get db :freq-range)))


;;make this a set-freq-rate! and add a :freq-rate to db
(reg-sub
 ::calc-freq-rate
 :<- [::spheres]
 :<- [::sorted-spheres]
 :<- [::selected-attr]
 :<- [::freq-range]
 :<- [::global]
 (fn [[spheres sorted-spheres attr freq-range global] _]
   (if global
     (let [high-point (apply max (map attr spheres))
           low-point (apply min (map attr spheres))
           range (- (:max freq-range) (:min freq-range))]
       (/ (- high-point low-point) range));the freq-range is what's audiable in hz.
     (let [high-point (apply max (map attr sorted-spheres))
           low-point (apply min (map attr sorted-spheres))
           range (- (:max freq-range) (:min freq-range))]
       (/ (- high-point low-point) range))) 
   ))

(subscribe [::freq-rate])

(reg-sub
 ::freq-rate
 (fn [db]
   (get db :freq-rate)))

                                        

;;(subscribe [::freq-rate])

;;TEST CASES:

;; (let [selected-attr (subscribe [::selected-attr])
;;       sys-selected (subscribe [::selected-system])
;;       spheres (subscribed [::sorted-spheres])]
;;   )


;; (subscribe [::freq-rate])
;; (js/parseFloat (:min @(subscribe [::freq-range])))

;; (subscribe [::sorted-spheres])
;; (subscribe [::spheres])

(reg-sub
 ::envelope
 (fn [db]
   (get db :envelope)))



;(subscribe [::freq-rate])

;(apply min (map @(subscribe [::selected-attr]) @(subscribe [::spheres])))



;@(subscribe [::spheres])
;(remove (set non-numeric-keys) (keys (first @(subscribe [::spheres]))))
;(subscribe [::selected-attr])


;; (subscribe [::sorted-attempt])
;; (filter #(= @(subscribe [::selected-system]) (:name %)) @(subscribe [::spheres]))
;;(map :name @(subscribe [::selected-system-attr]))

;; (reg-sub
;;  ::selected-spheres
;;  (fn [db]
;;    (let
;;        [satelites (set (:satelites @(subscribe [::selected-system-attr])))
;;         spheres @(subscribe [::spheres])
;;         parent @(subscribe [::selected-system])]
;;       (sort-by :apoapsis
;;                (concat
;;                 (list  (assoc (first (filter #(= (:name %) parent) spheres)) :periapsis 0 :apoapsis 0)) 
;;                 (filter #(contains? satelites (:name %)) spheres))) 
;;      )))


;; (map :name @(subscribe [::selected-spheres]))
