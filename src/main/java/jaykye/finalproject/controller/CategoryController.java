package jaykye.finalproject.controller;

import jaykye.finalproject.dao.CategoryDao;
import jaykye.finalproject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.net.*;
import java.io.*;

@Controller
public class CategoryController {
    @Autowired
    CategoryDao categoryDao;

//    public List<Category> getCategoriesFromServer(){
//        try{  URL yahoo = new URL("https://api.foursquare.com/v2/venues/categories?&client_id=KDWEBP3KJ0D352B0EINKSJ00NQ44B3UV5ZANPIGFOICR3FNM&client_secret=FWR4GJU2L1R34ZM2FIL3EQOSQSSWEUBAHSSHTIRAJA2PLPCL&v=20180928");
//            URLConnection yc = yahoo.openConnection();
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            yc.getInputStream()));
//            String inputLine;
//            int counter = 0;
//            while ((inputLine = in.readLine()) != null){
//                System.out.println(inputLine);
//                counter +=1;
//            }
//            System.out.println(counter);
//            in.close();
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//
//    return null;
//    }
}
