package com.holidayguru.web.api.controllers;

import com.holidayguru.services.interfaces.HostService;
import com.holidayguru.web.api.models.HostResposneModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class HostRestController {
    private final HostService hostService;
    private final ModelMapper modelMapper;


    @Autowired
    public HostRestController(HostService hostService, ModelMapper modelMapper) {
        this.hostService = hostService;
        this.modelMapper = modelMapper;
    }



    @GetMapping("/hosts/all")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public List<HostResposneModel> getAllHosts(){

        return this.hostService.findAll()
                .stream()
                .map(h -> this.modelMapper.map(h, HostResposneModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/hosts/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public HostResposneModel getAllHosts(@PathVariable String id){

        return this.modelMapper.map(this.hostService.findByHostId(id), HostResposneModel.class);



    }


    @RequestMapping(
            value = "/hosts/delete/{id}",
            method = {RequestMethod.GET, RequestMethod.PUT}
    )
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public void deleteHost (@PathVariable String id){

        this.hostService.deleteHostById(id);

    }

}
