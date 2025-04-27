(ns components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))


(defrecord Server [instance service]
  component/Lifecycle
  (start [this]
    (println "Starting the development Pedestal server...")
    (if instance
      this
      (assoc this :instance (-> service
                                (:runnable-service)
                                (assoc ::http/join? false)
                                (http/create-server)
                                (http/start)))))

  (stop [this]
    (println "Stopping the development Pedestal server...")
    (when instance
      (http/stop instance))
    (assoc this :instance nil)))

(defn new []
  (component/using
   (map->Server {})
   [:service]))
