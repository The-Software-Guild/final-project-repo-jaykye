package jaykye.finalproject.controller;

import jaykye.finalproject.dao.CategoryDao;
import jaykye.finalproject.dao.SearchHistoryDao;
import jaykye.finalproject.dao.VenueDao;
import jaykye.finalproject.model.Category;
import jaykye.finalproject.model.SearchHistory;
import jaykye.finalproject.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SavedPlacesController {
    @Autowired
    VenueDao venueDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("savedPlaces")
    public String displaySaved(Model model) {
        // get selection for method of search
        List<String> searchMethods = new ArrayList<>();
        searchMethods.add("By City");
        searchMethods.add("Near me");
        List<Category> categoriesFromDB = categoryDao.getAllCategory();
        List<Venue> savedVenues = venueDao.getSavedVenues();

        model.addAttribute("searchMethods", searchMethods);
        model.addAttribute("categoriesDB", categoriesFromDB);
        model.addAttribute("savedVenues", savedVenues);
        return "savedPlaces";
    }

    @GetMapping("saveVenueInSavedPlaces")
    public String saveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(true);
        venueDao.updateVenue(venue);
        return "redirect:/savedPlaces";
    }

    @GetMapping("unSaveVenueInSavedPlaces")
    public String unSaveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(false);
        venueDao.updateVenue(venue);
        return "redirect:/savedPlaces";
    }

}
