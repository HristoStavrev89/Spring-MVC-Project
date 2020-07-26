package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.City;
import com.holidayguru.data.entities.Host;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.CityRepository;
import com.holidayguru.data.repositories.HostRepository;
import com.holidayguru.data.repositories.UserRepository;
import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.HostService;
import com.holidayguru.services.models.HostServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HostServiceImpl implements HostService {
    private final ModelMapper modelMapper;
    private final HostRepository hostRepository;
    private final UserRepository userRepository;
    private final CityService cityService;

    @Autowired
    public HostServiceImpl(ModelMapper modelMapper, HostRepository hostRepository, UserRepository userRepository, CityRepository cityRepository, CityService cityService) {
        this.modelMapper = modelMapper;
        this.hostRepository = hostRepository;
        this.userRepository = userRepository;

        this.cityService = cityService;
    }


    @Override
    public HostServiceModel saveHost(HostServiceModel hostServiceModel, String username) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s is not found", username)));

        Host host = this.modelMapper.map(hostServiceModel, Host.class);

        host.setUser(user);

        host.setActivity(hostServiceModel.getActivity());

        City city = this.modelMapper.map(this.cityService.findByName(hostServiceModel.getCity()), City.class);

        //todo set city

        host.setCity(city);
        //todo check the comments
        //todo check the dates


        //todo check

        this.hostRepository.saveAndFlush(host);
        System.out.println();

        return this.modelMapper.map(host, HostServiceModel.class);
    }
}
