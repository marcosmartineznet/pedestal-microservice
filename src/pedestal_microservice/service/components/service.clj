(ns pedestal-microservice.service.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [pedestal-microservice.config :as config]))

;; NOTE: the service will need to depend on all dependencies that should be available in other interceptors
;; TODO: need an interceptor to inject dependencies to make them available in other interceptors

(defn new-service-map [routes config]
  (let [port (config/get-application-port config)]
    {::http/routes (:expanded-routes routes)
     ::http/type :jetty
     ::http/port port}))

(defn dev-service-map-init [srvc-map]
  (-> srvc-map
      (merge {::http/join? false})
      http/default-interceptors
      http/dev-interceptors))

(defn prod-service-map-init [srvc-map]
  (http/default-interceptors srvc-map))

(defn runnable-service [srvc-map config]
  (let [env (config/get-env config)]
    (-> (if (= env :prod)
          (prod-service-map-init srvc-map)
          (dev-service-map-init srvc-map)))))

(defrecord MicroserviceService [config routes]
  component/Lifecycle
  (start [this]
    (assoc this :runnable-service (-> routes
                                      (new-service-map config)
                                      (runnable-service config))))

  (stop [this]
    (assoc this :runnable-service nil)))

(defn new []
  (component/using
   (map->MicroserviceService {})
   [:config :routes]))
