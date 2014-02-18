(ns kremers.monger-session
  (:require [monger.collection :as mng])
  (:use [ring.middleware.session.store :as ringstore])
  (:import [java.util UUID Date]))

(defn ser [data] (prn-str data))

(deftype MongodbStore [collection-name auto-key-change? date-field-name]
  ringstore/SessionStore
  (read-session [_ key] 
    (if (nil? key) 
      {} 
      (if-let [entity (mng/find-one-as-map collection-name {:_id key})]
        (read-string (:content entity)) {})))
  (write-session [_ key data]
    (do  
      (let  [entity (if (nil? key) nil (mng/find-one-as-map collection-name {:_id key}))
             key-change? (or (= nil entity) auto-key-change?)
             newkey (if key-change? (str (UUID/randomUUID)) key)]
        (if entity
          (do (if key-change?
                (do (mng/remove collection-name {:_id key})
                    (mng/insert collection-name {:_id newkey date-field-name (Date.) :content (ser (assoc data :_id newkey))}))
                (mng/update collection-name {:_id newkey} {:_id newkey date-field-name (Date.) :content (ser (assoc data :_id newkey))}))
              newkey)
          (do (mng/insert collection-name {:_id newkey date-field-name (Date.) :content (ser (assoc data :_id newkey))})
              newkey)))))
  (delete-session [_ key]
    (mng/remove collection-name {:_id key})
    nil))

(defn mongodb-store
  ([] (mongodb-store {}))
  ([opt]
     (let [collection-name (opt :collection-name "ring_sessions")
           auto-key-change? (opt :auto-key-change? false)
           date-field-name (opt :date-field-name :time_created)]
       (MongodbStore. collection-name auto-key-change? date-field-name))))


