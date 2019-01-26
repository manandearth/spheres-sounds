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
 ::freq-rate
 :<- [::spheres]
 :<- [::selected-attr]
 (fn [[spheres attr] _]
   (let [high-point (apply max (map attr spheres))
         low-point (apply min (map attr spheres))
         freq-range 11970]
     (/ (- high-point low-point) freq-range)) ;the freq-range is what's audiable in hz.
   ))

(subscribe [::freq-rate])



(apply min (map @(subscribe [::selected-attr]) @(subscribe [::spheres])))



@(subscribe [::spheres])
(remove (set non-numeric-keys) (keys (first @(subscribe [::spheres]))))
(subscribe [::selected-attr])

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
