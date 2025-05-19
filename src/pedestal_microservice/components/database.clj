(ns pedestal-microservice.components.database
  (:require [next.jdbc.connection :as connection])
  (:import org.flywaydb.core.Flyway
           com.zaxxer.hikari.HikariDataSource))

(defn new-component [db-spec]
  (connection/component
   HikariDataSource
   (assoc db-spec
          :init-fn (fn [datasource]
                     (let [fw (.. (Flyway/configure)
                                  (dataSource datasource)
                                  (locations (into-array String ["classpath:database/migration"]))
                                  (table "schema_version")
                                  (load))]
                       (.migrate fw))))))
