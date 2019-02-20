(ns spheres-sounds.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [spheres-sounds.subs :as subs]
   [spheres-sounds.audio :as audio]
   [reagent.core :as r]
   [cljs.spec.alpha :as s]
   [cljs.spec.test.alpha :as stest]
   [cljs-bach.synthesis :refer [audio-context]]
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]))


;;cljs-bach context
(defonce context audio-context)


;;keyboard listener:
(re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])

;;WIP position 
(def x-position 100)


(defn defs []
  [:svg [:defs
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
         ;; [:mask {:id "m1" :x "0" :y "0" :width "200%" :height "100%"}
         ;;  [:rect {:x x-position :width "1000" :height "100" :fill "white"}]]
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

         ]])

(defn sun []
  [:svg
   [:circle.sphere.sun {:cx (+ x-position -120)
                        :cy 50
                        :r 200
                        :filter "url(#f2)"
                        :mask "url(#m1)"
                        }
    ]])
(defn mercury []
  [:svg
   [:circle.sphere.mercury {:cx (+ x-position 150) :cy 50 :r 3 :filter "url(#f2)"}]])


(defn venus []
  [:svg
   [:circle.sphere.venus {:cx (+ x-position 190) :cy 50 :r 9 :filter "url(#f2)"}]])

(defn earth []
  [:g {:title "Hello-world!"}
      [:circle.sphere.earth {:cx (+ x-position 240) :cy 50 :r 12 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 250) :cy 45 :rx 10 :ry 7 :filter "url(#f2)" :mask "url(#m-earth)" :style {:fill "#3a4"}}]
      [:ellipse {:cx (+ x-position 230) :cy 55 :rx 10 :ry 7 :filter "url(#f2)" :mask "url(#m-earth)" :style {:fill "#3a4"}}]])

(defn mars []
  [:g
      [:circle.sphere.mars {:cx (+ x-position 300) :cy 50 :r 7 :filter "url(#f2)"}]
      [:circle.sphere {:cx (+ x-position 300) :cy 50 :r 4 :style {:fill "#b66"} :filter "url(#f3)"}]
      ])
(defn jupiter []
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
       [:ellipse {:cx (+ x-position 520) :cy 65 :rx 10 :ry 6 :style {:fill "#b53" :stroke "#bbb" :stroke-width "2px"}}]]])

(defn saturn []
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
     )

(defn uranus []
  [:g
      [:circle.sphere.uranus {:cx (+ x-position 760) :cy 50 :r 20 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.3"
                 :transform "rotate(100,860,50)" :mask "url(#m-uranus)"}]
      [:ellipse {:cx (+ x-position 760) :cy 50 :rx 30 :ry 10 :fill "none" :stroke-width "1px" :stroke "white" :opacity "0.5"
                 :transform "rotate(120,860,50)" :mask "url(#m-uranus)"}]]

     )

(defn neptune []
  [:g
      [:circle.sphere.neptune {:cx (+ x-position 860) :cy 50 :r 18 :filter "url(#f2)"}]
      [:ellipse {:cx (+ x-position 865) :cy 48 :rx 6 :ry 3 :filter "url(#f3)" :style {:fill "#33f"}}]]
     )

(defn pluto []
  [:circle.sphere.pluto {:cx (+ x-position 940) :cy 50 :r 5 :filter "url(#f2)"}]
     
     )




(def key-map {:number-vec [49 50 51 52 53 54 55 56 57 48]
              :qwerty-vec [81 87 69 82 84 89 85 73 79 80 219 221]
              :home-vec [65 83 68 70 71 72 74 75 76 186 192 222 90]
              :zxc-vec [90 88 67 86 66 78 77 188 190 191]
              })


(defn systems-box []
  (let [x-position 100]
    [:svg.system {:x 0
                  :y 0
                  :height 120
                  :width 1100}
     [defs]

     [:rect.system {:x x-position
                    :y 2
                    :width 1000
                    :height 100
                    }]
     [sun]
     [mercury]
     [venus]
     [earth]
     [mars]
     [jupiter]
     [saturn]
     [uranus]
     [neptune]
     [pluto]
     
     ]))

