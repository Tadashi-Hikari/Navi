(ns netnavi.plugins.chatgpt.command-line)

                                        ; When I type sapphire, send a one off message to ChatGPT. Eventually, this should maintain its own ring of consiousness
(defn quick-chat-with-assistant [message]
  (get-in (api/create-chat-completion {:model "gpt-3.5-turbo"
                                       :messages (add-new-message message)}) [:choices 0 :message :content]))
