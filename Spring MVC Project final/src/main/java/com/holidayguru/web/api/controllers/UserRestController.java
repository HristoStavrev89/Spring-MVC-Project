package com.holidayguru.web.api.controllers;

import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.web.api.models.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }



    @GetMapping("/all/users")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public List<UserResponseModel> getAllUsers(){
        return this.userService.findAllUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, UserResponseModel.class))
                .collect(Collectors.toList());
    }
}
