(ns pedestal-microservice.service.components.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]))

(defrecord Routes [routes]
  component/Lifecycle
  (start [this]
    (assoc this :expanded-routes (route/expand-routes routes)))

  (stop [this]
    (assoc this :expanded-routes nil)))

(defn new-routes [& route-sets]
  (map->Routes {:routes (reduce #(apply conj %1 %2) #{} route-sets)}))
