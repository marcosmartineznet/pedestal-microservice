(ns pedestal-microservice.app
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.config :as config]
            [pedestal-microservice.echo.core :as echo]
            [pedestal-microservice.components.database :as database]
            [pedestal-microservice.components.routes :as routes]
            [pedestal-microservice.components.server :as server]
            [pedestal-microservice.service.core :as service]
            [pedestal-microservice.service.components.service :as service-component]))

(defn new-system-map [profile]
  (let [config (config/get-config profile)
        db-spec (config/db-spec config)]
    (component/system-map
     :config config
     :database (database/new-component db-spec)
     :routes (routes/new-routes
              echo/routes
              service/routes)
     :service (service-component/new)
     :server (server/new))))

(defn -main []
  (println "Starting application...")
  (-> (new-system-map :prod)
      (component/start)))
