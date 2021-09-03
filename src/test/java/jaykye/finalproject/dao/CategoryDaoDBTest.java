package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;
import jaykye.finalproject.model.SearchHistory;
import jaykye.finalproject.model.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class CategoryDaoDBTest {
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

        Category fromDao = categoryDao.getCategoryById(category.getId());

        assertEquals(category, fromDao);
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setId("aaaaaaaaaaaaaaaaaaaaaaaaa");
        category.setName("Coffee Shop");
        categoryDao.addCategory(category);

        Category category2 = new Category();
        category2.setId("zzzzzzzzzzzzzzzzzzzzzzzzz");
        category2.setName("Coffee Shop");
        categoryDao.addCategory(category2);

        List<Category> categories = categoryDao.getAllCategory();
        assertEquals(2, categories.size());

        assertTrue(categories.contains(category));
        assertTrue(categories.contains(category2));
    }
}