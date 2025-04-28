(ns pedestal-microservice.app
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.echo.core :as echo]
            [pedestal-microservice.service.core :as service]
            [pedestal-microservice.server.components.server :as server-component]
            [pedestal-microservice.service.components.service :as service-component]
            [pedestal-microservice.service.components.routes :as routes-component]))

(defn build-routes []
  (routes-component/new-routes
   echo/routes
   service/routes))

(defn -main []
  (println "Starting application...")
  (-> (component/system-map
       :routes (build-routes)
       :service (service-component/new)
       :server (server-component/new))
      (component/start)))
