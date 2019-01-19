(ns spheres-sounds.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [spheres-sounds.subs :as subs]
   ))

(defn systems-box []
  (let [x-position 100]
    [:svg.system {:x 0
                  :y 0
                  :height 120
                  :width 1100}
     [:defs
      [:filter
       ;;the filter size here defines the bounding box in which the effect will take place.
       {:id "f2" :x "-100%" :y "-100%" :width "300%" :height "300%"}
       [:feOffset
        {:result "offOut" :in "SourceGraphic" :dx "0" :dy "0"}]
       [:feGaussianBlur
        {:result "blurOut" :in "offOut" :stdDeviation "8"}]
       [:feBlend
        {:in "SourceGraphic" :in2 "blurOut" :mode "normal"}]]
      
      [:mask {:id "m1" :x "0" :y "0" :width "200%" :height "100%"}
       [:rect {:x x-position :width "1000" :height "100" :fill "white"}]]
      ]
     [:rect.system {:x x-position
                    :y 2
                    :width 1000
                    :height 100
                    }]
     [:circle.sphere.sun {:cx (+ x-position -120)
                          :cy 50
                          :r 200
                          :filter "url(#f2)"
                          :mask "url(#m1)"
                          }
      ]
     [:circle.sphere.mercury {:cx (+ x-position 150) :cy 50 :r 3 :filter "url(#f2)"}]
     [:circle.sphere.venus {:cx (+ x-position 190) :cy 50 :r 9 :filter "url(#f2)"}]
     [:circle.sphere.earth {:cx (+ x-position 240) :cy 50 :r 12 :filter "url(#f2)"
                            :on-click #(js/alert "earth")}]
     [:circle.sphere.mars {:cx (+ x-position 300) :cy 50 :r 7 :filter "url(#f2)"}]
     [:circle.sphere.jupiter {:cx (+ x-position 500) :cy 50 :r 42 :filter "url(#f2)"}]
     [:circle.sphere.saturn {:cx (+ x-position 650) :cy 50 :r 28 :filter "url(#f2)"}]
     [:circle.sphere.uranus {:cx (+ x-position 760) :cy 50 :r 20 :filter "url(#f2)"}]
     [:circle.sphere.neptune {:cx (+ x-position 860) :cy 50 :r 18 :filter "url(#f2)"}]
     [:circle.sphere.pluto {:cx (+ x-position 940) :cy 50 :r 5 :filter "url(#f2)"}]
     
     ]))

(defn systems-menu []
  [:svg {:style {:width 1200 :height 30
                 }}
   (let [systems @(subscribe [::subs/systems])
         selected-system @(subscribe [::subs/selected-system])]
     [:g (for [system systems]
           (if (= selected-system (clojure.string/capitalize system))
             [:g  {:style {:cursor "pointer"}}
                              
              [:rect.system {:x (+ 60 (* 140 (.indexOf systems system)))
                                 :y 0
                                 :width 120
                                 :height 30
                                 }]
               [:text.system {:x (+ 70 (* 140 (.indexOf systems system)))
                              :y 25
                              :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                              } (clojure.string/capitalize system)]
               ]
             [:g {:style {:cursor "pointer"}}
              [:rect.system {:x (+ 60 (* 140 (.indexOf systems system)))
                                 :y 0
                                 :width 120
                                 :height 30
                                 }]
               [:text.system {:x (+ 70 (* 140 (.indexOf systems system)))
                              :y 25
                              :on-click #(dispatch [:select-system (clojure.string/capitalize system)])
                             
                              } (clojure.string/capitalize system)]
               ]))])]
  )

(defn selected-system-box []
  (let [x-position 100
        parent @(subscribe [::subs/selected-system-attr])
        spheres @(subscribe [::subs/selected-spheres])
        ]
    [:svg.system {:x 0
                  :y 200
                  :height 120
                  :width 1100}
     [:defs
      [:filter
       ;;the filter size here defines the bounding box in which the effect will take place.
       {:id "f2" :x "-100%" :y "-100%" :width "300%" :height "300%"}
       [:feOffset
        {:result "offOut" :in "SourceGraphic" :dx "0" :dy "0"}]
       [:feGaussianBlur
        {:result "blurOut" :in "offOut" :stdDeviation "8"}]
       [:feBlend
        {:in "SourceGraphic" :in2 "blurOut" :mode "normal"}]]
      
      [:mask {:id "m1" :x "0" :y "0" :width "200%" :height "100%"}
       [:rect {:x x-position :width "1000" :height "100" :fill "white"}]]
      ]
     [:rect.system {:x x-position
                    :y 2
                    :width 1000
                    :height 100
                    }]
     [:circle.sphere {:cx (+ 100 (/ (.log js/Math (:volume parent)) 2 ))
                      :cy  50
                      ;;TODO I'm here with the pow!
                      :r  (/ (.pow js/Math (.log10 js/Math (:volume parent))3) 100) 
                      :fill (case (:name parent)
                              "Sun" "#ff6"
                              "Earth" "#19d"
                              "Mars" "#833"
                              "Jupiter" "#943"
                              "Saturn" "#a81"
                              "Uranus" "#19d"
                              "Neptune" "#17d"
                              "Pluto" "#533")
                      :filter "url(#f2)"
                      :mask "url(#m1)"}
      ]
     (let [apo-list (map #(.log js/Math (/ (+ (:periapsis %) (:apoapsis %)) 2)) spheres)
           apo-max (apply max (rest apo-list))
           apo-min (apply min (rest apo-list))
           apo-average  (/ (+ apo-max apo-min) 2)]
  (for [sphere (rest spheres)]
    [:circle.satelite {:cx (+ 20 (/ (.log js/Math (:apoapsis sphere)) (/ apo-max 1000)))
                       :cy 50
                       :r 3
                       :filter "url(#f2"}]))
     ]))

(.pow js/Math (apply max (map #(.log js/Math (:apoapsis %)) (rest @(subscribe [::subs/selected-spheres])))) 2)

(defn stage []
  [:svg {:style {:width 1200 :height 1050}}
   [:rect.system {:x 100 :y 20 :width 1000 :height 1000}]
   [:svg  {:x 10 :width 1080 :height 300}
    (let [selected-system @(subscribe [::subs/selected-spheres])]
      (for [sphere selected-system]
        [:g [:rect.system {:width 80 :height 20 :x (* 100 (inc (.indexOf selected-system sphere))) :y 100}]
         [:text.spheres {:x (* 100 (inc (.indexOf selected-system sphere))) :y 115}
          (:name sphere)]]))]
   [selected-system-box]]
  )

;; (subscribe [::subs/name])
;;(:satelites @(subscribe [::subs/selected-system-attr]))
;; (subscribe [::subs/selected-system])


(defn main-panel []
  (let [name (subscribe [::subs/name])
          spheres (subscribe [::subs/spheres])
        ]
    [:body {:style {:width 1200
                    :height 1000
                    :position "absolute"}}
     [:div.guide
      [:h1  @name "/interplanetary instrument"]
      [:h2 "Select a system:"]]
     [systems-box]
     [systems-menu]
     [:h2.guide "Toggle spheres on and off:"]
     [stage]
     ]))
