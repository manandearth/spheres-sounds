(ns spheres-sounds.css
  (:require [garden.def :refer [defstylesheet]]
            [garden.selectors :as s]))

(defstylesheet screen
  {:output-to "resources/public/css/screen.css"}
  [:body {:background "black"}]
   

  
  [:h1 {:font-size "3rem"
        :font-family "Share Tech Mono, monospace"
        :color "#773"
        :text-shadow "2px 2px #744"
        :margin-top "0"
        :margin-bottom "20px"
        }
   ]
  [:h2 {:font-size "2rem"
        :font-family "Share Tech Mono, monospace"
        :color "#168"
        :text-shadow "1px 1px #777"}]

  [:h3 {:font-size "1.5rem"
        :font-family "Share Tech Mono, monospace"
        :color "#825"
        :text-shadow "0.3px 0.3px #770"}]


  [:rect.system {:fill "#222"
                 :stroke "#838"
                 :stroke-width "0.3rem"
             ;; :color "#818"
                 }]

  [:rect.selected {:fill "#444"
                 :stroke "#f2f"
                 :stroke-width "0.3rem"
             ;; :color "#818"
                 }]
  
  [:.guide {:width 1200 :padding-left "100px"}
   ]
  [:.sphere {}
   [:&:hover {:stroke-width "3" :stroke "#2f2"}]]
  
  [:circle.sun {:fill "#ff6"}]
  [:circle.mercury {:fill "#ff9"}]
  [:circle.venus {:fill "#fd8"}]
  [:circle.earth {:fill "#19d"}]
  [:circle.mars {:fill "#833"}]
  [:circle.jupiter {:fill "#975"}]
  [:circle.saturn {:fill "#ec5"}]
  [:circle.uranus {:fill "#19d"}]
  [:circle.neptune {:fill "#15d"}]
  [:circle.pluto {:fill "#533"}]  
  [:circle.satelite {:fill "#777"}]
  [:text.system {:font-family "Share Tech Mono, monospace"
                 :font-size "1.5rem"
                 :fill "#757"
                 :text-shadow "1px 1px #999"}
   [:&:hover {:fill "#f2f"}]
   ]

  [:text.selected {:font-family "Share Tech Mono, monospace"
                   :font-size "1.5rem"
                   :fill "#f2f"
                   :text-shadow "0.1px 0.1px #999"}]

  [:text.spheres {:font-family "Share Tech Mono, monospace"
                  :font-size "1rem"
                  :fill "#44a"
                  :text-shadow "0.1px 0.1px #bbb"
                  :cursor "pointer"}
   [:&:hover {:fill "#f2f"}]]

  ;[(s/+ :text.spheres:hover :circle.earth) {:fill "#fff"}]

  [:text.spheres.visible {:font-family "Share Tech Mono, monospace"
                          :font-size "1rem"
                          :fill "#f2f"
                          :text-shadow "0.1px 0.1px #bbb"
                          :cursor "pointer"}
   ]

  [:text#graph   {:font-family "Share Tech Mono, monospace"
                  :font-size "1rem"
                  :fill "#44a"
                  :text-shadow "0.1px 0.1px #bbb"
                  :cursor "pointer"}]
  
  [:text#graph.not-pressed {:fill "#aaa"}]
  [:text#graph.pressed {:fill "#ccc"
                        :font-size "1.1rem"}]
  
  
  [:circle#graph {:fill "#bbb"
                  :opacity 0.5
                  :cursor "pointer"}
   [:&:hover {:fill "#a8a"}]]
  
  [(s/+ :circle#graph:hover :.tooltip) {
                                           ;;:opacity 1
                                           :display "inline"}]


  [(s/+ :text#graph:hover :.tooltip) {
                                           ;;:opacity 1
                                           :display "inline"}]

  [:g.tooltip {:cursor "pointer"
               :display "none"}
   ]


  [:text.tooltip {:font-family "Share Tech Mono, monospace"
                  :font-size "1rem"
                  :fill "#111"
                  :text-shadow "0.1px 0.1px #bbb"
                  :cursor "pointer"
                  }
   ]

  [:.slider {:display "inline-block"
             :width "500px"
             :height "20px"
             :padding "20px"
             :position "relative"
             :margin-left "150px"
             :cursor "pointer"
             }
   ]

  [:.range-slider {:opacity 0.5
                   :margin 0
                   :width "300px"
                   }
   [:&:hover {:opacity 1}]
   ]
  
  [:p {:font-family "Share Tech Mono, monospace"
       :font-size "1.3rem"
       :color "#a2a"
       :text-shadow "0.1px 0.1px #bbb"
       }]

  [:.attack {:color "#aea" :display "inline"}]
  [:.decay {:color "#ec9" :display "inline"}]
  [:.sustain {:color "#79f" :display "inline"}]
  [:.hold {:color "79f" :display "inline"}]
  [:.release {:color "#a9f" :display "inline"}]
 
  [:.container {:overflow "hidden"}]
  
  [:.column {:float "left"
             :margin "60px"
             :background-color "none"
             :padding-bottom "100%"
             :margin-bottom "-100%"
             }
   ]

;; [:.envelope {:font-family "Share Tech Mono, monospace"
;;                    :font-size "1.5rem"
;;                    :fill "#fff"
;;                  :text-shadow "0.1px 0.1px #999"
;;                  :opacity 1
;;                  }]

  [:.envelope {:border 0
               :background "#222"
               :font-size "20px"
               :color "#aaa"
               :width "30px"
               :display "inline"}]
  )


