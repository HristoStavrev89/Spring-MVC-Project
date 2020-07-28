package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CityService cityService;

    @Autowired
    public HomeController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String home(){

        this.cityService.seedCities();

        return "home";
    }

}
