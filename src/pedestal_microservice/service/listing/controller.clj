(ns pedestal-microservice.service.listing.controller
  (:require [com.stuartsierra.component :as component]
            [next.jdbc :refer [with-transaction]]
            [pedestal-microservice.service.listing.data.repository :as ListingRepository]))

(defprotocol ListingController
  (create-listing! [this input])
  (get-by-id [this id])
  (get-all [this]))

(defrecord ListingControllerImpl [database datasource]
  component/Lifecycle
  (start [this]
    (assoc this :datasource (database)))

  (stop [this]
    (assoc this :datasource nil))

  ListingController
  (create-listing! [_ input]
    (with-transaction [tx datasource]
      (ListingRepository/save! tx input)))

  (get-by-id [_ id]
    (with-transaction [tx datasource {:read-only true}]
      (ListingRepository/find-by-id tx id)))

  (get-all [_]
    (with-transaction [tx datasource {:read-only true}]
      (ListingRepository/find-all tx))))

(defn new-listing-controller []
  (component/using
   (map->ListingControllerImpl {})
   [:database]))
