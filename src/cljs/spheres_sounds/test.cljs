(ns spheres-sounds.test
  (:require  [cljs.test :as t :include-macros true]
             [re-frame.core :refer [subscribe dispatch]]
             [spheres-sounds.views :as v]
             [spheres-sounds.subs :as subs]))



(t/deftest interpolate
  "checks that the interpolated values stay in the limit (30-12000)"
  (let [toggled-on (subscribe [::subs/toggled-attr])
        toggled-reduced  (map #(v/interpolate %) @toggled-on)]
    (t/testing "values being interpolated"
      (t/is (every? #(< 29 % 12001) toggled-reduced)))))


(let [toggled-on (subscribe [::subs/toggled-attr])
        toggled-reduced  (map #(v/interpolate %) @toggled-on)]
  (every? #(< 1000 % 2000) toggled-reduced))


(every? #(< 29 % 12001) (map #(v/interpolate %) @(subscribe [::subs/toggled-attr])))
