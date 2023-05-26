(ns mallinator.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as r]
   [mallinator.subs :as subs]
   [malli.core :as m]
   [malli.generator :as mg]))

(def twitchmsg "This calculator aims to provide a rough estimate on the amount one can expect to make off Twitch ads given a relatively common reported rate of $0.0035 per view on the Twitch Platform")

(defn atom-input [value]
  ;;This react component can be given an r/atom in a parent component
  ;;to manage on behalf of the parent, updating the "value" property
  ;;of the HTML
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        avg-viewer-count (r/atom 1000)
        num-ads (r/atom 30)
        rate (r/atom 0.0035)]

    (fn [] [:div {:style {:font-family "Helvetica"}}
            [:div
             [:h1 "Twitch Calculator"]
             [:h2 "Rate Calculator for twitch ad payouts"]
             [:h3 twitchmsg]]
            [:div
             [:h2 "Rate of Viewer Payout: " [:mark "$" @rate]]
             [:h2 "Daily Average Viewers: " [:mark [atom-input avg-viewer-count]]]
             [:h2 "Daily Ads run: " [:mark  [atom-input num-ads] " Ads/ Day"]]
             [:h2 "That is 1 ad every: " [:mark (int (/ 3600 @num-ads)) " minutes"]]
             [:ul "That is: " (* 365 @num-ads) " ads run each year totaling:"
              [:li [:strong [:mark (/ (* 365 @num-ads) 120) " hours"]]
               [:li [:strong [:mark (/ (* 365 @num-ads) 2880) " days"]]]
               [:p "======================"]
               [:ul
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 1)) " per day"]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 7)) " per week"]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 31)) " per month"]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 91)) " per Quarter"]
                [:h1 [:mark "That's: $" (int (* @avg-viewer-count @rate @num-ads 365)) " per year"]]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 365 5)) " every 5 years"]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 365 10)) " every 10 years"]
                [:li "That's: $" (int (* @avg-viewer-count @rate @num-ads 365 25)) " every 25 years"]]]]]])))