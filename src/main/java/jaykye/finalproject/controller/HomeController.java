package jaykye.finalproject.controller;

import jaykye.finalproject.dao.CategoryDao;
import jaykye.finalproject.dao.SearchHistoryDao;
import jaykye.finalproject.dao.VenueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    @Autowired
    VenueDao venueDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SearchHistoryDao searchHistoryDao;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("")
    public String viewHomePage(){
        return "home";
    }
}
