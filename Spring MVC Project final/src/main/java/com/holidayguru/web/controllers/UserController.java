package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserRegisterBindingModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import com.holidayguru.web.controllers.models.viewModels.UserProfileViewModel;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model){

        if (!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "register";
    }


    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRepeatPassword())) {

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";

        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";
    }


    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login()
    {
        return "login";
    }


    @GetMapping("/my-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myProfile(Principal principal, ModelAndView modelAndView){

        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName());
        UserProfileViewModel profileModel = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);

        modelAndView.addObject("profileModel", profileModel);
        modelAndView.setViewName("my-profile");
        return modelAndView;
    }

    @GetMapping("/profile-edit")
    @PreAuthorize("isAuthenticated()")
    public String myProfileEdit(Model model, Principal principal) {

        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName());
        UserProfileEditBindingModel userProfileEditBindingModel = this.modelMapper.map(userServiceModel, UserProfileEditBindingModel.class);

        if (!model.containsAttribute("userProfileEditBindingModel")) {
            model.addAttribute("userProfileEditBindingModel", userProfileEditBindingModel);
        }
        return "/profile-edit";
    }

    @PostMapping("/profile-edit")
    @PreAuthorize("isAuthenticated()")
    public String myProfileEditConfirm(@Valid @ModelAttribute("userProfileEditBindingModel") UserProfileEditBindingModel userProfileEditBindingModel,
                                             BindingResult bindingResult, RedirectAttributes redirectAttributes){

        //todo da validiram parolata - ako password e gre6no da vadi gre6ka i da ne se smenq s novata


        if (bindingResult.hasErrors() || !userProfileEditBindingModel.getNewPassword().equals(userProfileEditBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userProfileEditBindingModel", userProfileEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileEditBindingModel", bindingResult);

            return "redirect:profile-edit";
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userProfileEditBindingModel, UserServiceModel.class);

        this.userService.editUserProfile(userServiceModel, userProfileEditBindingModel);

        return "redirect:my-profile";
    }
}
