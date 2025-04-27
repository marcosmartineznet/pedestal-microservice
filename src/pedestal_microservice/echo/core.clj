(ns pedestal-microservice.echo.core)

(defn echo [req]
  {:status 200 :body (pr-str req)})

(def routes
  #{["/echo" :get echo :route-name :echo]})
