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

@Controller
public class RecommendationController {
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

    @PostMapping("searchRecommendedVenues")
    public String searchRecommendedVenues(HttpServletRequest request, Model model) {
        // get selection for method of search
        List<String> searchMethods = new ArrayList<>();
        searchMethods.add("By City");
        searchMethods.add("Near me");
        List<Category> categoriesFromDB = categoryDao.getAllCategory();

        model.addAttribute("searchMethods", searchMethods);
        model.addAttribute("categoriesDB", categoriesFromDB);


        String venueName = request.getParameter("venueName");
        String searchMethod = request.getParameter("searchMethod");
        String locationQuery = "";
        if (searchMethod.equals("By City")) {
            locationQuery += "near=" + request.getParameter("cityName");
        }
        else if (searchMethod.equals("Near me")){
            // Near me
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            locationQuery += "ll=" + latitude + ","+ longitude;
        }

        String url = "https://api.foursquare.com/v2/venues/explore?" +
                locationQuery + "&query="+ venueName + "&limit=5" +
                "&client_id="+ client_id +
                "&client_secret=" + client_secret +
                "&v=20210901";

        RecommendationResponseJson.RecommendationResponseJsonRoot recResponseJson = restTemplate.getForObject(url,
                RecommendationResponseJson.RecommendationResponseJsonRoot.class);
        List<Recommendation> recommendations = new ArrayList<>();
        Venue venue;
        for (RecommendationResponseJson.Group group : recResponseJson.getResponse().getGroups()) {
            for (RecommendationResponseJson.Item item : group.getItems()) {
                VenueJson venueJson = item.getVenue();
                if (venueDao.getVenueById(venueJson.getId()) == null) {

                    venue = parseVenueJson(venueJson);
                    List<CategoryJson> categoryJsons = venueJson.getCategories();
                    List<Category> categories = new ArrayList<>();
                    for (CategoryJson categoryJson : categoryJsons) {
                        Category category = parseCategoryJson(categoryJson);

                        categories.add(category);
                        categoryDao.addCategory(category);
                    }
                    venue.setCategories(categories);

                    // Save to database.
                    venueDao.addVenue(venue);
                } else {
                    venue = venueDao.getVenueById(venueJson.getId());
                }
                Recommendation recommendation = new Recommendation();
                recommendation.setVenue(venue);
                // Add to search history
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setVenue(venue);
                searchHistory.setSearchDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                searchHistoryDao.addSearchHistory(searchHistory);

                String recommendedReason = "";
                for (RecommendationResponseJson.ReasonItem reasonItem : item.getReasons().getItems()) {
                    recommendedReason += reasonItem.getSummary() + "\n";
                }
                recommendation.setReason(recommendedReason);

                recommendations.add(recommendation);
            }
        }
        resultNum = recommendations.size();

        model.addAttribute("recommendations", recommendations);
        List<SearchHistory> recentHistories = searchHistoryDao.getRecentSearchHistory(resultNum);
        model.addAttribute("searchHistories", recentHistories);
        return "recommendation";
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

    @GetMapping("recommendation")
    public String displaySearch(Model model) {
        // get selection for method of search
        List<String> searchMethods = new ArrayList<>();
        searchMethods.add("By City");
        searchMethods.add("Near me");
        List<Category> categoriesFromDB = categoryDao.getAllCategory();
        List<SearchHistory> recentHistories = searchHistoryDao.getRecentSearchHistory(resultNum);
        List<Recommendation> recommendations = new ArrayList<>();

        model.addAttribute("searchMethods", searchMethods);
        model.addAttribute("categoriesDB", categoriesFromDB);
        model.addAttribute("searchHistories", recentHistories);
        model.addAttribute("recommendations", recommendations);
        return "recommendation";
    }

    @GetMapping("saveRecommendedVenue")
    public String saveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(true);
        venueDao.updateVenue(venue);
        return "redirect:/recommendation";
    }

    @GetMapping("unSaveRecommendedVenue")
    public String unSaveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(false);
        venueDao.updateVenue(venue);
        return "redirect:/recommendation";
    }
    
}
