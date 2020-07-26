package com.holidayguru.services.models;

import com.holidayguru.data.entities.Country;

public class CityServiceModel extends BaseServiceModel {


    private String name;

    private CountryServiceModel country;

    public CityServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryServiceModel getCountry() {
        return country;
    }

    public void setCountry(CountryServiceModel country) {
        this.country = country;
    }
}
