(ns core
  (:use etaoin.api)
  (:require [clojure.edn :as edn]))


;;=========== Configurations =============

(def secrets
  (edn/read-string
    (slurp "./resources/secrets.edn")))

(def draftPost
  (edn/read-string
    (slurp "./resources/draftPost.edn")))


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



(defn create-draft-post
  "
  Navigate the browser to the < Create message > page and uses the text provided in the
  `./resources/draftPost.edn` to create a draft. Saves the screenshot of the page within the
  `./resources` directory.

  This function takes no argument.
  "
  []

  (wait 3)

  (click-el driver
            (query driver {:css "#HeaderUserActions--NewPost"}))
  (wait 3)

  (click-el driver
            (query driver {:css ".notranslate"}))
  (wait 2)

  (fill-active driver (:text draftPost))
  (wait 2)

  (click-el driver
            (query driver {:fn/has-text "Save draft"}))
  (wait 2)

  (screenshot driver "./resources/draftPost.png")
  (wait 2))

;;=========== Main code  =============
(defn main
  "This function is the main entry point for the code.

  Just fire up the repl, move to this namespace and execute `(main)`."
  []
  (do
    (login)
    (create-draft-post)))



