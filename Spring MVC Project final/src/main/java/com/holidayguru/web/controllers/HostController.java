package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.CityService;
import com.holidayguru.services.interfaces.CloudinaryService;
import com.holidayguru.services.interfaces.CountryService;
import com.holidayguru.services.interfaces.HostService;
import com.holidayguru.services.models.HostServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.HostAddBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/host")
public class HostController {
    private final CountryService countryService;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final HostService hostService;

    @Autowired
    public HostController(CountryService countryService, CityService cityService, ModelMapper modelMapper, CloudinaryService cloudinaryService, HostService hostService) {
        this.countryService = countryService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.hostService = hostService;
    }


    @GetMapping("/host-add")
    @PreAuthorize("isAuthenticated()")
    public String addHost(Model model){

        if (!model.containsAttribute("hostAddBindingModel")){
            model.addAttribute("hostAddBindingModel", new HostAddBindingModel());
        }

        //Da go izvikam predi dobavqneto na host
        this.cityService.seedCities();
        return "/host-add";
    }


    @PostMapping("/host-add")
    @PreAuthorize("isAuthenticated()")
    public String addHostConfirm(@Valid @ModelAttribute("hostAddBindingModel") HostAddBindingModel hostAddBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {


        if (bindingResult.hasErrors() || hostAddBindingModel.getImage().isEmpty()){
            redirectAttributes.addFlashAttribute("hostAddBindingModel", hostAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hostAddBindingModel", bindingResult);

            //todo print the errors into the form

            System.out.println();
            return "redirect:host-add";
        }

        //todo dobavqneto na snimka da e ZADULJITELNO



        String username = principal.getName();
        HostServiceModel hostServiceModel = this.modelMapper.map(hostAddBindingModel, HostServiceModel.class);
        System.out.println();
        hostServiceModel.setImage(this.cloudinaryService.uploadImg(hostAddBindingModel.getImage()));
        System.out.println();

        HostServiceModel hostServiceModel1 = this.hostService.saveHost(hostServiceModel, username);

        System.out.println();

        return "redirect:/home";
    }



}
