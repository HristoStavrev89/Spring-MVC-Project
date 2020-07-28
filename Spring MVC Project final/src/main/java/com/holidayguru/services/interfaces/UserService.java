package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import com.holidayguru.web.controllers.models.viewModels.UserProfileViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, UserProfileEditBindingModel userProfileEditBindingModel);

    List<UserServiceModel> findAllUsers();

    List<UserProfileViewModel> getAllUsersWithoutRootRole(String username);

    void deleteUserById(String userId);

    void changeUserRole(String userId, String role);

}
