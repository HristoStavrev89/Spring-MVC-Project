package com.holidayguru.web.controllers.models.viewModels;

public class CityViewModel {

    private String name;
    private CountryViewModel country;


    public CityViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CountryViewModel getCountry() {
        return country;
    }

    public void setCountry(CountryViewModel country) {
        this.country = country;
    }
}
