package com.holidayguru.web.controllers;

import com.holidayguru.exceptions.ImageSizeLimitExceededException;
import com.holidayguru.exceptions.ResourceNotFoundException;
import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.HostService;
import com.holidayguru.web.controllers.models.bindingModels.HomeSearchModel;
import com.holidayguru.web.controllers.models.viewModels.HostViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final CityService cityService;
    private final HostService hostService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CityService cityService, HostService hostService, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.hostService = hostService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.addObject("homeSearchModel", new HomeSearchModel());
        modelAndView.setViewName("home");
        this.cityService.seedCities();

        //todo check the cities


        return modelAndView;
    }

    @PostMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView search(HomeSearchModel homeSearchModel, ModelAndView modelAndView) {


        List<HostViewModel> hostViewModels = this.hostService.findAllByCity(homeSearchModel.getCity(), homeSearchModel.getActivity())
                .stream()
                .map(h -> this.modelMapper.map(h, HostViewModel.class))
                .filter(a -> a.getActivity().equals(homeSearchModel.getActivity()))
                .collect(Collectors.toList());


        modelAndView.addObject("hostViewModels", hostViewModels);
        modelAndView.setViewName("results");
        return modelAndView;
    }





}
