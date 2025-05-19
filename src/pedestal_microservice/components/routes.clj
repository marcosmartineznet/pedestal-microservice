(ns pedestal-microservice.components.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :refer [body-params]]
            [pedestal-microservice.service.listing.port.http.handler :as listing.handler]))

(def specs
  #{["/listing" :post [(body-params) `listing.handler/create-listing]]
    ["/listing" :get `listing.handler/get-all-listings]
    ["/listing/:id" :get [(body-params) `listing.handler/get-listing-by-id]]})

(defrecord Routes [routes]
  component/Lifecycle
  (start [this]
    this)

  (stop [this]
    this))

(defn new-routes [& additional-specs]
  (map->Routes {:routes (into #{} cat (conj additional-specs specs))}))
