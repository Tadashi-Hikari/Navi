(ns netnavi.plugins.chatgpt.features
  (:require [netnavi.util :as util] 
            [netnavi.plugins.chatgpt.gpt :as gpt] 
            [clojure.java.shell :as shell]
            [netnavi.plugins.chatgpt.installer :as installer]
            [netnavi.plugins.system.clipboard :as clipboard]
            [netnavi.plugins.chatgpt.personalities.core :as personality])
  (:import [netnavi.assist Assistant]))

;(let [result (clojure.java.shell/sh "firefox")])

(defn clear
  "A simple expression to clear a bash shell"
  []
  ; Uses ANSI code for 'move input to start' and "clear terminal". Does not work in all terminals
  (print "\u001b[H\u001b[2J"))

(defn clear-emacs []
  (dotimes [i 20]
    (println)))

(defn init!
  "reset the assistant back to default by mutating the record"
  []
  (reset! (:running-log gpt/assistant) gpt/empty-chat)
  (clear)
  (println (format "%sReinitialized%s" util/RED util/RESET)))

(defn init []
  (init!))

(defn strike-last-input!
  "This form removes the last prompt/response pair"
  []
  (if (< (count @(:running-log gpt/assistant)) 2)
    (println "Nothing to do!")
    (swap! (:running-log gpt/assistant) #(subvec % 0 (- (count %) 2)))))

(defn print-running-log []
  (println @(:running-log gpt/assistant)))

(defn print-last-prompt []
  (println (let [result (:content (get @(:running-log gpt/assistant) (- (count @(:running-log gpt/assistant)) 2)))]
             (format "%s%s%s" util/RED result util/BLUE))))

(defn print-last-response []
  (println (let [result (:content (last @(:running-log gpt/assistant)))]
             (format "%s%s%s" util/RED result util/BLUE))))

(defn echo-transcript []
  (print "Enter filename: ")
  (flush) 
  (let [filename (read-line)]
    (doseq [index (range (count @(:running-log gpt/assistant)))]
      (spit filename (nth @(:running-log gpt/assistant) index) :append true)
      (spit filename "\n" :append true))))

(defn help []
  (doseq [item (keys (ns-publics 'netnavi.plugins.chatgpt.features))]
     (println item)))

(defn bash []
  (shell/sh "bash"))

(defn exit []
  (System/exit 0))

(defn echo
  "Used to print the last output to a file"
  []
  (println "Enter filename to spit: ")
  (let [filename (read-line)]
    (spit filename (last @(:running-log gpt/assistant)))
    (spit filename "\n")))

; TODO: Break this out a bit more
(defn c-clip 
  "Append the clipboards text to the next message"
  []
  (print (format "%sWhat would you like to say to %s:%s" util/GREEN personality/navi-name util/RESET) "")
  (flush)
  ; This is almost the (perpetual-loop) expression in netnavi.core
  (let [text (clipboard/get-clipboard-string)
        input (read-line)
        added-data (str (format "The following data has been attached as supplementary to the prior text. Please consider it in your response: %s" text))] 
    (do (println util/line)
        (println util/RED (gpt/chat-with-assistant (format "%s %s" input added-data)) util/RESET)
        (println util/line))))

(defn echo-append []
  ; This could/should be pulled from a let? then I can call it from elsewhere
  (print "Enter filename: ")
  (flush)
  (let [filename (read-line)]
    (spit filename (get (last @(:running-log gpt/assistant)) :content) :append true)
    (spit filename "\n" :append true)))

; I might want this to return 
(defn check-for-command? 
  "Checks if a command exists. If so, it runs the command" 
  [prompt] 
  (let [resolved (resolve (symbol "netnavi.plugins.chatgpt.features" prompt))]
    (if resolved
     (do 
       (println "Command" prompt "executed")
       (println util/line)
       (resolved)
       true)
      nil)))
 
; Find a better way to do this
(defn set-env-var 
  "A simple wrapper for the installer expression, to set the API Key and Org info"
  [] 
  (installer/request-api-info))

;(check-for-command? "init!")

;(print netnavi.plugins.gpt/assistant)
;(count @(:running-log netnavi.plugins.gpt/assistant))
;(strike-last-input!)
;(init!)
