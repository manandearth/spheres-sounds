(defproject spheres-sounds "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.516"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [garden "1.3.5"]
                 [ns-tracker "0.3.1"]
                 [cljs-bach "0.3.0"]
                 #_[com.cognitect/transit-cljs "0.8.239"]
                 [re-pressed "0.3.0"]
                 [cider/piggieback "0.4.0"]
]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-garden "0.2.8"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    ;"resources/public/css"
                                    ]

  :figwheel {:css-dirs ["resources/public/css"]}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   spheres-sounds.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [figwheel-sidecar "0.5.18"]
                   [cider/piggieback "0.4.0"]
                   [re-frisk "0.5.3"]]

    :plugins      [[com.bhauman/figwheel-main "0.2.0"]]
    :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
    
  }
   
   :prod { }
   :uberjar {:source-paths ["env/prod/clj"]
             :omit-source  true
             :main         spheres-sounds.server
             :aot          [spheres-sounds.server]
             :uberjar-name "spheres-sounds.jar"
             :prep-tasks   [["cljsbuild" "once" "min"] "compile"]}
   }

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "spheres-sounds.core/mount-root"}
     :compiler     {:main                 spheres-sounds.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload re-frisk.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            spheres-sounds.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}


    ]}
  )
