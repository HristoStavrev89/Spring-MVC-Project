package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRoles();
    Set<RoleServiceModel> findAll();
    RoleServiceModel findByAuthority(String authority);

}
