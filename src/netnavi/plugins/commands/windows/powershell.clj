(ns netnavi.plugins.commands.windows.powershell
  (:require [clojure.java.io :as io]))

(def shell "powershell.exe")

(defn get-operating-system []
  (let [os-name (System/getProperty "os.name")]
    (cond
      (re-find #"(?i)windows" os-name) "Windows"
      (re-find #"(?i)linux|unix|mac" os-name) "Linux/Unix/Mac"
      :else "Unkown")))

(println (get-operating-system))
