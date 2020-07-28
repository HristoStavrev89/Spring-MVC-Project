package com.holidayguru.web.controllers.models.viewModels;

import com.holidayguru.data.entities.City;
import com.holidayguru.data.entities.enums.Activity;
import com.holidayguru.data.entities.enums.Cities;

import java.time.LocalDate;

public class HostViewModel {

    private String id;
    private String name;
    private String activity;
    private String description;
    private CityViewModel city;

    private String image;

    private LocalDate availableFrom;
    private LocalDate until;

    public HostViewModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CityViewModel getCity() {
        return city;
    }

    public void setCity(CityViewModel city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }
}
