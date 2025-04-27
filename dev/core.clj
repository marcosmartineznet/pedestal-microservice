(ns core
  (:require [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl
             :refer [reset set-init start stop system]]
            [components.server :as server]
            [pedestal-microservice.service.components.service :as service]))

(defn new-system []
  (component/system-map
   :routes
   :server (server/new)
   :service (service/new)))

(set-init (fn [_] (new-system)))
