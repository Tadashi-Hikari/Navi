(ns navi.base.chatbot.core 
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]))

(defn extract-response [resp]
  (let [body (get resp :body)
        json-object (json/read-json body)
        content (get-in json-object [:choices 0 :message :content])]
    content))

(defn chat [input]
  (client/post "http://localhost:1337/v1/chat/completions" {:content-type :json
                                                                             :form-params {:messages [{:content "You are a helpful assistant."
                                                                                                       :role "system"} {:content input
                                                                                                                        :role "user"}]
                                                                                           :model "mistral-ins-7b-q4"
                                                                                           :stream false
                                                                                           :max_tokens 4096
                                                                                           :frequency_penalty 0
                                                                                           :presence_penalty 0
                                                                                           :temperature 0.7
                                                                                           :top_p 0.95}}))
