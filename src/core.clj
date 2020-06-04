(ns core
  (:use etaoin.api)
  (:require [clojure.edn :as edn]
            [clojure.string :as clj-str]
            [clojure.java.io :as io]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]))


;;=========== Configurations =============


(def secrets
  (edn/read-string
    (slurp "./resources/secrets.edn")))


(timbre/merge-config!
  {:appenders {:spit (appenders/spit-appender {:fname (:logfile-name secrets})}})


(def driver (firefox {:path-driver  (:path-driver secrets)
                      :path-browser (:path-browser secrets)}))


;;=========== Base functions =============


(defn login
  "
  Visits the reddit login page and uses the credentials read from `./resources/secrets.edn`
  into the input fields.

  This function takes no argument.
  "
  []

  (go driver "https://www.reddit.com/login/")
  (wait 2)

  (click-el driver (query driver {:tag :input :id "loginUsername"}))
  (fill-active driver (:username secrets))
  (wait 2)


  (click-el driver (query driver {:tag :input :id "loginPassword"}))
  (fill-active driver (:password secrets))
  (wait 2)

  (click-el driver
            (query driver {:fn/has-class "AnimatedForm__submitButton"}))
  (wait 2))


;;=========== Main code  =============
(defn main
  "This function is the main entry point for the code.

  Just fire up the repl, move to this namespace and execute `(main)`."
  []
  (do
    (login)))



