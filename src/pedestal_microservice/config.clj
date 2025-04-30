(ns pedestal-microservice.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]))

(defn get-config
  ([] (get-config :prod))
  ([profile] (-> "config.edn"
                 (io/resource)
                 (aero/read-config {:profile profile}))))

(defn get-env [config]
  (:env config))

(defn get-application-port [config]
  (-> config :application :port))

(defn db-spec [config]
  (:database config))
