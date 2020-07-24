package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.Country;
import com.holidayguru.data.entities.enums.Countries;
import com.holidayguru.data.repositories.CountryRepository;
import com.holidayguru.exceptions.CountryNotFoundException;
import com.holidayguru.services.interfaces.CountryService;
import com.holidayguru.services.models.CountryServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCountries() {

        if (this.countryRepository.count() == 0) {
            List<Country> countryList = new ArrayList<>();
            for (Countries country : Countries.values()) {
                countryList.add(new Country(country.name()));
            }

            this.countryRepository.saveAll(countryList);

        }


    }

    @Override
    public CountryServiceModel findByName(String name) {
        Country country = this.countryRepository.findByName(name)
                .orElseThrow(() -> new CountryNotFoundException("Country not found."));
        return this.modelMapper.map(country, CountryServiceModel.class);
    }
}
