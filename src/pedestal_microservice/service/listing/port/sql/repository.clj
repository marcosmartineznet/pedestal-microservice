(ns pedestal-microservice.service.listing.port.sql.repository
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def ^:private opts
  {:builder-fn rs/as-unqualified-kebab-maps})

(defn find-all [conn]
  (sql/query conn ["select * from listing"] opts))

(defn find-by-id [id conn]
  (sql/get-by-id conn :listing id opts))

(defn save! [listing conn]
  (sql/insert! conn :listing listing opts))
