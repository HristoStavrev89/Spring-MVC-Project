package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
public class HostController {
    private final CountryService countryService;
    private final CityService cityService;

    @Autowired
    public HostController(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }


    @GetMapping("/add")
    public String addHost(){


        this.cityService.seedCities();

        return "host-add";
    }


    @GetMapping("/my-hosts")
    public String myHosts(){
        return "my-hosts";
    }



}
