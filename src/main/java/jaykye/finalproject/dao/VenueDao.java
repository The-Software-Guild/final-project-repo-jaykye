package jaykye.finalproject.dao;

import jaykye.finalproject.model.Venue;

import java.util.List;

public interface VenueDao {
    Venue getVenueById(String id);
    List<Venue> getAllVenues();
    List<Venue> getSavedVenues();
    Venue addVenue(Venue venue);
    void updateVenue(Venue venue);
    void deleteVenueById(String id);

}
