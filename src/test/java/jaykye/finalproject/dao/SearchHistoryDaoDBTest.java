package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;
import jaykye.finalproject.model.SearchHistory;
import jaykye.finalproject.model.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class SearchHistoryDaoDBTest {
    @Autowired
    VenueDao venueDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @BeforeEach
    public void setUp() {
        List<Venue> venues = venueDao.getAllVenues();
        for(Venue venue : venues) {
            venueDao.deleteVenueById(venue.getId());
        }

        List<Category> categories = categoryDao.getAllCategory();
        for(Category category : categories) {
            categoryDao.deleteCategoryById(category.getId());
        }

        List<SearchHistory> searchHistories = searchHistoryDao.getAllSearchHistory();
        for(SearchHistory searchHistory : searchHistories) {
            searchHistoryDao.deleteSearchHistoryById(searchHistory.getId());
        }
    }

    @Test
    public void testAddAndGetVenueById(){
        Category category = new Category();
        category.setId("aaaaaaaaaaaaaaaaaaaaaaaaa");
        category.setName("Coffee Shop");
        categoryDao.addCategory(category);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Venue venue = new Venue();
        venue.setId("bbbbbbbbbbbbbbbbbbbbbbbbb");
        venue.setName("Starbucks");
        venue.setCategories(categories);
        venue.setSavedFavorite(true);
        venueDao.addVenue(venue);

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setVenue(venue);
        searchHistory.setSearchDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        searchHistory = searchHistoryDao.addSearchHistory(searchHistory);

        SearchHistory fromDao = searchHistoryDao.getSearchHistoryById(searchHistory.getId());

        assertEquals(searchHistory, fromDao);
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setId("aaaaaaaaaaaaaaaaaaaaaaaaa");
        category.setName("Coffee Shop");
        categoryDao.addCategory(category);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Venue venue = new Venue();
        venue.setId("bbbbbbbbbbbbbbbbbbbbbbbbb");
        venue.setName("Starbucks");
        venue.setCategories(categories);
        venue.setSavedFavorite(true);
        venueDao.addVenue(venue);

        Venue venue2 = new Venue();
        venue2.setId("ccccccccccccccccccccccccc");
        venue2.setName("Van Houtte");
        venue2.setCategories(categories);
        venue2.setSavedFavorite(true);
        venueDao.addVenue(venue2);


        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setVenue(venue);
        searchHistory.setSearchDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        searchHistory = searchHistoryDao.addSearchHistory(searchHistory);


        SearchHistory searchHistory2 = new SearchHistory();
        searchHistory2.setVenue(venue2);
        searchHistory2.setSearchDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        searchHistory2 = searchHistoryDao.addSearchHistory(searchHistory2);


        List<SearchHistory> searchHistories = searchHistoryDao.getAllSearchHistory();
        assertEquals(2, searchHistories.size());

        assertTrue(searchHistories.contains(searchHistory));
        assertTrue(searchHistories.contains(searchHistory2));
    }
}