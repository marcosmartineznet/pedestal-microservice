(ns pedestal-microservice.service.listing.adapter.http)

(defn service->http [m]
  (let [{id :id
         title :title
         body :body
         created-at :created-at
         updated-at :updated-at} m]
    {:id id
     :title title
     :body body
     :created-at (.toString created-at)
     :updated-at (.toString updated-at)}))
