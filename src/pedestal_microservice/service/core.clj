(ns pedestal-microservice.service.core)

(defn health-get-handler [request]
  {:status 200 :body :ok})

(def routes
  #{["/health" :get health-get-handler :route-name :health]})
