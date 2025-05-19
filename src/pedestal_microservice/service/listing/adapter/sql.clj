(ns pedestal-microservice.service.listing.adapter.sql
  (:import java.time.ZoneOffset))

(defn sql->service [entity]
  (let [{id :id
         title :title
         body :body
         created-at :created-at
         updated-at :updated-at} entity]
    {:id id
     :title title
     :body body
     :created-at (.. created-at
                     toLocalDateTime
                     (atOffset ZoneOffset/UTC))
     :updated-at (.. updated-at
                    toLocalDateTime
                    (atOffset ZoneOffset/UTC))}))
