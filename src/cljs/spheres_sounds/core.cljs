(ns spheres-sounds.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [spheres-sounds.events :as events]
   [spheres-sounds.views :as views]
   [spheres-sounds.config :as config]
   [re-pressed.core :as rp]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
