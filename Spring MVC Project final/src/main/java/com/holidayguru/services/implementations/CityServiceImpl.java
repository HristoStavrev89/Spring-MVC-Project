package com.holidayguru.services.implementations;

import com.holidayguru.data.repositories.CityRepository;
import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    private final CountryService countryService;
    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CountryService countryService, CityRepository cityRepository) {
        this.countryService = countryService;
        this.cityRepository = cityRepository;
    }


    @Override
    public void seedCities() {

        if (this.cityRepository.count() == 0) {
            this.countryService.seedCountries();

            System.out.println();
        }


    }
}
