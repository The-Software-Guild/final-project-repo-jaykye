package jaykye.finalproject.controller;

import jaykye.finalproject.dao.CategoryDao;
import jaykye.finalproject.dao.SearchHistoryDao;
import jaykye.finalproject.dao.VenueDao;
import jaykye.finalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
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

    @PostMapping("searchVenue")
    public String searchVenue(HttpServletRequest request, Model model) {
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

        String url = "https://api.foursquare.com/v2/venues/search?" +
                locationQuery + "&query="+ venueName + "&limit=5" +
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

        return "search";
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

    @GetMapping("search")
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
        return "search";
    }

    @GetMapping("saveVenue")
    public String saveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(true);
        venueDao.updateVenue(venue);
        return "redirect:/search";
    }

    @GetMapping("unSaveVenue")
    public String unSaveVenue(String id){
        Venue venue = venueDao.getVenueById(id);
        venue.setSavedFavorite(false);
        venueDao.updateVenue(venue);
        return "redirect:/search";
    }

//
//    @PostMapping("addHero")
//    public String addHero(Hero hero, BindingResult result, HttpServletRequest request) {
//        String superpowerId = request.getParameter("superpowerId");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
//        hero.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
//
//        List<Organization> organizations = new ArrayList<>();
//
//        if(organizationIds != null) {
//            for(String organizationId : organizationIds) {
//                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
//            }
//        } else {
//            FieldError error = new FieldError("hero",
//                    "organizations", "Must include one organization");
//            result.addError(error);
//        }
//        hero.setOrganizations(organizations);
//
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(hero);
//        if(violations.isEmpty()) {
//            heroDao.addHero(hero);
//        } // error 들은 html 페이지에 errors 라는 항목으로 model 에 넣어서 전달한다.
//
//        return "redirect:/heroes";
//    }
//
//    @GetMapping("heroDetail")
//    public String heroDetail(Integer id, Model model) { // HttpServletRequest 안쓰고 바로 id 빼올 수 있네...
//        Hero hero = heroDao.getHeroById(id);
//        model.addAttribute("hero", hero);
//        return "heroDetail";
//    }
//
//    @GetMapping("deleteHero")
//    public String deleteHero(Integer id) {
//        heroDao.deleteHeroById(id);
//        return "redirect:/heroes";
//    }
//
//    @GetMapping("editHero")
//    public String editHero(Integer id, Model model) {
//        Hero hero = heroDao.getHeroById(id);
//        List<Organization> organizations = organizationDao.getAllOrganizations();
//        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
//        model.addAttribute("hero", hero);
//        model.addAttribute("organizations", organizations);
//        model.addAttribute("superpowers", superpowers);
//        return "editHero";
//    }
//
//    // Hero 의 validation 은 이전과는 다르다. 여기서는 데이터를 variable 이나 DTO object 가 아닌 HttpServletRequest 로 보내기 때문.
//    @PostMapping("editHero")
//    public String performEditHero(@Valid Hero hero, BindingResult result,
//                                  HttpServletRequest request, Model model) {
//        String superpowerId = request.getParameter("superpowerId");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
//        hero.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
//
//        List<Organization> organizations = new ArrayList<>(); // 아예 새로 만드네?
//        if(organizationIds != null) {
//            for(String organizationId : organizationIds) {
//                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
//            }
//        } else {
//            // 아까 @Valid parameter(DTO obj) 로 validate 한 애들은 #fields.hasError 함수 사용해서 html 파일에서 에러를 볼 수 있었다.
//            // @Valid(이거는 항상 BindingResult 가 따라와야 함.) 의 경우 자동으로 어딘가에 obj - 에러 pair 가 저장됨.
//            // 이 경우 DTO 가 아닌 HttpServletRequest 로 데이터를 들여오기 때문에 그렇게 못한다. -- 수동으로 넣어줘야함.
//            /*
//            The BindingResult uses FieldErrors to keep track of which field has errors in our object.
//            Lucky for us, we can create our own FieldError and add it to the BindingResult.
//             */
//            FieldError error = new FieldError("hero", "organizations", "Must include one organization");
//            result.addError(error);
//        }
//        hero.setOrganizations(organizations);
//
//        if(result.hasErrors()) {
//            model.addAttribute("superpowers", superpowerDao.getAllSuperpowers());
//            model.addAttribute("organizations", organizationDao.getAllOrganizations());
//            model.addAttribute("hero", hero);
//            return "editHero";
//        }
//
//        heroDao.updateHero(hero);
//
//        return "redirect:/heroes";
//    }


}
