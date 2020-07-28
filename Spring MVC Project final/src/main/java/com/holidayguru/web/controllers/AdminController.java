package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.web.controllers.models.viewModels.UserProfileViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView adminPage(Principal principal, ModelAndView modelAndView) {

        String username = principal.getName();

        List<UserProfileViewModel> userProfileViewModels = this.userService.getAllUsersWithoutRootRole(username);

        modelAndView.addObject("userProfileViewModels", userProfileViewModels);
        modelAndView.setViewName("admin");

        return modelAndView;
    }

}
