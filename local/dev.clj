(ns dev
  (:require [com.stuartsierra.component.repl
             :refer [reset set-init start stop system]]
            [pedestal-microservice.app :as app]))

;; NOTE: for local development, run a postgres container
;;       $ podman run --rm --name local-pg -e POSTGRES_PASSWORD=test -p 5432:5432 -d postgres
(set-init (fn [_] (app/new-system-map :local)))
