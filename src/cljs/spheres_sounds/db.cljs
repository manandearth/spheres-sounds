(ns spheres-sounds.db
  (:require [spheres-sounds.records :refer [spheres-map]]
            ;[cognitect.transit :refer [reader writer read]]
            ;; [cljs.reader :as edn]
            )
  )

(def default-db
  {:name "Spheres-audio"
   :spheres (into {} (for [[name sphere] spheres-map]
                       {name (assoc sphere :name name)}
                       ))
   :systems ["sun" "earth" "mars" "jupiter" "saturn" "uranus" "neptune" "pluto"]
   :selected-system "Sun"
   :selected-attr :apoapsis
   :envelope [0 1 0 1 0]
   :freq-range {:min 30 :max 12000}
   :global false
   :freq-rate  (let [apo (map :apoapsis (vals spheres-map))]
                 (/ (- (apply max apo) (apply min apo)) 11970))
   })



;(:envelope default-db)
;;(:spheres default-db)

(def spheres "~/clojure-projects/spheres/spheres-sounds/resources/public/spheres.edn")
;(edn/read spheres)
;; (cljs.tools.reader.edn/read spheres)
;; (cljs.reader/read spheres)

;(slurp spheres)

;; (def r (reader :json))
;; (def w (writer :json))
;; (println (read r "{\"foo\":\"bar\"}"))

;(t/read r "resources/public/spheres.edn")

