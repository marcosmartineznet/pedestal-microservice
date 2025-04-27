(ns pedestal-microservice.server.core
  (:require [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl
             :refer [reset set-init start stop system]]
            [io.pedestal.http :as http]
            [pedestal-microservice.server.components.server :as server]
            [pedestal-microservice.server.routes :as routes]))

(defn new-system [env]
  (component/system-map
   :service-map {:env env
                 ::http/routes routes/routes
                 ::http/type :jetty
                 ::http/port 8088
                 ::http/join? false}
   :server (component/using (server/new) [:service-map])))

(set-init (fn [_] (new-system :prod)))
