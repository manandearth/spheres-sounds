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

  [:rect.system {:fill "#222"
                 :stroke "#838"
                 :stroke-width "0.3rem"
             ;; :color "#818"
                 }]

  [:.sphere {}
   [:&:hover {:stroke-width "3" :stroke "#2f2"}]]
  
  [:circle.sun {:fill "#ff6"}]
  [:circle.mercury {:fill "#ff9"}]
  [:circle.venus {:fill "#fd8"}]
  [:circle.earth {:fill "#19d"}]
  [:circle.mars {:fill "#833"}]
  [:circle.jupiter {:fill "#943"}]
  [:circle.saturn {:fill "#a81"}]
  [:circle.uranus {:fill "#17d"}]
  [:circle.neptune {:fill "#19d"}]
  [:circle.pluto {:fill "#533"}]  

  [:text.system {:font-family "Share Tech Mono, monospace"
                 :font-size "1.5rem"
                 :fill "#757"
                 :text-shadow "1px 1px #999"}
   [:&:hover {:fill "#f2f"}]
   ])



