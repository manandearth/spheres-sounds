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

      [:filter
       ;;the filter size here defines the bounding box in which the effect will take place.
       {:id "f3" :x "-100%" :y "-100%" :width "300%" :height "300%"}
       [:feOffset
        {:result "offOut" :in "SourceGraphic" :dx "0" :dy "0"}]
       [:feGaussianBlur
        {:result "blurOut" :in "offOut" :stdDeviation "2"}]
       [:feBlend
        {:in "SourceGraphic" :in2 "blurOut" :mode "normal"}]]

      ;;mask for sun
      [:mask {:id "m1" :x "0" :y "0" :width "200%" :height "100%"}
       [:rect {:x x-position :width "1000" :height "100" :fill "white"}]]
      ;;mask for jupiter
      [:mask {:id "m-jupiter" :x "0" :y "0" :width "200%" :height "100%"}
       [:circle {:cx (+ x-position 500) :cy 50 :r 42 :fill "white"}]]
      ;;mask for earth
      [:mask {:id "m-earth" :x "0" :y "0" :width "200%" :height "100%"}
       [:circle {:cx (+ x-position 240) :cy 50 :r 12 :fill "white"}]]
      ;;masks for saturn

      [:mask {:id "punch-saturn"}
       [:circle {:cx (+ x-position 650) :cy 50 :r 28 :fill "black" 
                 ;:mask "url(#saturn-helper)"
                 }]]
      
      [:mask {:id "saturn-helper"}
       [:rect {:x (+ x-position 620) :y 50 :width 60 :height 40 :fill "white"}]
       ]

      [:mask {:id "m-saturn"}
       ;;TODO need to reverse the mask on Saturn
       [:rect {:x (+ x-position 580) :y 0 :width 200 :height 200 :fill "white"}]
       [:circle {:cx (+ x-position 650) :cy 50 :r 28 :fill "black"}]
       [:rect {:x (+ x-position 580) :y 50 :width 200 :height 30 :fill "white"}]
       ]

      
      [:linearGradient {:id "saturn-gradient" :x (+ x-position 25) :y 480 :width 400 :height 400 :gradientTransform "rotate(90, 0, 0)" }
       [:stop {:offset "0%"   :stop-color "rgba(255, 255, 255, 0)"}]
       [:stop {:offset "100%" :stop-color "rgba(255, 255, 255, 255)"}]]

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
     [:g
      [:circle.sphere.earth {:cx (+ x-position 240) :cy 50 :r 12 :filter "url(#f2)"
                             :on-click #(js/alert "earth")}]
      [:ellipse {:cx (+ x-position 250) :cy 45 :rx 10 :ry 7 :filter "url(#f2)" :mask "url(#m-earth)" :style {:fill "#3a4"}}]
      [:ellipse {:cx (+ x-position 230) :cy 55 :rx 10 :ry 7 :filter "url(#f2)" :mask "url(#m-earth)" :style {:fill "#3a4"}}]]
     [:g
      [:circle.sphere.mars {:cx (+ x-position 300) :cy 50 :r 7 :filter "url(#f2)"}]
      [:circle.sphere {:cx (+ x-position 300) :cy 50 :r 4 :style {:fill "#b66"} :filter "url(#f3)"}]
      ]
     [:g 
      [:circle.sphere.jupiter {:cx (+ x-position 500) :cy 50 :r 42 :filter "url(#f2)"}]
      [:g {:mask "url(#m-jupiter)"} [:line {:x1 (+ x-position 450) :y1 20 :x2 (+ x-position 550) :y2 20 :style  {:stroke "#f33" :stroke-width "7px"}}]
       [:line {:x1 (+ x-position 450) :y1 20 :x2 (+ x-position 550) :y2 20 :filter "url(#f2)" :style  {:stroke "#963" :stroke-width "7px"}}]
       [:line {:x1 (+ x-position 450) :y1 30 :x2 (+ x-position 550) :y2 30 :style  {:stroke "#bbb" :stroke-width "8px"}}]
       [:line {:x1 (+ x-position 450) :y1 40 :x2 (+ x-position 550) :y2 40 :style  {:stroke "#f99" :stroke-width "4px"}}]
       [:line {:x1 (+ x-position 450) :y1 45 :x2 (+ x-position 550) :y2 45 :style  {:stroke "#f55" :stroke-width "3px"}}]
       [:line {:x1 (+ x-position 450) :y1 50 :x2 (+ x-position 550) :y2 50 :style  {:stroke "#bbb" :stroke-width "8px"}}]
       [:line {:x1 (+ x-position 450) :y1 55 :x2 (+ x-position 550) :y2 55 :style  {:stroke "#f55" :stroke-width "7px"}}]
       [:line {:x1 (+ x-position 450) :y1 60 :x2 (+ x-position 550) :y2 60 :style  {:stroke "#f88" :stroke-width "7px"}}]
       [:line {:x1 (+ x-position 450) :y1 70 :x2 (+ x-position 550) :y2 70 :style  {:stroke "#bbb" :stroke-width "8px"}}]
       [:line {:x1 (+ x-position 450) :y1 75 :x2 (+ x-position 550) :y2 75 :style  {:stroke "#bbb" :stroke-width "8px"}}]
       [:line {:x1 (+ x-position 450) :y1 85 :x2 (+ x-position 550) :y2 85 :style  {:stroke "#f66" :stroke-width "7px"}}]
       [:ellipse {:cx (+ x-position 520) :cy 65 :rx 10 :ry 6 :style {:fill "#b53" :stroke "#bbb" :stroke-width "2px"}}]]]

     [:g
      [:circle.sphere.saturn {:cx (+ x-position 650) :cy 50 :r 28 :filter "url(#f2)"}]
      [:svg {:height 1000
             ;; :filter "url(#f2)"
                   } [:ellipse {:cx (+ x-position 650) :cy 50 :rx 50 :ry 20
                                      :stroke
                   ;; "url(#saturn-gradient)"
                   "#dc9"
                   :stroke-width 10 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   :filter "url(#f3)" 
                   }]]]
     [:g
      [:circle.sphere.uranus {:cx (+ x-position 760) :cy 50 :r 20 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.3"
                 :transform "rotate(100,860,50)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.5"
                 :transform "rotate(120,860,50)"}]]
     [:g
      [:circle.sphere.neptune {:cx (+ x-position 860) :cy 50 :r 18 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 865) :cy 48 :rx 6 :ry 3 :filter "url(#f3)" :style {:fill "#33f"}}]]
     [:circle.sphere.pluto {:cx (+ x-position 940) :cy 50 :r 5 :filter "url(#f2)"}]
     
     ]))

