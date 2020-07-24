package com.holidayguru.services.interfaces;

import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, UserProfileEditBindingModel userProfileEditBindingModel);
}
