(ns pedestal-microservice.service.listing.controller
  (:require [com.stuartsierra.component :as component]
            [next.jdbc :as jdbc]
            [pedestal-microservice.service.listing.port.sql.repository :as listing.repo]
            [pedestal-microservice.service.listing.adapter.sql :as listing.adapter.sql]))

(defn create-listing! [input storage]
  (jdbc/with-transaction [tx storage]
    (-> input
        (listing.repo/save! tx)
        listing.adapter.sql/sql->service)))

(defn get-by-id [id storage]
  (jdbc/with-transaction [tx storage {:read-only true}]
    (-> id
        (listing.repo/find-by-id tx)
        listing.adapter.sql/sql->service)))

(defn get-all [storage]
  (jdbc/with-transaction [tx storage {:read-only true}]
    (->> (listing.repo/find-all tx)
         (map listing.adapter.sql/sql->service))))