(defn systems-menu []
  [:svg {:style {:width 1200 :height 60
                 }}
   (let [systems @(subscribe [::subs/systems])
         selected-system @(subscribe [::subs/selected-system])
         number-vec (:number-vec key-map)
         number-key [1 2 3 4 5 6 7 8 9 0]]
     [:svg {:x 100}
      (for [system systems]
        (if (= selected-system (clojure.string/capitalize system))
          [:g  {:key (str system " selected") :style {:cursor "pointer"}}
           [:rect.selected {:x (* 127 (.indexOf systems system))
                          :y 0
                          :width 110
                          :height 60
                          }]
           [:text.selected {:x (* 127 (.indexOf systems system))
                          :y 25
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                            }  (clojure.string/capitalize system)
            ]
           [:text.system {:x (+ 32 (* 127 (.indexOf systems system)))
                          :y 50
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                            } (str "\n<" (nth number-key (.indexOf systems system)) ">")]
           ]
          [:g {:key (str system " not-selected") :style {:cursor "pointer"}}
           [:rect.system {:x (* 127 (.indexOf systems system))
                          :y 0
                          :width 110
                          :height 60
                          }]
           [:text.system {:x (* 127 (.indexOf systems system))
                          :y 25
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)])
                          
                          } (clojure.string/capitalize system)]
           [:text.system {:x (+ 32 (* 127 (.indexOf systems system)))
                          :y 50
                          :on-click #(dispatch [:select-system (clojure.string/capitalize system)]) 
                            } (str "\n<" (nth number-key (.indexOf systems system)) ">")]
           ]))])]
  )

(defn defs2 []
  [:svg [:defs
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
         ]])

