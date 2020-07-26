package com.holidayguru.services.models;

import com.holidayguru.data.entities.City;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.entities.enums.Activity;
import com.holidayguru.data.entities.enums.Cities;

import java.time.LocalDate;

public class HostServiceModel extends BaseServiceModel {


    private String name;
    private Activity activity;
    private String description;

    private String city;

    private String image;

    private LocalDate availableFrom;
    private LocalDate until;

    private UserServiceModel user;
    private boolean isFree;

    public HostServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
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

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

}
