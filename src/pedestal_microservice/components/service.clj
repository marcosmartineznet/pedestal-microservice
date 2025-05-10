(ns pedestal-microservice.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :refer [interceptor]]
            [pedestal-microservice.config :as config]))

;; NOTE: the service will need to depend on all dependencies that should be available in other interceptors
(defn service-dependencies [service]
  "Interceptor to inject and make available dependencies to the system"
  (interceptor
   {:name ::service-dependencies-interceptor
    :enter (fn [context]
             (let [exclude (complement #{:config :routes})] ;; do not include these components
               (assoc-in context
                         [:request :dependencies]
                         (select-keys service (filter exclude (keys service))))))}))

(defn service-dependencies-interceptor [service-map service]
  (update service-map ::http/interceptors conj (service-dependencies service)))

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

(defn runnable-service [srvc-map config service]
  (let [env (config/get-env config)]
    (-> (if (= env :prod)
          (prod-service-map-init srvc-map)
          (dev-service-map-init srvc-map))
        (service-dependencies-interceptor service))))

(defrecord MicroserviceService [config routes database ListingController]
  component/Lifecycle
  (start [this]
    (assoc this :runnable-service (-> routes
                                      (new-service-map config)
                                      (runnable-service config this))))

  (stop [this]
    (assoc this :runnable-service nil)))

(defn new []
  (component/using
   (map->MicroserviceService {})
   [:config :routes :database :ListingController]))