(defn selected-system-box []
  (let [x-position 100
        parent (subscribe [::subs/selected-system-attr])
        spheres (subscribe [::subs/sorted-spheres])
        ]
    
    [:svg.system {:x 100
                  :y 100
                  :height 104
                  :width 1100}
     [defs2]
     [:rect.system {:x 0
                    :y 2
                    :width 1000
                    :height 100
                    }]
     
     [:svg
      (case (:name @parent)
        "Sun" [:svg {:x -100} [sun]]
        "Earth" [:g {:transform "translate (-2050 -250) scale (6 6)"} [earth]]
        "Mars" [:g {:transform "translate (-2800 -300) scale (7 7)"} [mars]]
        "Jupiter" [:g {:transform "translate (-3150 -250) scale (5 5)"} [jupiter]]
        "Saturn" [:g {:transform "translate (-3800 -250) scale (5 5)"} [saturn]]
        "Uranus" [:g {:transform "translate (-4300 -280) scale (5 5)"} [uranus]]
        "Neptune" [:g {:transform "translate (-4800 -180) scale (5 5)"} [neptune]]
        "Pluto" [:g {:transform "translate (-8330 -350) scale (8 8)"} [pluto]])]
     (let [apo-list (map #(.log js/Math (/ (+ (:periapsis %) (:apoapsis %)) 2)) @spheres)
           apo-max (apply max (rest apo-list))
           apo-min (apply min (rest apo-list))
           apo-average  (/ (+ apo-max apo-min) 2)]
  (for [sphere (rest @spheres)] ;;TODO need better representation for the satelites... (first not uniform)
    [:circle.satelite {:key (str "circle " sphere)
                       :cx (+ 20 (/ (.log js/Math (:apoapsis sphere)) (/ apo-max 900)))
                       :cy 50
                       :r 3
                       :filter "url(#f2"}]))
     ]))




(defn stage []
  [:div [:svg {:style {:width 1200 :height 210}}
         [:rect.system {:x 100 :y 20 :width 1000 :height 180}]
         [:svg  {:x 40 :width 1080 :height 180}

          (let [selected-system (subscribe [::subs/sorted-spheres])
                qwerty-vec (:qwerty-vec key-map)
                qwerty-keys [\q \w \e \r \t \y \u \i \o \p \[ \] ]]
            (for [sphere @selected-system]
              (if (:vis sphere)
                [:g 
                 {:key (str "g-" sphere)
                  :on-click #(dispatch [:visible (:name sphere)])}
                 [:rect.selected {:width 70 :height 50 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 50
                                  }]
                 [:text.spheres.visible {:x (+ 5 (* 81 (inc (.indexOf @selected-system sphere)))) :y 65}
                  (:name sphere)]
                 [:text.spheres {:x (+ 20 (* 81 (inc (.indexOf @selected-system sphere)))) :y 90}
                  (str "<" (nth qwerty-keys (.indexOf @selected-system sphere)) ">")]
                 ]
                [:g {:key (str "g-" sphere)
                     :on-click #(dispatch [:visible (:name sphere)])}
                 [:rect.system {:width 70 :height 50 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 50}]
                 [:text.spheres {:x (+ 5 (* 81 (inc (.indexOf @selected-system sphere)))) :y 65}
                  (:name sphere)]
                 [:text.spheres {:x (+ 20 (* 81 (inc (.indexOf @selected-system sphere)))) :y 90}
                  (str "<" (nth qwerty-keys (.indexOf @selected-system sphere)) ">")]]
                )))]
         [selected-system-box]
         ]]
  )

(defn global-button []
  [:svg 
   (let [global @(subscribe [::subs/global])
         freq-rate @(subscribe [::subs/freq-rate])
         calc-freq-rate (subscribe [::subs/calc-freq-rate])]
     (if global
       [:g {:on-click #(dispatch [:toggle-global])  
            }
        [:rect.selected {:x 120 :y 30
                            :height 40
                            :width 80
                         }]
        [:text.selected {:x 120 :y 60
                         :height 40
                         :width 50} "Global"]]
       [:g {:on-click #(dispatch [:toggle-global])}
        [:rect.system {:x 120 :y 30
                          :height 40
                          :width 80
                       }]
        [:text.system {:x 120 :y 60
                            :height 40
                       :width 50} "Local"]]))
   [:text.selected {:x 145 :y 90
                         :height 40
                         :width 50
                    :style {:font-size "1rem"}} "<\\>"]]
  
  )

(defn sliders []
  (let 
      [;; frequency-range (r/atom {:min "30" :max "12000"})
       freq-range (subscribe [::subs/freq-range])
       ]
    [:div.container 
     {:style {:display "inline-block"}}
     [:div.column  {:style {:transform "translate(-50px, -60px)"}}
      [:input.range-slider { :type "range" :id "min" :name "low-bar"
               :min "30" :max (:max @freq-range)  :step "1" :value (:min @freq-range)
               :on-change #(dispatch [:update-freq-range! :min (js/parseFloat (.-target.value %))])
               }]
      [:h3  {:style {:margin 0}} (str "min : " (:min @freq-range))]
      ]
     
     [:div.column {:style {:transform "translate(-20px, -60px)"}}
      [:input.range-slider {:type "range" :id "max" :name "high-bar"
               :min (:min @freq-range) :max "12000"  :step "1"
               :value (:max @freq-range)
               :on-change #(dispatch [:update-freq-range! :max (js/parseFloat (.-target.value %))])
               }]
      [:h3  {:style {:margin 0}} (str "max : " (:max @freq-range))]
      ]
     ]))

(defn ranges []
  [:div
  [global-button]
   [sliders]])


(defn interpolate [x]
  "We have two ranges, the selected frequency range and the range of value 
of selected attribute at a given state,which is filtered to its system or
remains global (:global db). x is the a value of an attribute, this function 
returns the frequency"
  (let [selected-attr @(subscribe [::subs/selected-attr])
        spheres (subscribe [::subs/spheres])
        sorted-spheres (subscribe [::subs/sorted-spheres])
        global @(subscribe [::subs/global])
        y-range (subscribe [::subs/freq-range])
        y-1 (:min @y-range)
        y-2 (:max @y-range)
        x-1-global (apply min (map selected-attr @spheres))
        x-1-local (apply min (map selected-attr @sorted-spheres))
        x-2-global (apply max (map selected-attr @spheres))
        x-2-local (apply max (map selected-attr @sorted-spheres))]
    (if global
      (+ y-1 (* (- y-2 y-1) (/ (- x x-1-global) (- x-2-global x-1-global))))
      (+ y-1 (* (- y-2 y-1) (/ (- x x-1-local) (- x-2-local x-1-local)))))
    ))

;;look at spec, son't know where to place it.

;; (s/fdef interpolate :args (s/cat :x number?))
;; (stest/instrument `interpolate)

;; (interpolate "e")
;; (interpolate 2)

;;example spec:
;; (defn same-number [x]
;;   x)
;; (s/fdef same-number :args (s/cat :x number?))
;; (stest/instrument `same-number)
;; (same-number 2)


(defn graph-tooltip
  "helper function defines tooltip text"
  [trans selected-attr body]
  (let [freq-rate (subscribe [::subs/freq-rate])
        adjustment (:min @(subscribe [::subs/freq-range]))] 
    [:g.tooltip
     [:rect {:x 700
             :y 0
             :width 200
             :height 20
             :opacity 0.4
             :rx 5
             :fill "#ccc"
             }]
     [:text.tooltip {:x 700
                     :y 15}
      (selected-attr body)
      (case selected-attr
        :apoapsis " km"
        :periapsis " km"
        :circumference " km"
        :mass " kg"
        :volume " km³"
        :orbital_period " days"
        :surface_area " km²" "")]

     [:rect {:x 700
             :y 20
             :width 200
             :height 20
             :opacity 0.4
             :rx 5
             :fill "#ccc"
             }]
     [:text.tooltip {:x 700
                     :y 35}
      (str (interpolate (selected-attr body)) " Hz")]])
  )

(defn graph []
  [:svg {:width 1800 :x -200 :y 150}
   (let [spheres @(subscribe [::subs/sorted-spheres])
         selected-attr @(subscribe [::subs/selected-attr])
         sys-selected @(subscribe [::subs/selected-system])
         adshr @(subscribe [::subs/envelope])
         selected-spheres @(subscribe [::subs/selected-spheres])
         ;;visual:
         min-val (apply min (map selected-attr (filter #(= true (:vis %)) spheres)))
         max-val (apply max (map selected-attr (filter #(= true (:vis %)) spheres)))
         fit  (/ max-val 900)
         home-keys [\a \s \d \f \g \h \j \k \l \; \' \# ]
         ]
     [:g
      (for [body spheres
               :when (:vis body) ;toggle visability
               ]
           (let [trans (+ 350 (/ (selected-attr body) fit)) ;visual
                 size (.log js/Math (:volume body)) ;visual
                 freq-rate (subscribe [::subs/calc-freq-rate])
                 freq-range @(subscribe [::subs/freq-range])
                 adjustment  (:min freq-range)
                 global @(subscribe [::subs/global])]
             [:g {:key (str "g-" (:name body))}
              [:circle#graph
               {:r size
                :cx trans 
                :cy 100
                ;; :fill "#6666"
                :key (str "circle-" (:name body))
                :on-mouse-over #(dispatch [:audio adshr (interpolate (selected-attr body))])
                }]
              [graph-tooltip trans selected-attr body]
              [:text#graph
               { :x trans
                :y 100
                :font-size 10
                :fill "#888888"
                :key (str "text-" (:name body))} (:name body)]
              [:text
               {:x (- trans 15)
                :y  170
                :font-size 20
                :fill "#fff"}
               (str "<" (nth home-keys (.indexOf selected-spheres body)) ">")
               ]]))])])

(defn chord
  []
  [:svg
   (let [toggled-on (subscribe [::subs/toggled-attr])
         freq-rate (subscribe [::subs/freq-rate])
         adjustment (:min @(subscribe [::subs/freq-range])) ;30hz is the lowest audiable so the range is increased by that much for 0 to be audiable.
         toggled-reduced  (vec (map #(interpolate %) @toggled-on))
         adshr @(subscribe [::subs/envelope])
         global (subscribe [::subs/global])]
     
     [:g {:key "chord"
          :cursor "pointer"
          :on-click #(dispatch [:chord adshr toggled-reduced])}
      [:rect.system {:x 550 :y 330 :width 100 :height 30}]
      [:text.system {:x 563 :y 353} "Chord"]])])


(defn attribute-selector []
  [:svg
   [:rect.system {:x 110 :y 50 :width 980 :height 50}]
   (let [attributes (subscribe [::subs/attributes])
         selected-attr @(subscribe [::subs/selected-attr])
         calc-freq-rate (subscribe [::subs/calc-freq-rate])
         freq-rate (subscribe [::subs/freq-rate])
         zxc-keys [\z \x \c \v \b \n \m \, \. \/ ]]
     (for [attr @attributes]
       (if (= attr selected-attr)
         [:g {:cursor "pointer"
              }
          [:rect.selected {:x (* 140 (inc (.indexOf @attributes attr)))  :y 60
                           :height 60 :width 100
                           }]
          [:text.spheres.visible {:x (* 140 (inc (.indexOf @attributes attr)))  :y 80
                           :height 30 :width 100
                                  } attr]
          [:text.spheres.system {:x (+ 35 (* 140 (inc (.indexOf @attributes attr))))  :y 110
                                  :height 30 :width 100}
           (str "<" (nth zxc-keys (.indexOf @attributes attr)) ">")]]
         [:g {:cursor "pointer" :on-click #(do (dispatch [:select-attribute attr])
                                               (dispatch [:update-freq-rate! @calc-freq-rate]))}
          [:rect.system  {:x (* 140 (inc (.indexOf @attributes attr)))  :y 60
                           :height 60 :width 100
                           }]
          [:text.spheres.system {:x (* 140 (inc (.indexOf @attributes attr)))  :y 80
                           :height 30 :width 100
                                 } attr]
          [:text.spheres.system {:x (+ 35 (* 140 (inc (.indexOf @attributes attr))))  :y 110
                                  :height 30 :width 100}
           (str "<" (nth zxc-keys (.indexOf @attributes attr)) ">")]]
         )))]
  )

(defn player []
  [:svg {:width 1200 :height 365}
   [:rect.system {:x 100 :y 50 :width 1000 :height 300}]
   [attribute-selector]
   [graph]
   [chord]])


(def output-gain (r/atom {:gain "2.0"}))

(defn volume-slider []
  [:div.slider {:width 1000 :height 500}
   [:h2  {:style {:margin-left "340px"}} "-G-A-I-N-" ]
   [:input.slider {:type "range" :id "gain" :name "volume" :min "0" :max "2.0"  :step "0.1" 
                   :on-change (fn [e] (swap! output-gain assoc :gain (.-target.value e)))
                   }]
   [:h3 {:style {:width "100%" :margin-left "400px"}} (:gain @output-gain)]
   ])

(defn controls []
  [:div
   [:svg {:width 1200 :height :150}
    [:g
     [:rect.system {:x 100 :y 50 :width 1000 :height 40}]
     [:text.system {:y 80 :x 520} "-A-D-S-H-R-"]]
    ]
   [:div
    [:h3.guide "envelope (temporarily specless) enter 5 integers (i.e. 01010)"]]
   ;[volume-slider]
   ])


(defn envelope-input []
  (let [envelope (r/atom "01010")
        env @(subscribe [::subs/envelope])]
    (fn []
      [:input {:type "text"
               :value @envelope
               :auto-focus true
               :placeholder "a s d h r"
               :on-change #(reset! envelope (-> % .-target .-value))
               :on-key-press (fn [e] (when (= (.-which e) 13)
                                       (dispatch [:set-envelope!
                                                  (vec (map js/parseInt
                                                              (rest (clojure.string/split @envelope #""))))])))}])))

(defn keydown-rules []
  (let [selected-attr @(subscribe [::subs/selected-attr])
        spheres @(subscribe [::subs/sorted-spheres])
        adshr @(subscribe [::subs/envelope])
        homerow-vec (:home-vec key-map)
        ordered-spheres (sort-by selected-attr (filter #(= (:vis %) true) spheres))
        systems @(subscribe [::subs/systems])
        selected-system @(subscribe [::subs/selected-system])
        numbers-vec (:number-vec key-map)
        selected-system-attr @(subscribe [::subs/selected-system-attr])
        qwerty-vec (:qwerty-vec key-map)
        attrs @(subscribe [::subs/attributes])
        zxc-vec (:zxc-vec key-map)]
    {:event-keys
     
     (into
      (into
       (into
        (conj
         (into []
               (for [body ordered-spheres]
                 [[:audio adshr (interpolate (selected-attr body))]
                  [{:keyCode (nth homerow-vec (.indexOf ordered-spheres body))}]]))
         
         [[:toggle-global]
          [{:keyCode 220}]       ;</>
          [{:keyCode 220         ;<|>
            :shiftKey true}]])
        
        (for [system systems]
          [[:select-system (clojure.string/capitalize system)]
           [{:keyCode (nth numbers-vec (.indexOf systems system))}]]))
       
       (for [sphere spheres]
         [[:visible (:name sphere)]
          [{:keyCode (nth qwerty-vec (.indexOf spheres sphere))}]]))
      
      (for [attr attrs]
        [[:select-attribute attr]
         [{:keyCode (nth zxc-vec (.indexOf attrs attr))}]])
      )
     
     
     }))

;;TODO make the key visible when pressed.

(defn keyboard []
  (dispatch
   [::rp/set-keydown-rules
     (keydown-rules)
    
      ]))




;; (defn key-map []
;;   (dispatch
;;    [::rp/set-keydown-rules
;;     {;; takes a collection of events followed by key combos that can trigger the event
;;      :event-keys
;;      [
;;       ;; Event & key combos 1
;;       [;; this event
;;        [:toggle-global] ; will be triggered if
;;        [{:keyCode 220}] ; enter
;;        [{:keyCode 220  ; or delete
;;          :shiftKey true}]] ; is pressed
      
;;       ;; Event & key combos 2
;;       [; this event will be triggered if
;;        [:toggle-global]   ;tab is pressed twice in a row
;;        [{:keyCode 9} {:keyCode 9}]]
;;       ]
     
;;      ;; takes a collection of key combos that, if pressed, will clear
;;      ;; the recorded keys
;;      :clear-keys
;;      ;; will clear the previously recorded keys if
;;      [;; escape
;;       [{:keyCode 27}]
;;       ;; or Ctrl+g
;;       [{:keyCode   71
;;         :ctrlKey true}]]
;;      ;; is pressed
     
;;      ;; takes a collection of keys that will always be recorded
;;      ;; (regardless if the user is typing in an input, select, or textarea)
;;      :always-listen-keys
;;      ;; will always record if
;;      [;; enter
;;       {:keyCode 13}]
;;      ;; is pressed
     
;;      ;; takes a collection of keys that will prevent the default browser
;;      ;; action when any of those keys are pressed
;;      ;; (note: this is only available to keydown)
;;      :prevent-default-keys
;;      ;; will prevent the browser default action if
;;      [;; Ctrl+g
;;       {:keyCode   71
;;        :ctrlKey true}]
;;      ;; is pressed
;;      }]))

(defn footer []
  [:div.footer
   [:p
    {:style
     {:margin-left "100px"
      :padding-bottom "100px"}}
    "Made by"
    [:a {:href "https://github.com/manandearth"} " Adam Gefen, "]
    "A clojure developer, an open source under the " [:a {:href "https://opensource.org/licenses/Artistic-2.0"} "Apache Artistic License 2.0"]]])

(defn main-panel []
  (let [name (subscribe [::subs/name])
          spheres (subscribe [::subs/spheres])]

    [:div
     [:div.guide {:style {:width 1000}}
      [:h1  @name "/interplanetary instrument"]
      [:h3 "Some of the bodies in our solar system are gigantic and they travel in greater speed than anything on this planet, yet they are silent. The following is an interactive exploration of the relation between some of those bodies through sound."]
      [:h2 {:style {:margin-bottom -5}}
       "Select a system:"]
      ]
     [systems-menu]
     [systems-box]
     [:h3.guide {:style {:margin-top -15 :margin-bottom -15}}
      "Toggle spheres on or off:"]
     [stage]
     [:h3.guide {:title "you will need headphones or some reasonable speakers as the tones tend to mostly be right at the edges.."
                 :style {:margin -8}}
      "Toggle" [:span {:style {:color "sandybrown"}} " <Global/Local>"] " and set the range -> 30Hz the lowest and 12KHz the highest"]
     [ranges]
[:h2.guide {:style {:margin-top -40 :margin-bottom -25}}
      "Choose the attribute that will be used for frequency:"]
     [player]
     [controls]
     [envelope-input]
     [footer]
     (keyboard)

     ]
    ))
