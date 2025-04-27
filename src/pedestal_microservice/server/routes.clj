(ns pedestal-microservice.server.routes)

;; this will be its own component

(defn hello-handler [req]
  {:status 200 :body "Hello, world"})

(def routes
  #{["/health" :get hello-handler :route-name :health]})
