package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.HostService;
import com.holidayguru.web.controllers.models.viewModels.HostViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
    private final HostService hostService;
    private final ModelMapper modelMapper;


    @Autowired
    public GalleryController(HostService hostService, ModelMapper modelMapper) {
        this.hostService = hostService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public ModelAndView gallery(ModelAndView modelAndView){


        List<HostViewModel> hostViewModelList = this.hostService
                .findAll()
                .stream()
                .map(h -> this.modelMapper.map(h, HostViewModel.class))
                .collect(Collectors.toList());


        modelAndView.addObject("hostViewModelList", hostViewModelList);
        modelAndView.setViewName("gallery");

        return modelAndView;
    }

}
