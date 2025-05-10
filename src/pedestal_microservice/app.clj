(ns pedestal-microservice.app
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.config :as config]
            [pedestal-microservice.components.database :as database]
            [pedestal-microservice.components.routes :as routes]
            [pedestal-microservice.components.server :as server]
            [pedestal-microservice.components.service :as service]
            [pedestal-microservice.service.listing.routes.core :as listing.routes]
            [pedestal-microservice.service.listing.controller :as listing.controller]))

(defn new-system-map [profile]
  (let [config (config/get-config profile)
        db-spec (config/db-spec config)]
    (component/system-map
     :config config
     :routes (routes/new-routes listing.routes/specs)
     :database (database/new-component db-spec)
     :service (service/new)
     :server (server/new)

     :ListingController (listing.controller/new-listing-controller))))

(defn -main []
  (println "Starting application...")
  (-> (new-system-map :prod)
      (component/start)))
