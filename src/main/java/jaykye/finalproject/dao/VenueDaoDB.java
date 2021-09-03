package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;
import jaykye.finalproject.model.SearchHistory;
import jaykye.finalproject.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VenueDaoDB implements VenueDao {
    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Venue getVenueById(String id) {
        try {
            final String GET_VENUE_BY_ID = "SELECT * FROM venue WHERE venueId = ?";
            Venue venue = jdbc.queryForObject(GET_VENUE_BY_ID, new VenueMapper(), id);

            venue.setCategories(getCategoriesForVenue(id));
            return venue;
        } catch(DataAccessException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    // ############################### Foreign key object 가져오는 함수들 ###############################

    private List<Category> getCategoriesForVenue(String venueId){
        final String SELECT_CATEGORY_FOR_VENUE = "select c.* from category c join venue_category vc on c.categoryId = vc.categoryId join venue v on v.venueId = vc.venueId WHERE v.venueId = ?";
        return jdbc.query(SELECT_CATEGORY_FOR_VENUE, new CategoryDaoDB.CategoryMapper(), venueId);
    }
    // #################################################################################################


    
    @Override
    public List<Venue> getAllVenues() {
        final String GET_ALL_VENUES = "SELECT * FROM venue";
        List<Venue> venues = jdbc.query(GET_ALL_VENUES, new VenueMapper());
        for (Venue venue : venues) {
            venue.setCategories(getCategoriesForVenue(venue.getId()));
        }
        return venues;    
    }

    @Override
    public List<Venue> getSavedVenues() {
        final String GET_SAVED_VENUES = "SELECT * FROM venue where isSavedFavorite = 1";
        List<Venue> venues = jdbc.query(GET_SAVED_VENUES, new VenueMapper());
        for (Venue venue : venues) {
            venue.setCategories(getCategoriesForVenue(venue.getId()));
        }
        return venues;
    }

    @Override
    public Venue addVenue(Venue venue) {
        final String INSERT_VENUE = "INSERT IGNORE INTO venue(venueId, venueName, address, city, state, country, latitude, longitude, isSavedFavorite) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(INSERT_VENUE,
                venue.getId(),
                venue.getName(),
                venue.getAddress(),
                venue.getCity(),
                venue.getState(),
                venue.getCountry(),
                venue.getLatitude(),
                venue.getLongitude(),
                venue.isSavedFavorite()
        );

        // venue id is retrieved from API.
        insertVenue_Category(venue);
        return venue;
    }

    // Handle Many to many bridge table.
    private void insertVenue_Category(Venue venue){
        final String INSERT_VENUE_CATEGORY = "INSERT IGNORE into venue_category(venueId, categoryId) VALUES(?, ?)";
        for (Category category : venue.getCategories()) {
            jdbc.update(INSERT_VENUE_CATEGORY, venue.getId(), category.getId());
        }
    }

    @Override
    public void updateVenue(Venue venue) {
        final String UPDATE_VENUE = "update venue set venueName = ?, address = ?, " +
                "city = ?, state = ?, country = ?, latitude = ?, longitude = ?, " +
                "isSavedFavorite = ? " +
                "WHERE venueId = ?";
        jdbc.update(UPDATE_VENUE,
                venue.getName(),
                venue.getAddress(),
                venue.getCity(),
                venue.getState(),
                venue.getCountry(),
                venue.getLatitude(),
                venue.getLongitude(),
                venue.isSavedFavorite(),
                venue.getId());

    }

    @Override
    public void deleteVenueById(String id) {
        final String DELETE_VENUE_CATEGORY = "DELETE FROM venue_category WHERE venueId = ?";
        jdbc.update(DELETE_VENUE_CATEGORY, id);

        final String DELETE_SEARCHHISTORY = "DELETE FROM searchHistory WHERE venueId = ?";
        jdbc.update(DELETE_SEARCHHISTORY, id);

        final String DELETE_VENUE = "DELETE FROM venue WHERE venueId = ?";
        jdbc.update(DELETE_VENUE, id);
    }

    public static final class VenueMapper implements RowMapper<Venue> {
        /**
         * Do not map foreign keys here.
         * @param rs
         * @param index
         * @return
         * @throws SQLException
         */
        @Override
        public Venue mapRow(ResultSet rs, int index) throws SQLException {
            Venue venue = new Venue();
            venue.setId(rs.getString("venueId"));
            venue.setName(rs.getString("venueName"));
            venue.setAddress(rs.getString("address"));
            venue.setCity(rs.getString("city"));
            venue.setState(rs.getString("state"));
            venue.setCountry(rs.getString("country"));
            venue.setLatitude(rs.getDouble("latitude"));
            venue.setLongitude(rs.getDouble("longitude"));
            venue.setSavedFavorite(rs.getBoolean("isSavedFavorite"));
            return venue;
        }
    }
}
