(ns pedestal-microservice.core
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.server.components.server :as server]
            [pedestal-microservice.service.components.service :as service]
            [pedestal-microservice.server.routes :as routes]))

(defn -main []
  (println "Starting application...")
  (-> (component/system-map
       :service-map {::http/routes routes/routes
                     ::http/type :jetty
                     ::http/port 8080
                     ::http/join? false}
       :service (service/new)
       :server (server/new))
      (component/start-system)))
