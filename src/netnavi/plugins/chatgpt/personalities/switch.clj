(ns netnavi.plugins.chatgpt.personalities.switch
  (:require
   [netnavi.assist :as assistant]
   [netnavi.plugins.chatgpt.gpt :as gpt] 
   [netnavi.plugins.chatgpt.gpt :refer [assistant]]))

(import '[netnavi.assist Assistant])

(defn set-assistant []
  (swap! (:running-log gpt/assistant) (constantly (gpt/new-chat "netnav-dev"))))

(defn switch-personality
  ; this may require reworking core & assistant record. probably inits transcript
  "This function takes the current assistant and changes its conversational context"
  []
  (print "Which personality would you like: ")
  (flush)
  (let [input (read-line)]
    (cond
      (= input "netnavi-dev") (set-assistant)
      (= input "guru") (println "guru")
      :else (println "Sticking w/ the generic"))))

(switch-personality)
;(println (:running-log gpt/assistant))

;(defn switch-to-default [])

;(defn switch-to-enlisted [])