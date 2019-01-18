(ns spheres-sounds.db
  (:require [spheres-sounds.records :refer [spheres-map]]
            [cognitect.transit :as t]
            ;; [cljs.reader :as edn]
            )
  )

(def default-db
  {:name "Spheres-audio"
   :spheres (into {} (for [[name sphere] spheres-map]
                       {name (assoc sphere :name name)}
                       ))
   :systems ["sun" "earth" "mars" "jupiter" "saturn" "uranus" "neptune" "pluto"]
   :selected-system "Sun"})


;;(:spheres default-db)


  (def spheres "~/clojure-projects/spheres/spheres-sounds/resources/public/spheres.edn")
;(edn/read spheres)
;; (cljs.tools.reader.edn/read spheres)
;; (cljs.reader/read spheres)

;(slurp spheres)

(def r (t/reader :json))
(def w (t/writer :json))
(println (t/read r "{\"foo\":\"bar\"}"))

;(t/read r "resources/public/spheres.edn")
