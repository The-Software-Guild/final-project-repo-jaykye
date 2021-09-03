package jaykye.finalproject.controller;

public class myValidation {
    public static boolean cityNameHasError(String cityName){
        if (cityName == ""){
            return true;
        }
        return false;
    }
}
