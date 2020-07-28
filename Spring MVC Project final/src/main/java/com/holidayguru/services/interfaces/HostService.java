package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.HostServiceModel;

import java.util.List;

public interface HostService {


    HostServiceModel saveHost(HostServiceModel hostServiceModel, String username);

    List<HostServiceModel> findAllByUserId(String userId);

    void deleteHostById(String hostId);

    List<HostServiceModel> findAllByCity(String city, String activity);
}
