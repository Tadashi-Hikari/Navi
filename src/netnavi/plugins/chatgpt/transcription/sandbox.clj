(ns netnavi.plugins.chatgpt.transcription.sandbox
  (:require [clojure.java.io :as io]
            [netnavi.plugins.chatgpt.gpt :as gpt] ))

(def echo-directory "./echo-data")