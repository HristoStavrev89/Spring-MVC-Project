package com.holidayguru.web.controllers;

import com.holidayguru.services.interfaces.RoleService;
import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.web.controllers.models.bindingModels.RoleBindingModel;
import com.holidayguru.web.controllers.models.viewModels.RoleView;
import com.holidayguru.web.controllers.models.viewModels.UserProfileViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView adminPage(Principal principal, ModelAndView modelAndView) {

        String username = principal.getName();

        List<UserProfileViewModel> userProfileViewModels = this.userService.getAllUsersWithoutRootRole(username);

        List<RoleView> allRoles = this.roleService.findAllRolesViewModels();

        modelAndView.addObject("roleBindingModel", new RoleBindingModel());
        modelAndView.addObject("userProfileViewModels", userProfileViewModels);
        modelAndView.addObject("allRoles", allRoles);
        modelAndView.setViewName("admin");

        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String id){


        this.userService.deleteUserById(id);
        return "redirect:/admin";

    }

    @PostMapping("/change-role/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeRole(@PathVariable String id, RoleBindingModel roleBindingModel){
        this.userService.changeUserRole(id, roleBindingModel.getRole());
        return "redirect:/admin";
    }


}
