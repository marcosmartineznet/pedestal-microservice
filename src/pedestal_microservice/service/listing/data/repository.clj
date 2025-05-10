(ns pedestal-microservice.service.listing.data.repository
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def ^:private opts
  {:builder-fn rs/as-unqualified-kebab-maps})

(defn find-all [conn]
  (sql/query conn ["select * from listing"] opts))

(defn find-by-id [conn id]
  (jdbc/execute-one! conn ["select * from listing where id = ?" id] opts))

(defn save! [conn listing]
  (sql/insert! conn :listing listing opts))
