package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;
import jaykye.finalproject.model.SearchHistory;
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
public class SearchHistoryDaoDB implements SearchHistoryDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SearchHistory getSearchHistoryById(int id) {
        try {
            final String GET_SEARCHHISTORY_BY_ID = "SELECT * FROM searchHistory WHERE searchHistoryId = ?";
            SearchHistory searchHistory = jdbc.queryForObject(GET_SEARCHHISTORY_BY_ID, new SearchHistoryDaoDB.SearchHistoryMapper(), id);
            Venue venue = getVenueForSearchHistory(id);
            venue.setCategories(getCategoriesForVenue(venue.getId()));
            searchHistory.setVenue(venue);
            return searchHistory;
        } catch(DataAccessException ex) {
            return null;
        }
    }


    // ############################### Foreign key object 가져오는 함수들 ###############################

    private Venue getVenueForSearchHistory(int searchHistroyId){
        final String SELECT_VENUE_FOR_SEARCHHISTORY = "select v.* from searchHistory sh join venue v on sh.venueId = v.venueId WHERE sh.searchHistoryId = ?";
        return jdbc.queryForObject(SELECT_VENUE_FOR_SEARCHHISTORY, new VenueDaoDB.VenueMapper(), searchHistroyId);
    }

    private List<Category> getCategoriesForVenue(String venueId){
        final String SELECT_CATEGORY_FOR_VENUE = "select c.* from category c join venue_category vc on c.categoryId = vc.categoryId join venue v on v.venueId = vc.venueId WHERE v.venueId = ?";
        return jdbc.query(SELECT_CATEGORY_FOR_VENUE, new CategoryDaoDB.CategoryMapper(), venueId);
    }
    // #################################################################################################



    @Override
    public List<SearchHistory> getAllSearchHistory() {
        final String GET_ALL_SEARCHHISTORYES = "SELECT * FROM searchHistory";
        List<SearchHistory> searchHistories = jdbc.query(GET_ALL_SEARCHHISTORYES, new SearchHistoryMapper());
        for (SearchHistory searchHistory : searchHistories) {
            Venue venue = getVenueForSearchHistory(searchHistory.getId());
            venue.setCategories(getCategoriesForVenue(venue.getId()));
            searchHistory.setVenue(venue);
        }
        return searchHistories;     
    }

    @Override
    public List<SearchHistory> getRecentSearchHistory(int limit) {
        final String GET_RECENT_SEARCHHISTORIES = "SELECT * FROM searchHistory order by searchDatetime desc limit " + limit;
        List<SearchHistory> searchHistories = jdbc.query(GET_RECENT_SEARCHHISTORIES, new SearchHistoryMapper());
        for (SearchHistory searchHistory : searchHistories) {
            Venue venue = getVenueForSearchHistory(searchHistory.getId());
            venue.setCategories(getCategoriesForVenue(venue.getId()));
            searchHistory.setVenue(venue);
        }
        return searchHistories;
    }

    @Override
    public SearchHistory addSearchHistory(SearchHistory searchHistory) {
        final String INSERT_SEARCHHISTORY = "INSERT INTO searchHistory(searchDatetime, venueId) " +
                "VALUES(?, ?)";
        jdbc.update(INSERT_SEARCHHISTORY,
                searchHistory.getSearchDatetime(),
                searchHistory.getVenue().getId()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        searchHistory.setId(newId);
        return searchHistory;
    }

    @Override
    public void updateSearchHistory(SearchHistory searchHistory) {

    }

    @Override
    public void deleteSearchHistoryById(int id) {
        final String DELETE_SEARCHHISTORY = "DELETE FROM searchHistory WHERE searchHistoryId = ?";
        jdbc.update(DELETE_SEARCHHISTORY, id);
    }

    public static final class SearchHistoryMapper implements RowMapper<SearchHistory> {
        /**
         * Do not map foreign keys here.
         * @param rs
         * @param index
         * @return
         * @throws SQLException
         */
        @Override
        public SearchHistory mapRow(ResultSet rs, int index) throws SQLException {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setId(rs.getInt("searchHistoryId"));
            searchHistory.setSearchDatetime(rs.getTimestamp("searchDatetime").toLocalDateTime());
            return searchHistory;
        }
    }
}
