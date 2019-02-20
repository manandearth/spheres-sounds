(ns spheres-sounds.subs
  (:require
   [re-frame.core :refer [reg-sub subscribe reg-fx]]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

;;RETURNS THE VALUE OF THE KEY :spheres (SEQ OF MAPS)
(reg-sub
 ::spheres
 (fn [db]
   (vals (:spheres db))))

;;RETURN NAMES OF ALL PARENTS IN ALL SYSTEMS (VECTOR OF STRINGS)
(reg-sub
 ::systems
 (fn [db]
   (:systems db)))

;;RETURNS NAME OF PARENT OF SELECTED-SYSTEM (STRING)
(reg-sub
 ::selected-system
 (fn [db]
   (:selected-system db)))

;;THE SPHERES INCLUDED IN HE SELECTED SYSTEM 
(reg-sub
 ::selected-system-attr
 (fn [db]
   (get-in db [:spheres (:selected-system db)])))

;;THE SPHERES INCLUDED IN THE SELECTED SYSTEM ORDERED BY APOAPSIS
(reg-sub
 ::sorted-spheres
 :<- [::spheres]
 :<- [::selected-system]
 (fn [[spheres parent] _]
   (sort-by :apoapsis (concat
                       (list (assoc (first (filter #(= parent (:name %)) spheres)) :periapsis (:self-bias parent) :apoapsis (:self-bias parent)))
                       (filter #(= parent (:parent %))  spheres)))))



(reg-sub
 ::selected-attr
 (fn [db]
   (:selected-attr db)))


;;MAP OF THE VALUES OF THE SELECTED ATTRIBUTE OF SPHERES OF SELECTED SYSTEM
(reg-sub
 ::toggled-attr
 :<- [::selected-attr]
 :<- [::sorted-spheres]
 (fn [[attr spheres] _]
   (map attr (filter :vis spheres))))

;;THE SPHERES IN THE SELECTED SYSTEM ORDERED BY THE SELECTED-ATTRIBUTE
(reg-sub
 ::selected-spheres
 :<- [::spheres]
 :<- [::selected-system]
 :<- [::selected-attr]
 (fn [[spheres parent attr] _]
   (sort-by attr
            (filter #(= true (:vis %)) (concat
                                        (list (assoc (first (filter #(= parent (:name %)) spheres)) :periapsis (:self-bias parent) :apoapsis (:self-bias parent)))
                                        (filter #(= parent (:parent %)) spheres))))))


;;THE ATTRIBUTES THAT CAN BE REPRESENTED IN THE GRAPH (NUMERIC)
(def non-numeric-keys [:parent :satelites :form :name :vis])


;;JUST A FUNKY WAY OF GETTING THE KEYS OF THE NUMERIC ATTRIBUTES IN ONE SEQUENCE...
(reg-sub
 ::attributes
 :<- [::spheres]
 (fn [spheres _]
   (remove (set non-numeric-keys) (keys (first spheres)))))

;;RETURNS BOOLEAN
(reg-sub
 ::global
 (fn [db]
   (:global db)))

;;RETURNS A MAP WITH THE KEYS: {:min <foo> :max <bar>}
(reg-sub
 ::freq-range
 (fn [db]
   (get db :freq-range)))

;;DONE make this a set-freq-rate! and add a :freq-rate to db
;;TODO this si not really any valid interpolation, just for visual representation of spheres in graph.
(reg-sub
 ::calc-freq-rate
 :<- [::spheres]
 :<- [::sorted-spheres]
 :<- [::global]
 :<- [::selected-attr]
 :<- [::freq-range]
 (fn [[spheres sorted-spheres global attr freq-range] _]
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


;;A FLOAT
(reg-sub
 ::freq-rate
 (fn [db]
   (get db :freq-rate)))

;;RETURN A VECTOR OF FIVE INTEGERS.
(reg-sub
 ::envelope
 (fn [db]
   (get db :envelope)))

;;RETURNS THE PRESSED KEY
(reg-sub
 ::pressed
 (fn [db]
   (get db :pressed)))

(subscribe [::envelope])
