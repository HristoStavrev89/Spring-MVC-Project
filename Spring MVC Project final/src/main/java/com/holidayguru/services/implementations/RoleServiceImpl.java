package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.Role;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.RoleRepository;
import com.holidayguru.services.interfaces.RoleService;
import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.services.models.RoleServiceModel;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.viewModels.RoleView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;



    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public void seedRoles() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAll() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }

    @Override
    public List<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleView> findAllRolesViewModels() {
        return this.roleRepository.findAll()
                .stream()
                .map(role -> this.modelMapper.map(role, RoleView.class))
                .filter(r -> !r.getAuthority().contains("ROOT"))
                .collect(Collectors.toList());
    }


}
