(ns mallinator.schema
  (:refer-clojure :exclude [abs])
  (:require
   [malli.core :as m]
   [malli.generator :as gm]))

(def Point
   [:tuple double? double?])

(def genno (gm/generate keyword?))

(gm/generate Point)

