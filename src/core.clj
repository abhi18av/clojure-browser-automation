(ns core
  (:use etaoin.api)
  (:require [clojure.edn :as edn]))

(def secrets
  (edn/read-string
    (slurp "./src/secrets.edn")))

(def driver (firefox {:path-driver  "/usr/local/bin/geckodriver"
                      :path-browser "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox"}))


;;;;;;;;;


(go driver "https://leetcode.com/accounts/login/")


(defn login []
  (click-el driver (first (query-all driver {:tag :input})))
  (fill-active driver (:username secrets))
  (wait 2)


  (click-el driver (second (query-all driver {:tag :input})))
  (fill-active driver (:password secrets))
  (wait 2)

  (click-el driver
            (query driver {:fn/has-class "btn-content-container__214G"})))

(login)

