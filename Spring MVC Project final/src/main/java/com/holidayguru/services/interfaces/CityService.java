package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.CityServiceModel;

public interface CityService {

    void seedCities();

    CityServiceModel findByName(String name);

}
