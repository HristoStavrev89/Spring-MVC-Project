package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.Country;
import com.holidayguru.data.entities.enums.Countries;
import com.holidayguru.data.repositories.CountryRepository;
import com.holidayguru.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public void seedCountries() {

        if (this.countryRepository.count() == 0) {

//            Country bulgaria = new Country("Bulgaria");
//            Country spain = new Country("Spain");
//            Country france = new Country("France");
//            Country germany = new Country("Germany");
//            Country england = new Country("England");

            List<Country> countryList = new ArrayList<>();

            for (Countries country : Countries.values()) {
                countryList.add(new Country(country.name()));
            }


            System.out.println();

        }


    }
}
