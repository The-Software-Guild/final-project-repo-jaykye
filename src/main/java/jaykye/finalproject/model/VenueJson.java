package jaykye.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VenueJson {

    private String id;
    private String name;
    private LocationJson location;

    private List<CategoryJson> categories;

    public VenueJson() {
    }

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

    public LocationJson getLocation() {
        return location;
    }

    public void setLocation(LocationJson location) {
        this.location = location;
    }

    public List<CategoryJson> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryJson> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", location=" + location +
                '}';
    }
}
