(ns pedestal-microservice.service.listing.routes.core
  (:require [io.pedestal.http.body-params :refer [body-params]]
   [pedestal-microservice.service.listing.controller :as controller]))

(defn create-listing-handler [{:keys [dependencies] :as request}]
  (let [input (:json-params request)
        ListingController (:ListingController dependencies)
        listing (controller/create-listing! ListingController input)]
    {:status 200 :body listing}))

(defn get-all-listings-handler [request]
  (let [{:keys [dependencies]} request
        ListingController (:ListingController dependencies)
        result (controller/get-all ListingController)]
    {:status 200 :body {:data result}}))


(def specs
  #{["/listing" :get `get-all-listings-handler]
    ["/listing" :post [(body-params) `create-listing-handler]]})
