(ns spheres-sounds.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [spheres-sounds.subs :as subs]
   [spheres-sounds.audio :as audio]
   
   ))

(defonce context (cljs-bach.synthesis/audio-context))


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

      [:mask {:id "m-saturn"}
       ;;TODO need to reverse the mask on Saturn
       [:rect {:x (+ x-position 580) :y 0 :width 200 :height 200 :fill "white"}]
       [:circle {:cx (+ x-position 650) :cy 50 :r 28 :fill "black"}]
       [:rect {:x (+ x-position 580) :y 50 :width 200 :height 30 :fill "white"}]
       ]
      [:mask {:id "m-uranus"}
       ;;TODO need to reverse the mask on Saturn
       [:rect {:x (+ x-position 700) :y 0 :width 200 :height 200 :fill "white"}]
       [:circle {:cx (+ x-position 760) :cy 50 :r 20 :fill "black"}]
       [:rect {:x (+ x-position 700) :y 50 :width 200 :height 30 :fill "white"}]
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
      [:g {:mask "url(#m-jupiter)"}
       [:circle {:cx (+ x-position 500) :cy -100 :r 120:stroke "#bbb" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 135 :stroke "#f66" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 145 :stroke "#f99" :stroke-width "1px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 155 :stroke "#f88" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 158 :stroke "#bbb" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 172 :stroke "#f55" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 188 :stroke "#f88" :stroke-width "7px" :fill "none"}]
       [:circle {:cx (+ x-position 500) :cy -100 :r 200 :stroke "#bbb" :stroke-width "7px" :fill "none"}]
       [:ellipse {:cx (+ x-position 520) :cy 65 :rx 10 :ry 6 :style {:fill "#b53" :stroke "#bbb" :stroke-width "2px"}}]]]

     [:g {:transform "translate (80 -300) rotate(25 20 0)"} 
      [:circle.sphere.saturn {:cx (+ x-position 650) :cy 50 :r 28 :filter "url(#f2)"}]
      [:svg {:height 1000 
             ;; :filter "url(#f2)"
             }
       [:ellipse {:cx (+ x-position 650) :cy 50 :rx 50 :ry 20
                  :stroke  "#db4"`
                  ;; "url(#saturn-gradient)"
                  :stroke-width 10 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   :filter "url(#f3)" 
                  }]
       [:ellipse {:cx (+ x-position 650) :cy 50 :rx 40 :ry 15
                  :stroke  "#dd9"
                  ;; "url(#saturn-gradient)"
                  :stroke-width 10 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   ;; :filter "url(#f3)" 
                  }]
       [:ellipse {:cx (+ x-position 650) :cy 50 :rx 48 :ry 19
                  :stroke  "#333"
                  ;; "url(#saturn-gradient)"
                  :stroke-width 1 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   ;; :filter "url(#f3)" 
                  }]
       [:ellipse {:cx (+ x-position 650) :cy 50 :rx 36 :ry 10
                  :stroke  "#333"
                  ;; "url(#saturn-gradient)"
                  :stroke-width 1 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   :filter "url(#f3)" 
                  }]
       [:ellipse {:cx (+ x-position 650) :cy 50 :rx 54 :ry 24
                  :stroke  "#b73"
                  ;; "url(#saturn-gradient)"
                  :stroke-width 3 :fill "none" :opacity "1" :mask "url(#m-saturn)"
                   :filter "url(#f3)" 
                   }]]]
     [:g
      [:circle.sphere.uranus {:cx (+ x-position 760) :cy 50 :r 20 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.3"
                 :transform "rotate(100,860,50)" :mask "url(#m-uranus)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.5"
                 :transform "rotate(120,860,50)" :mask "url(#m-uranus)"}]]

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
          [:g  {:key (str system " selected") :style {:cursor "pointer"}}
           [:rect.selected {:x (* 127 (.indexOf systems system))
                          :y 0
                          :width 110
                          :height 30
                          }]
           [:text.selected {:x (* 127 (.indexOf systems system))
                          :y 25
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                          } (clojure.string/capitalize system)]
           ]
          [:g {:key (str system " not-selected") :style {:cursor "pointer"}}
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
        parent (subscribe [::subs/selected-system-attr])
        spheres (subscribe [::subs/sorted-spheres])
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
     [:circle.sphere {:cx (+ 100 (/ (.log js/Math (:volume @parent)) 2 ))
                      :cy  50
                      ;;TODO I'm here with the pow!
                      :r  (/ (.pow js/Math (.log10 js/Math (:volume @parent))3) 100) 
                      :fill (case (:name @parent)
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
     (let [apo-list (map #(.log js/Math (/ (+ (:periapsis %) (:apoapsis %)) 2)) @spheres)
           apo-max (apply max (rest apo-list))
           apo-min (apply min (rest apo-list))
           apo-average  (/ (+ apo-max apo-min) 2)]
  (for [sphere (rest @spheres)]
    [:circle.satelite {:key (str "circle " sphere)
                       :cx (+ 20 (/ (.log js/Math (:apoapsis sphere)) (/ apo-max 1000)))
                       :cy 50
                       :r 3
                       :filter "url(#f2"}]))
     ]))

;; (.pow js/Math (apply max (map #(.log js/Math (:apoapsis %)) (rest @(subscribe [::subs/sorted-selected])))) 2)

(defn try-me
  []
  [:svg
   (let [toggled-on (subscribe [::subs/toggled-apo])
         toggled-reduced (map #(/ % 1000000) @(subscribe [::subs/toggled-apo]))]
     
     [:g {:key "try-me"
          :cursor "pointer"
          :on-click #(dispatch [:audio toggled-reduced])}
      [:rect.system {:x 500 :y 350 :width 100 :height 30}]
      [:text.system {:x 507 :y 370} "try-me"]])])

;; (map #(/ % 100000) @(subscribe [::subs/toggled-apo]))
;; (0 698.169 1089.39 1520.98232 2492 4454.1 8166.2 15145 30080 72319000)
;(map audio/dings (map #(/ % 100000) @(subscribe [::subs/toggled-apo])))

(defn stage []
  [:svg {:style {:width 1200 :height 1050}}
   [:rect.system {:x 100 :y 20 :width 1000 :height 1000}]
   [:svg  {:x 40 :width 1080 :height 300}

    (let [selected-system (subscribe [::subs/sorted-spheres])]
      (for [sphere @selected-system]
        (if (:vis sphere)
          [:g 
           {:key (str "g-" sphere)
            :on-click #(dispatch [:invisible (:name sphere)])}
           [:rect.selected {:width 70 :height 20 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 100
                            }]
           [:text.spheres.visible {:x (* 81 (inc (.indexOf @selected-system sphere))) :y 115}
            (:name sphere)]]
          [:g {:key (str "g-" sphere)
               :on-click #(dispatch [:visible (:name sphere)])}
           [:rect.system {:width 70 :height 20 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 100}]
           [:text.spheres {:x (* 81 (inc (.indexOf @selected-system sphere))) :y 115}
            (:name sphere)]]
          )))]
   [selected-system-box]
   [try-me]

   ]
  )

;; (subscribe [::subs/name])
;;(:satelites @(subscribe [::subs/selected-system-attr]))
;; (for [sphere @(subscribe [::subs/selected-spheres])] (:name sphere))


(defn main-panel []
  (let [name (subscribe [::subs/name])
          spheres (subscribe [::subs/spheres])
        ]
    [:div {:style {:width 1200
                    :height 1000
                    :position "absolute"}}
     [:div.guide
      [:h1  @name "/interplanetary instrument"]
[:h3 "Some of the bodies in our solar system are gigatic and they travel in greater speed than anything on this planet, yet the are silent. The following is an interactive exploration of the relation between some of those bodies through sound."]
      [:h2 "Select a system:"]]
     [systems-menu]
     
     [systems-box]
     [:h2.guide "Toggle spheres on and off:"]
     [stage]
     ]))
