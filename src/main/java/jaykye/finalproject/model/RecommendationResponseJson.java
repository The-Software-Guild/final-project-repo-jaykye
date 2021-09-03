package jaykye.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationResponseJson {
    public static class RecommendationResponseJsonRoot {
        private Response response;

        public RecommendationResponseJsonRoot() {
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        @Override
        public String toString() {
            String out = "RecommendationResponseJson{" +
                    "response='" + response + '\'' +
                    '}';
            return out;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private List<Group> groups;

        public Response() {
        }

        public List<Group> getGroups() {
            return groups;
        }

        public void setGroups(List<Group> groups) {
            this.groups = groups;
        }

        @Override
        public String toString() {
            String out = "Response{" +
                    "groups='" + groups + '\'' +
                    '}';
            return out;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Group {
        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            String out = "Group{" +
                    "items='" + items + '\'' +
                    '}';
            return out;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private VenueJson venue;
        private Reason reasons;
        private List<Tip> tips;

        public Item() {
        }

        public VenueJson getVenue() {
            return venue;
        }

        public void setVenue(VenueJson venue) {
            this.venue = venue;
        }

        public Reason getReasons() {
            return reasons;
        }

        public void setReasons(Reason reasons) {
            this.reasons = reasons;
        }

        public List<Tip> getTips() {
            return tips;
        }

        public void setTips(List<Tip> tips) {
            this.tips = tips;
        }

        @Override
        public String toString() {
            String out = "Item{" +
                    "venue='" + venue + '\'' +
                    "reasons='" + reasons + '\'' +
                    "tips='" + tips + '\'' +
                    '}';
            return out;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tip {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            String out = "Item{" +
                    "text='" + text + '\'' +
                    '}';
            return out;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Reason {
        private List<ReasonItem> items;

        public Reason() {
        }

        public List<ReasonItem> getItems() {
            return items;
        }

        public void setItems(List<ReasonItem> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            String out = "Reason{" +
                    "items='" + items + '\'' +
                    '}';
            return out;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReasonItem {
        private String summary;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        @Override
        public String toString() {
            String out = "ReasonItem{" +
                    "summary='" + summary + '\'' +
                    '}';
            return out;
        }
    }







}
//{
//        "meta": {
//        "code": 200,
//        "requestId": "5ac51ef86a607143de8eg5cb"
//        },
//        "response": {
//            "warning": {
//            "text": "There aren't a lot of results near you. Try something more general, reset your filters, or expand the search area."
//            },
//            "suggestedRadius": 600,
//            "headerLocation": "Lower East Side",
//            "headerFullLocation": "Lower East Side, New York",
//            "headerLocationGranularity": "neighborhood",
//            "totalResults": 230,
//            "suggestedBounds": {
//                "ne": {
//                    "lat": 40.724216906965616,
//                    "lng": -73.9896507407283
//                },
//                "sw": {
//                    "lat": 40.72151724718017,
//                    "lng": -73.98693222860872
//                }
//            },
//            "groups": [
//                {
//                "type": "Recommended Places",
//                "name": "recommended",
//                "items": [
//                    {
//                        "reasons": {
//                            "count": 0,
//                            "items": [
//                                {
//                                "summary": "This spot is popular",
//                                "type": "general",
//                                "reasonName": "globalInteractionReason"
//                                }
//                            ]
//                        },
//                        "venue": {
//                            "id": "49b6e8d2f964a52016531fe3",
//                            "name": "Russ & Daughters",
//                            "location": {
//                                "address": "179 E Houston St",
//                                "crossStreet": "btwn Allen & Orchard St",
//                                "lat": 40.72286707707289,
//                                "lng": -73.98829148466851,
//                                "labeledLatLngs": [
//                                {
//                                "label": "display",
//                                "lat": 40.72286707707289,
//                                "lng": -73.98829148466851
//                                }
//                                ],
//                                "distance": 130,
//                                "postalCode": "10002",
//                                "cc": "US",
//                                "city": "New York",
//                                "state": "NY",
//                                "country": "United States",
//                                "formattedAddress": [
//                                "179 E Houston St (btwn Allen & Orchard St)",
//                                "New York, NY 10002",
//                                "United States"
//                                ]
//                            },
//                            "categories": [
//                            {
//                            "id": "4bf58dd8d48988d1f5941735",
//                            "name": "Gourmet Shop",
//                            "pluralName": "Gourmet Shops",
//                            "shortName": "Gourmet",
//                            "icon": {
//                            "prefix": "https://ss3.4sqi.net/img/categories_v2/shops/food_gourmet_",
//                            "suffix": ".png"
//                            },
//                            "primary": true
//                            }
//                            ],
//                            "popularityByGeo": 0.9999983845502491,
//                            "venuePage": {
//                            "id": "77298563"
//                            }
//                        }
//                    }
//                ]
//                }
//            ]
//        }
//        }