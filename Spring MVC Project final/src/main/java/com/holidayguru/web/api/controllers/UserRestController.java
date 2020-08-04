package com.holidayguru.web.api.controllers;

import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.web.api.models.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/users/all")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public List<UserResponseModel> getAllUsers(){
        return this.userService.findAllUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, UserResponseModel.class))
                .collect(Collectors.toList());
    }


    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public UserResponseModel getAllUsers(@PathVariable String id){

        return this.modelMapper.map(this.userService.findUserById(id), UserResponseModel.class);

    }


    @RequestMapping(
            value = "/users/delete/{id}",
            method = {RequestMethod.GET, RequestMethod.PUT}
    )
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public void deleteUser(@PathVariable String id){
        this.userService.deleteUserById(id);
    }




}
