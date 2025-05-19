(ns pedestal-microservice.service.listing.port.http.handler
  (:require [io.pedestal.http.body-params :refer [body-params]]
   [pedestal-microservice.service.listing.controller :as listing.ctr]
   [pedestal-microservice.service.listing.adapter.http :as listing.adapter.http]))

(defn create-listing [{:keys [dependencies] :as request}]
  (let [{database :database} dependencies
        input (:json-params request)
        listing (-> input
                    (listing.ctr/create-listing! (database))
                    listing.adapter.http/service->http)]
    {:status 200 :body listing}))

(defn get-listing-by-id [{:keys [dependencies] :as request}]
  (let [{database :database} dependencies
        id (-> request :path-params :id Long/parseLong)
        listing (-> id
                    (listing.ctr/get-by-id (database))
                    listing.adapter.http/service->http)]
    {:status 200 :body listing}))

(defn get-all-listings [{:keys [dependencies] :as request}]
  (let [{database :database} dependencies
        result (->> (listing.ctr/get-all (database))
                    (map listing.adapter.http/service->http))]
    {:status 200 :body {:data result}}))
