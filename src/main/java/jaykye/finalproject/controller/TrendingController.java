package jaykye.finalproject.controller;

import jaykye.finalproject.dao.CategoryDao;
import jaykye.finalproject.dao.SearchHistoryDao;
import jaykye.finalproject.dao.VenueDao;
import jaykye.finalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static jaykye.finalproject.controller.myValidation.cityNameHasError;

@Controller
public class TrendingController {
    @Autowired
    VenueDao venueDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${client_id}")
    private String client_id;

    @Value("${client_secret}")
    private String client_secret;

    int resultNum = 0;

    @PostMapping("searchTrendingVenues")
    public String searchTrendingVenues(HttpServletRequest request, Model model) {
        // get selection for method of search
        List<String> searchMethods = new ArrayList<>();
        searchMethods.add("By City");
        searchMethods.add("Near me");
        List<Category> categoriesFromDB = categoryDao.getAllCategory();

        model.addAttribute("searchMethods", searchMethods);
        model.addAttribute("categoriesDB", categoriesFromDB);


        String searchMethod = request.getParameter("searchMethod");
        String locationQuery = "";
        String cityName;

        if (searchMethod.equals("By City")) {
            cityName = request.getParameter("cityName");
            locationQuery += "near=" + cityName;

            // check if city is entered in the input.
            if (cityNameHasError(cityName)) {
                model.addAttribute("cityNameHasError", cityNameHasError(cityName));
                return "nowTrending";
            }
        }
        else if (searchMethod.equals("Near me")){
            // Near me
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            locationQuery += "ll=" + latitude + ","+ longitude;
        }

        String url = "https://api.foursquare.com/v2/venues/trending?" +
                locationQuery +  "&limit=5" +
                "&client_id="+ client_id +
                "&client_secret=" + client_secret +
                "&v=20180928";

        ResponseJson responseJson = restTemplate.getForObject(url,
                ResponseJson.class);
        List<Venue> venues = new ArrayList<>();
        List<VenueJson> venueJsons = responseJson.getResponse().getVenues();
        Venue venue;
        for (VenueJson venueJson : venueJsons) {
            // If venue already in DB, use that. Else, Create a new obj and save in DB.
            if (venueDao.getVenueById(venueJson.getId()) == null){
                venue = parseVenueJson(venueJson);

                List<CategoryJson> categoryJsons = venueJson.getCategories();
                List<Category> categories = new ArrayList<>();

                for (CategoryJson categoryJson: categoryJsons){
                    Category category = parseCategoryJson(categoryJson);

                    categories.add(category);
                    categoryDao.addCategory(category);
                }
                venue.setCategories(categories);

                // Save to database.
                venueDao.addVenue(venue);
            }
            else {
                venue = venueDao.getVenueById(venueJson.getId());
            }
            venues.add(venue);

            // Add to search history
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setVenue(venue);
            searchHistory.setSearchDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            searchHistoryDao.addSearchHistory(searchHistory);
        }

        model.addAttribute("venues", venues);
        resultNum = venues.size();

        List<SearchHistory> recentHistories = searchHistoryDao.getRecentSearchHistory(resultNum);
        model.addAttribute("searchHistories", recentHistories);

        return "nowTrending";
    }

    private Venue parseVenueJson(VenueJson venueJson){
        Venue venue = new Venue();
        venue.setId(venueJson.getId());
        venue.setName(venueJson.getName());
        venue.setAddress(venueJson.getLocation().getAddress());
        venue.setCity(venueJson.getLocation().getCity());
        venue.setState(venueJson.getLocation().getState());
        venue.setCountry(venueJson.getLocation().getCountry());
        venue.setLatitude(venueJson.getLocation().getLat());
        venue.setLongitude(venueJson.getLocation().getLng());
        return venue;
    }

    private Category parseCategoryJson(CategoryJson categoryJson){
        Category category = new Category();
        category.setId(categoryJson.getId());
        category.setName(categoryJson.getName());
        return category;
    }

    @GetMapping("nowTrending")
    public String displaySearch(Model model) {
        // get selection for method of search
        List<String> searchMethods = new ArrayList<>();
        searchMethods.add("By City");
        searchMethods.add("Near me");
        List<Category> categoriesFromDB = categoryDao.getAllCategory();
        List<SearchHistory> recentHistories = searchHistoryDao.getRecentSearchHistory(resultNum);

        model.addAttribute("searchMethods", searchMethods);
        model.addAttribute("categoriesDB", categoriesFromDB);
        model.addAttribute("searchHistories", recentHistories);
        return "nowTrending";
    }

    @GetMapping("saveTrendingVenue")
    public String saveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(true);
        venueDao.updateVenue(venue);
        return "redirect:/nowTrending";
    }

    @GetMapping("unSaveTrendingVenue")
    public String unSaveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(false);
        venueDao.updateVenue(venue);
        return "redirect:/nowTrending";
    }


}
