package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.CountryServiceModel;

public interface CountryService {

    void seedCountries();

    CountryServiceModel findByName(String name);

}
