package com.holidayguru.web.controllers;

import com.holidayguru.data.entities.Host;
import com.holidayguru.services.interfaces.*;
import com.holidayguru.services.models.HostServiceModel;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.HostAddBindingModel;
import com.holidayguru.web.controllers.models.viewModels.HostViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/host")
public class HostController {
    private final CountryService countryService;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final HostService hostService;
    private final UserService userService;

    @Autowired
    public HostController(CountryService countryService, CityService cityService, ModelMapper modelMapper, CloudinaryService cloudinaryService, HostService hostService, UserService userService) {
        this.countryService = countryService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.hostService = hostService;
        this.userService = userService;
    }


    @GetMapping("/host-add")
    @PreAuthorize("isAuthenticated()")
    public String addHost(Model model){

        if (!model.containsAttribute("hostAddBindingModel")){
            model.addAttribute("hostAddBindingModel", new HostAddBindingModel());
        }

        return "/host-add";
    }


    @PostMapping("/host-add")
    @PreAuthorize("isAuthenticated()")
    public String addHostConfirm(@Valid @ModelAttribute("hostAddBindingModel") HostAddBindingModel hostAddBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {

        if (bindingResult.hasErrors() || hostAddBindingModel.getImage().isEmpty()){
            redirectAttributes.addFlashAttribute("hostAddBindingModel", hostAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hostAddBindingModel", bindingResult);

            return "redirect:host-add";
        }

        String username = principal.getName();
        HostServiceModel hostServiceModel = this.modelMapper.map(hostAddBindingModel, HostServiceModel.class);


        //todo the image should me smaller than 1mb!!!!

        hostServiceModel.setImage(this.cloudinaryService.uploadImg(hostAddBindingModel.getImage()));


        HostServiceModel hostServiceModel1 = this.hostService.saveHost(hostServiceModel, username);

        return "redirect:/home";
    }


    @GetMapping("/my-hosts")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myHosts(Principal principal, ModelAndView modelAndView){

        String username = principal.getName();

        UserServiceModel userServiceModel = this.userService.findByUsername(username);

       String id = userServiceModel.getId();


        List<HostViewModel> hostViewModelList = this.hostService.findAllByUserId(id)
                .stream()
                .map(h -> this.modelMapper.map(h, HostViewModel.class))
                .collect(Collectors.toList());



        modelAndView.addObject("hostViewModelList", hostViewModelList);
        modelAndView.setViewName("my-hosts");



        return modelAndView;
    }

    @GetMapping("/my-hosts/delete/{id}")
    public String hostDelete(@PathVariable String id){

        this.hostService.deleteHostById(id);

        return "redirect:/home";
    }





}
