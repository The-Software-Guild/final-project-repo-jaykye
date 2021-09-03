package jaykye.finalproject.model;

import java.util.List;
import java.util.Objects;

public class Venue {
    private String id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;
    private boolean isSavedFavorite;
    private List<Category> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isSavedFavorite() {
        return isSavedFavorite;
    }

    public void setSavedFavorite(boolean savedFavorite) {
        isSavedFavorite = savedFavorite;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Double.compare(venue.latitude, latitude) == 0 && Double.compare(venue.longitude, longitude) == 0 && isSavedFavorite == venue.isSavedFavorite && Objects.equals(id, venue.id) && Objects.equals(name, venue.name) && Objects.equals(address, venue.address) && Objects.equals(city, venue.city) && Objects.equals(state, venue.state) && Objects.equals(country, venue.country) && Objects.equals(categories, venue.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, city, state, country, latitude, longitude, isSavedFavorite, categories);
    }
}
