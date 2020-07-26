package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.City;
import com.holidayguru.data.entities.Country;
import com.holidayguru.data.repositories.CityRepository;
import com.holidayguru.data.repositories.CountryRepository;
import com.holidayguru.exceptions.CityNotFoundException;
import com.holidayguru.exceptions.CountryNotFoundException;
import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.CountryService;
import com.holidayguru.services.models.CityServiceModel;
import com.holidayguru.services.models.CountryServiceModel;
import org.hibernate.annotations.NotFoundAction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CountryService countryService;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityServiceImpl(CountryService countryService, CountryRepository countryRepository, CityRepository cityRepository, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCities() {

        if (this.cityRepository.count() == 0) {
            this.countryService.seedCountries();

            Country bulgaria = this.countryRepository.findByName("BULGARIA")
                    .orElseThrow(() -> new CountryNotFoundException("Country not found."));

            Country germany = this.countryRepository.findByName("GERMANY")
                    .orElseThrow(() -> new CountryNotFoundException("Country not found."));

            Country france = this.countryRepository.findByName("FRANCE")
                    .orElseThrow(() -> new CountryNotFoundException("Country not found."));

            Country spain = this.countryRepository.findByName("FRANCE")
                    .orElseThrow(() -> new CountryNotFoundException("Country not found."));

            Country england = this.countryRepository.findByName("FRANCE")
                    .orElseThrow(() -> new CountryNotFoundException("Country not found."));

            List<City> cityList = List.of(
                    new City("SOFIA", bulgaria),
                    new City("PLOVDIV", bulgaria),
                    new City("VARNA", bulgaria),
                    new City("BOURGAS", bulgaria),
                    new City("RUSE", bulgaria),

                    new City("BERLIN", germany),
                    new City("COLOGNE", germany),

                    new City("PARIS", france),

                    new City("BARCELONA", spain),
                    new City("MADRID", spain),
                    new City("VALENCIA", spain),

                    new City("LONDON", england)
            );

            this.cityRepository.saveAll(cityList);
        }


    }

    @Override
    public CityServiceModel findByName(String name) {
        City city = this.cityRepository.findByName(name).orElseThrow(() -> new CityNotFoundException("City not found"));

        return this.modelMapper.map(city, CityServiceModel.class);
    }


}
