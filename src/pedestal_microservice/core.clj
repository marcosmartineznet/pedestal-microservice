(ns pedestal-microservice.core
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.service.core :as service]
            [pedestal-microservice.server.components.server :as server-component]
            [pedestal-microservice.service.components.service :as service-component]
            [pedestal-microservice.service.components.routes :as routes-component]
            [pedestal-microservice.echo.core :as echo]))

(def RoutesComponent
  (routes-component/new-routes
   echo/routes
   service/routes))

(defn -main []
  (println "Starting application...")
  (-> (component/system-map
       :routes RoutesComponent
       :service (service-component/new)
       :server (server-component/new))
      (component/start-system)))
