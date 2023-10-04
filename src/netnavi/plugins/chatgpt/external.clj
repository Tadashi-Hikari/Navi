(ns netnavi.plugins.chatgpt.external 
  (:import [netnavi.assist Assistant])
  (:require [netnavi.plugins.chatgpt.features :as features]
            [netnavi.plugins.chatgpt.gpt :as gpt]
            [netnavi.util :as util]
            [netnavi.assist :as assistant]
            [netnavi.plugins.chatgpt.personalities.core :as personality]))

(require
 '[wkok.openai-clojure.api :as api]
 '[netnavi.assist :as assistant]) 

; These two may not fit here, but was circular dependant in gpt.clj
(def empty-chat [{:role "system" :content personality/standard}])

; This should be moved to GPT Module
(def assistant (Assistant. (atom empty-chat)))

(def memory "memory/desktop.memory")

(defn load-memory []
  (slurp memory))

; This should be where 
(defn append-to-memory []
  (spit memory (str (last @(:running-log gpt/assistant)) "\n") :append true))

(save-memory)

 (let [input (read-line)]
  (let [result (features/check-for-command? input)]
    (if result
      nil
      (do (println util/line)
          (println util/RED (gpt/chat-with-assistant input) util/RESET)
          (println util/line))
      )))

(load-memory)

(reset! (:running-log assistant) (load-memory))