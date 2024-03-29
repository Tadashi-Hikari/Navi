(defproject netnavi "0.0.1-CLI"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "0.2.6"]
                 [net.clojars.wkok/openai-clojure "0.9.0"]
                 [org.clojure/tools.cli "1.0.219"]]
  :main ^:skip-aot netnavi.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
