(ns spheres-sounds.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [spheres-sounds.subs :as subs]
   [spheres-sounds.audio :as audio]
   [reagent.core :as r]
   ))

(defonce context (cljs-bach.synthesis/audio-context))

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
   ;; [:defs
   ;;       ;;mask for sun
   ;;       [:mask {:id "m1" :x "0" :y "0" :width "200%" :height "100%"}
   ;;        [:rect {:x x-position :width "1000" :height "100" :fill "white"}]]]
   ;[defs]
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
      [:circle.sphere.earth {:cx (+ x-position 240) :cy 50 :r 12 :filter "url(#f2)"
                             :on-click #(js/alert "earth")}]
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
  (for [sphere (rest @spheres)]
    [:circle.satelite {:key (str "circle " sphere)
                       :cx (+ 20 (/ (.log js/Math (:apoapsis sphere)) (/ apo-max 900)))
                       :cy 50
                       :r 3
                       :filter "url(#f2"}]))
     ]))

;; (.pow js/Math (apply max (map #(.log js/Math (:apoapsis %)) (rest @(subscribe [::subs/sorted-selected])))) 2)



(defn stage []
  [:div [:svg {:style {:width 1200 :height 250}}
         [:rect.system {:x 100 :y 20 :width 1000 :height 200}]
         [:svg  {:x 40 :width 1080 :height 300}

          (let [selected-system (subscribe [::subs/sorted-spheres])]
            (for [sphere @selected-system]
              (if (:vis sphere)
                [:g 
                 {:key (str "g-" sphere)
                  :on-click #(dispatch [:invisible (:name sphere)])}
                 [:rect.selected {:width 70 :height 20 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 50
                                  }]
                 [:text.spheres.visible {:x (* 81 (inc (.indexOf @selected-system sphere))) :y 65}
                  (:name sphere)]]
                [:g {:key (str "g-" sphere)
                     :on-click #(dispatch [:visible (:name sphere)])}
                 [:rect.system {:width 70 :height 20 :x (* 81 (inc (.indexOf @selected-system sphere))) :y 50}]
                 [:text.spheres {:x (* 81 (inc (.indexOf @selected-system sphere))) :y 65}
                  (:name sphere)]]
                )))]
         [selected-system-box]
         ]]
  )



(defn graph-tooltip
  "helper function defines tooltip text"
  [trans selected-attr body]
  [:g.tooltip
   [:rect {:x 700
           :y 0
           :width 200
           :height 20
           :opacity 0.4
           :rx 5
           :fill "#ccc"
           }
    ]
   [:text.tooltip {:x 700
                   ;(+ 20 trans)
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
   ]
  )


;;TODO working on keys...: 97 is '\a'
(defn a? [evt]
  (= (.-which evt) 97)) ;;\a

(defn todo-input []
  (let [title (r/atom "")]
    (fn []
      [:input#new-todo {,,,
                        :on-key-down #(when (a? %)
                                        (dispatch [:todos/add @title])
                                        (reset! title ""))}])))





(defn graph []
  [:svg {:width 1800 :x -200 :y 150}
   (let [spheres (subscribe [::subs/sorted-spheres])
         selected-attr @(subscribe [::subs/selected-attr])
         sys-selected @(subscribe [::subs/selected-system])
         adshr @(subscribe [::subs/envelope])
         ;;for the circles:
         min-val (apply min (map selected-attr (filter #(= true (:vis %)) @spheres)))
         max-val (apply max (map selected-attr (filter #(= true (:vis %)) @spheres)))
         fit  (/ max-val 900)
         ;;for the sound:
         ]
     [:g
      {:on-key-press #(when (= (.-which %) 97)
                                  (js/alert "pressed 'a'"))}
      (for [body @spheres
               :when (:vis body) ;toggle visability
               ]
           (let [trans (+ 350 (/ (selected-attr body) fit))
                 size (.log js/Math (:volume body))
                 freq-rate (subscribe [::subs/freq-rate])
                 adjustment 30
                 ]
             [:g {:key (str "g-" (:name body))
                                        ;:data-tooltip "boo"
                  :on-mouse-over #(dispatch [:audio adshr (+ adjustment (/ (selected-attr body) @freq-rate))])
                  }
              [:circle#graph
               {:r size
                :cx trans 
                :cy 100
                ;; :fill "#6666"
                :key (str "circle-" (:name body))
                }]
                                        ;[tooltip2 xtrans ytrans x-select y-select body]
              [:text#graph
               { :x trans
                :y 100
                :font-size 10
                :fill "#888888"
                :key (str "text-" (:name body))} (:name body)]
              [graph-tooltip trans selected-attr body]
              
              ]))])])


;; (dispatch [:audio @(subscribe
;;                    [::subs/envelope])
;;            (+ 30
;;               (/ (@(subscribe [::subs/selected-attr])
;;                   (first @(subscribe [::subs/spheres])))
;;                  @(subscribe [::subs/freq-rate])))])



;; (list (@(subscribe [::subs/selected-attr]) (nth @(subscribe [::subs/sorted-spheres]) 3)))

;; (map #(+ 30 (/ % @(subscribe [::subs/freq-rate]))) @(subscribe [::subs/toggled-attr]))

(defn chord
  []
  [:svg
   (let [toggled-on (subscribe [::subs/toggled-attr])
         freq-rate (subscribe [::subs/freq-rate])
         adjustment 30 ;30hz is the lowest audiable so the range is increased by that much for 0 to be audiable.
         toggled-reduced  (vec (map #(+ adjustment (/ % @freq-rate)) @toggled-on))
         adshr @(subscribe [::subs/envelope])]
     
     [:g {:key "chord"
          :cursor "pointer"
          :on-click #(dispatch [:chord adshr toggled-reduced])}
      [:rect.system {:x 550 :y 110 :width 100 :height 30}]
      [:text.system {:x 560 :y 130} "Chord"]])])

;; (vec (map #(+ 30 (/ % @(subscribe [::subs/freq-rate]))) @(subscribe [::subs/toggled-attr])))
;; (30
;;  30.0007007975774
;;  30.00112044523016 
;;  30.001773676627167 
;;  30.00313985121476)


;;(map #(/ % 100000) @(subscribe [::subs/toggled-apo]))
;; (0 698.169 1089.39 1520.98232 2492 4454.1 8166.2 15145 30080 72319000)
;; (map audio/dings (map #(/ % 100000) @(subscribe [::subs/toggled-apo])))

(defn attribute-selector []
  [:svg
   [:rect.system {:x 110 :y 50 :width 980 :height 50}]
   (let [attributes (subscribe [::subs/attributes])
         selected-attr @(subscribe [::subs/selected-attr])]
     (for [attr @attributes]
       (if (= attr selected-attr)
         [:g {:cursor "pointer" :on-click #(dispatch [:select-attribute attr])}
          [:rect.selected {:x (* 140 (inc (.indexOf @attributes attr)))  :y 60
                           :height 30 :width 100
                           }]
          [:text.spheres.visible {:x (* 140 (inc (.indexOf @attributes attr)))  :y 80
                           :height 30 :width 100
                           } attr]]
         [:g {:cursor "pointer" :on-click #(dispatch [:select-attribute attr])}
          [:rect.system  {:x (* 140 (inc (.indexOf @attributes attr)))  :y 60
                           :height 30 :width 100
                           }]
          [:text.spheres.system {:x (* 140 (inc (.indexOf @attributes attr)))  :y 80
                           :height 30 :width 100
                           } attr]]
         )))]
  )

(defn player []
  [:svg {:width 1200 :height 350}
   [:rect.system {:x 100 :y 50 :width 1000 :height 300}]
   [attribute-selector]
[graph]
   
   [chord]])

;; (subscribe [::subs/name])
;; (:satelites @(subscribe [::subs/selected-system-attr]))
;; (for [sphere @(subscribe [::subs/selected-spheres])] (:name sphere))

(def output-gain (r/atom {:gain "2.0"}))

(defn volume-slider []
  [:div.slider {:width 1000 :height 500}
   [:h2  {:style {:margin-left "340px"}} "-G-A-I-N-" ]
   [:input.slider {:type "range" :id "gain" :name "volume" :min "0" :max "2.0"  :step "0.1" 
                   :on-change (fn [e] (swap! output-gain assoc :gain (.-target.value e)))
                   }]
   [:h3 {:style {:width "100%" :margin-left "400px"}} (:gain @output-gain)]
   ;[try-me] 
   
   ])

(defn controls []
  [:div
   [:svg {:width 1200 :height :150}
    [:g
     [:rect.system {:x 100 :y 50 :width 1000 :height 40}]
     [:text.system {:y 80 :x 520} "-A-D-S-H-R-"]]
    ]
   [:div
    [:h3 "input"]]
   ;[volume-slider]
   ])


(defn envelope-input []
  (let [envelope (r/atom "boo!")
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
                                                              (rest (clojure.string/split @envelope #""))))])
                                ;      (reset! envelope [1 1 1 1 1])
                                      )
                               )}
       ])))




(defn footer []
  [:div.footer
   ;; {:style {:background "#ddd"
   ;;               :display "block"
   ;;           }}
   [:p
    {:style
     {:margin-left "100px"
      :padding-bottom "100px"}}
    "Made by"
    [:a {:href "https://github.com/manandearth"} " Adam Gefen, "]
    "A clojure developer, an open source under the " [:a {:href "https://opensource.org/licenses/Artistic-2.0"} "Apache Artistic License 2.0"]]])



(defn main-panel []
  (let [name (subscribe [::subs/name])
          spheres (subscribe [::subs/spheres])
        ]

    [:div {:on-key-down (fn [e] (when (= (.. e -which (getElementById "boo")) 13)
                                         (js/alert "hoo!")))
      :on-change (fn [e] (swap! output-gain assoc :gain (.-target.value e)))
           :title "hey"
           :id "boo"
           :style {:width 1150
                   :height 1000
                   :position "absolute"}}
     [:div.guide
      [:h1  @name "/interplanetary instrument"]
      [:h3 "Some of the bodies in our solar system are gigantic and they travel in greater speed than anything on this planet, yet they are silent. The following is an interactive exploration of the relation between some of those bodies through sound."]
      [:h2 "Select a system:"]
      ]
     [systems-menu]
     [systems-box]
     [:h2.guide "Toggle spheres on or off:"]
     [stage]
     [:h2.guide "Choose the attribute that will be used for frequency:"]
     [:h3.guide {:title "you will need headphones or some reasonable speakers as the tones tend to mostly be right at the edges.."} "This range will be set globally -> 30Hz the lowest and 12KHz the highest"]
     [player]
     [controls]
     [envelope-input]
     [footer]
     ]))


;(def now (js/Date.))

;
;; (let [me #js {:fname "Adam" :lname "Gefen"}]
;;   (.-lname me))
