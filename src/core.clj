(ns core
  (:use etaoin.api)
  (:require [etaoin.keys :as keys]
            [clojure.edn :as edn]))


(def driver (chrome {:path-driver  "/usr/local/bin/chromedriver"
                       :path-browser "/Applications/Chromium.app/Contents/MacOS/Chromium"}))

;;;;;;;;;



