(ns netnavi.core
  (:require [netnavi.util :as util]
            [netnavi.assist :as assistant]
            [clojure.tools.cli :refer [cli]]
            [netnavi.plugins.chatgpt.features :as features]
              ; This should be changed to GPT installer
            [netnavi.plugins.chatgpt.installer :as installer])
  (:gen-class))

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

;(print @(:running-log netnavi.plugins.gpt/assistant)) 

(defn focused-session
"Launch a conversation with your Navi.jar that will take control of this terminal instance"
[]
(doall
 (installer/do-startup-check
  (print util/BLUE (format "\n%s initalized\n%s" assistant-name util/RESET))
  (println util/line)
  (perpetual-loop))))

(def cli-opts
  ; The tricky part here is that :default only works if -q isn't flagged. -q with no value returns nil. set to nil for consistency
  ["-q" "--question" "ask a question" :default nil])

(defn -main
  "Main entry for the program. command line args processed here"
  [& args]
  (let [result (:question (first (cli args cli-opts)))]
    (if-not (nil? result)
      (println (gpt/quick-chat-with-assistant result))
      (focused-session))))
