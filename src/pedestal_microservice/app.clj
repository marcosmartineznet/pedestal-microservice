(ns pedestal-microservice.app
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.config :as config]
            [pedestal-microservice.components.database :as database]
            [pedestal-microservice.components.routes :as routes]
            [pedestal-microservice.components.server :as server]
            [pedestal-microservice.components.service :as service]))

(defn new-system-map [profile]
  (let [config (config/get-config profile)
        db-spec (config/db-spec config)]
    (component/system-map
     :config config
     :routes (routes/new-routes)
     :database (database/new-component db-spec)
     :service (service/new)
     :server (server/new))))

(defn -main []
  (println "Starting application...")
  (-> (new-system-map :prod)
      (component/start)))
