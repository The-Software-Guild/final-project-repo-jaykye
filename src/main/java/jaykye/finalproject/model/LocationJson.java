package jaykye.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationJson {
    private String address;
    private String city;
    private String state;
    private String country;
    private double lat;
    private double lng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", city=" + city +
                ", state=" + state +
                ", country=" + country +
                ", latitude=" + lat +
                ", longitude=" + lng +
                '}';
    }
}

