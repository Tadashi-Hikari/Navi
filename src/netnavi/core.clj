(ns netnavi.core
  (:require [netnavi.util :as util]
            [netnavi.assist :as assistant]
            [netnavi.plugins.chatgpt.features :as features]
              ; This should be changed to GPT installer
            [netnavi.plugins.chatgpt.installer :as installer])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(require
 '[netnavi.plugins.chatgpt.gpt :as gpt])

(def assistant-name "Sapphire.EXE")

(defn perpetual-loop
  "The main entry point to a NetNavi"
  []
  (loop []
    (print (format "%sWhat would you like to say to %s:%s" util/GREEN assistant-name util/RESET) "")
    ; Needed, otherwise print won't print before (read-line)
    (flush)
    (let [input (read-line)]
      (let [result (features/check-for-command? input)]
        (if result
          nil
          (do (println util/line)
              (println util/RED (gpt/chat-with-assistant input) util/RESET)
              (println util/line)))))
    (recur)))

; ----- START PROGRAM HERE -----
(installer/do-startup-check)
(print util/BLUE (format "\n%s initalized\n%s" assistant-name util/RESET))
(println util/line)
(perpetual-loop)

;(print @(:running-log netnavi.plugins.gpt/assistant)) 