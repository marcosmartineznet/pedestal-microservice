(ns pedestal-microservice.service.listing.core
  (:require [clojure.spec.alpha :as s])
  (:import java.lang.Long
           java.time.OffsetDateTime))

;; Core business logic will go here

(s/def ::id
  (partial instance? Long))

(s/def ::title
  (s/and
   string?
   #(<= (count %) 255)))

(s/def ::body
  string?)

(s/def ::created-at
  (partial instance? OffsetDateTime))

(s/def ::updated-at
  (partial instance? OffsetDateTime))

(s/def ::listing
  (s/keys
   :req-un [::id
            ::title
            ::body
            ::created-at
            ::updated-at]))

(s/def ::create-listing-input
  (s/keys
   :req-un [::title
            ::body]))