(defn systems-menu []
  [:svg {:style {:width 1200 :height 30
                 }}
   (let [systems @(subscribe [::subs/systems])
         selected-system @(subscribe [::subs/selected-system])]
     [:svg {:x 100}
      (for [system systems]
        (if (= selected-system (clojure.string/capitalize system))
          [:g  {:style {:cursor "pointer"}}
           
           [:rect.system {:x (* 127 (.indexOf systems system))
                          :y 0
                          :width 110
                          :height 30
                          }]
           [:text.system {:x (* 127 (.indexOf systems system))
                          :y 25
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                          } (clojure.string/capitalize system)]
           ]
          [:g {:style {:cursor "pointer"}}
           [:rect.system {:x (* 127 (.indexOf systems system))
                          :y 0
                          :width 110
                          :height 30
                          }]
           [:text.system {:x (* 127 (.indexOf systems system))
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
   [:svg  {:x 40 :width 1080 :height 300}
    (let [selected-system @(subscribe [::subs/selected-spheres])]
      (for [sphere selected-system]
        [:g [:rect.system {:width 70 :height 20 :x (* 81 (inc (.indexOf selected-system sphere))) :y 100}]
         [:text.spheres {:x (* 81 (inc (.indexOf selected-system sphere))) :y 115}
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
