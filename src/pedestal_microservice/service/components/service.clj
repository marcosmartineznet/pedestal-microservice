(ns pedestal-microservice.service.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defn runnable-service [routes]
  {::http/routes (:expanded-routes routes)
   ::http/type :jetty
   ::http/port 8080})

(defrecord MicroserviceService [routes]
  component/Lifecycle
  (start [this]
    (assoc this :runnable-service (runnable-service routes)))

  (stop [this]
    (assoc this :runnable-service nil)))

(defn new []
  (component/using
   (map->MicroserviceService {})
   [:routes]))
