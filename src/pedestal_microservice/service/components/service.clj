(ns pedestal-microservice.service.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord MicroserviceService [runnable-service service-map]
  component/Lifecycle
  (start [this]
    (assoc this :runnable-service (-> service-map
                                      (http/default-interceptors))))

  (stop [this]
    (assoc this :runnable-service nil)))

(defn new []
  (component/using
   (map->MicroserviceService {})
   [:service-map]))
