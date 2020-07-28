package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.RoleServiceModel;
import com.holidayguru.web.controllers.models.viewModels.RoleView;

import java.util.List;
import java.util.Set;

public interface RoleService {

    void seedRoles();
    Set<RoleServiceModel> findAll();

    RoleServiceModel findByAuthority(String authority);

    List<RoleServiceModel> findAllRoles();

    List<RoleView> findAllRolesViewModels();


}
