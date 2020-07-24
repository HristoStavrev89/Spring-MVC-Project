package com.holidayguru.services.models;

public class CountryServiceModel extends BaseServiceModel {

    private String name;

    public CountryServiceModel() {
    }

    public CountryServiceModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
