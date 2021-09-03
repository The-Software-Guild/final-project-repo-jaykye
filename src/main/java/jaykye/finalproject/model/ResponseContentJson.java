package jaykye.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseContentJson {
    private List<VenueJson> venues;

    public ResponseContentJson(){}

    public List<VenueJson> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueJson> venues) {
        this.venues = venues;
    }

    @Override
    public String toString() {
        String out = "ResponseContent{" +
                "venues='" + venues + '\'' +
                '}';
        return out;
    }
}


//{"meta":{"code":200,"requestId":"612e96b313b9fc2f98fea191"},
//        "notifications":[{"type":"notificationTray","item":{"unreadCount":0}}],
//        "response":{
//    "venues":[{"id":"49e4a677f964a52014631fe3",
//        "name":"Starbucks",
//        "contact":{"phone":"7188550856","formattedPhone":"(718) 855-0856","twitter":"starbucks","instagram":"starbucks","facebook":"22092443056","facebookUsername":"Starbucks","facebookName":"Starbucks"},
//        "location":{"address":"67 Front St",
//        "crossStreet":"at Main St",
//        "lat":40.702694,
//        "lng":-73.990906,
//        "labeledLatLngs":[{"label":"display","lat":40.702694,"lng":-73.990906}],
//        "distance":823,
//        "postalCode":"11201",
//        "cc":"US",
//        "neighborhood":"DUMBO",
//        "city":"Brooklyn",
//        "state":"NY",
//        "country":"United States",
//        "contextLine":"DUMBO",
//        "contextGeoId":77126,
//        "formattedAddress":["<span itemprop=\"streetAddress\">67 Front St<\/span> (at Main St)","<span itemprop=\"addressLocality\">Brooklyn<\/span>, <span itemprop=\"addressRegion\">NY<\/span> <span itemprop=\"postalCode\">11201<\/span>"]},
//        "canonicalUrl":"https:\/\/foursquare.com\/v\/starbucks\/49e4a677f964a52014631fe3",
//        "canonicalPath":"\/v\/starbucks\/49e4a677f964a52014631fe3",
//        "categories":[{"id":"4bf58dd8d48988d1e0931735","name":"Coffee Shop","pluralName":"Coffee Shops","shortName":"Coffee Shop","icon":{"prefix":"https:\/\/ss3.4sqi.net\/img\/categories_v2\/food\/coffeeshop_","mapPrefix":"https:\/\/ss3.4sqi.net\/img\/categories_map\/food\/coffeeshop","suffix":".png"},"primary":true}],
//        "verified":true,"stats":{"tipCount":61,"usersCount":6344,"checkinsCount":20221},"url":"https:\/\/www.starbucks.com\/store\/16940\/","urlSig":"do4n4MfcHFZNSi2WINgS9tvEKzw=","hasMenu":true,"menu":{"type":"Menu","label":"Menu","anchor":"View Menu","url":"https:\/\/www.starbucks.com\/menu","mobileUrl":"https:\/\/www.starbucks.com\/menu","canonicalPath":"\/v\/starbucks\/49e4a677f964a52014631fe3\/menu","externalUrl":"https:\/\/www.starbucks.com\/menu"},"allowMenuUrlEdit":true,"beenHere":{"lastCheckinExpiredAt":0},"specials":{"count":0,"items":[]},"storeId":"9368","hereNow":{"count":0,"summary":"Nobody here","groups":[]},"referralId":"v-1630443187","venueChains":[{"id":"556f676fbd6a75a99038d8ec"}],"hasPerk":false},{"id":"49edd0a5f964a520fa671fe3","name":"Starbucks","contact":{"phone":"7182430455","formattedPhone":"(718) 243-0455","twitter":"starbucks","instagram":"starbucks","facebook":"22092443056","facebookUsername":"Starbucks","facebookName":"Starbucks"},"location":{"address":"134 Montague St","crossStreet":"btwn Henry & Clinton St","lat":40.69455558,"lng":-73.99398646,"labeledLatLngs":[{"label":"display","lat":40.69455558,"lng":-73.99398646}],"distance":790,"postalCode":"11201","cc":"US","neighborhood":"Brooklyn Heights","city":"Brooklyn","state":"NY","country":"United States","contextLine":"Brooklyn Heights","contextGeoId":7579,"formattedAddress":["<span itemprop=\"streetAddress\">134 Montague St<\/span> (btwn Henry &amp; Clinton St)","<span itemprop=\"addressLocality\">Brooklyn<\/span>, <span itemprop=\"addressRegion\">NY<\/span> <span itemprop=\"postalCode\">11201<\/span>"]},"canonicalUrl":"https:\/\/foursquare.com\/v\/starbucks\/49edd0a5f964a520fa671fe3","canonicalPath":"\/v\/starbucks\/49edd0a5f964a520fa671fe3","categories":[{"id":"4bf58dd8d48988d1e0931735","name":"Coffee Shop","pluralName":"Coffee Shops","shortName":"Coffee Shop","icon":{"prefix":"https:\/\/ss3.4sqi.net\/img\/categories_v2\/food\/coffeeshop_","mapPrefix":"https:\/\/ss3.4sqi.net\/img\/categories_map\/food\/coffeeshop","suffix":".png"},"primary":true}],"verified":true,"stats":{"tipCount":49,"usersCount":3397,"checkinsCount":10498},"url":"https:\/\/www.starbucks.com\/store\/88600\/","urlSig":"En4DfnVj6ss7iZ\/FbeqyEMb5lyw=","hasMenu":true,"menu":{"type":"Menu","label":"Menu","anchor":"View Menu","url":"https:\/\/www.starbucks.com\/menu","mobileUrl":"https:\/\/www.starbucks.com\/menu","canonicalPath":"\/v\/starbucks\/49edd0a5f964a520fa671fe3\/menu","externalUrl":"https:\/\/www.starbucks.com\/menu"},"allowMenuUrlEdit":true,"beenHere":{"lastCheckinExpiredAt":0},"specials":{"count":0,"items":[]},"storeId":"844","hereNow":{"count":0,"summary":"Nobody here","groups":[]},"referralId":"v-1630443187","venueChains":[{"id":"556f676fbd6a75a99038d8ec"}],"hasPerk":false}]}}