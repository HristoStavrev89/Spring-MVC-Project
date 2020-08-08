package com.holidayguru.services.implementations;

import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.UserRepository;
import com.holidayguru.exceptions.UserNotFoundException;
import com.holidayguru.exceptions.UsernameAlreadyTakenException;
import com.holidayguru.services.interfaces.RoleService;
import com.holidayguru.services.models.RoleServiceModel;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {

//    private final UserRepository userRepository;
//    private final ModelMapper modelMapper;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final RoleService roleService;
//    private final HostService hostService;

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository userRepository;
    @Mock
    RoleServiceImpl roleService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    HostServiceImpl hostService;


    User user;
    UserServiceModel userServiceModel;
    UserProfileEditBindingModel userProfileEditBindingModel;

    @Before
    public void initTests(){
        user = new User();
        userServiceModel = new UserServiceModel();
        userProfileEditBindingModel = new UserProfileEditBindingModel();

        userServiceModel.setFirstName("Pesho");
        userServiceModel.setSecondName("Peshev");
        userServiceModel.setEmail("pesho@abv.bg");
        userServiceModel.setPassword("123456");
        userServiceModel.setAuthorities(Set.of(new RoleServiceModel()));
        userServiceModel.setUsername("pesho123");


    }

    @Test
    public void registerUser_ShouldWorkIfEverythingIsOk(){
        Mockito.when(modelMapper.map(userServiceModel, User.class))
                .thenReturn(user);

        Mockito.when(modelMapper.map(user, UserServiceModel.class))
                .thenReturn(userServiceModel);

        Mockito.when(userRepository.saveAndFlush(user))
                .thenReturn(user);

        UserServiceModel result = service.registerUser(userServiceModel);

        Mockito.verify(modelMapper).map(userServiceModel, User.class);
        Mockito.verify(userRepository).saveAndFlush(user);
        Mockito.verify(modelMapper).map(user, UserServiceModel.class);

        assertEquals(userServiceModel, result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void loadUserByUsername_ShouldThrowIllegalArgumentException(){
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenReturn(Optional.of(user));

        User userResult = (User) service.loadUserByUsername("pesho123");
        assertEquals(user, userResult);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_ShouldTjrowUSerNotFoundExceptionIfNotExists(){
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenThrow(UsernameNotFoundException.class);
        service.loadUserByUsername("pesho123");
    }

    @Test
    public void findByUsername_ShouldWorkIfTheUSerExists(){
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenReturn(Optional.of(user));

    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_ThrowUsernameNotFoundExceptionIfNotExists(){
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenThrow(UsernameNotFoundException.class);
        service.loadUserByUsername("pesho123");
    }

    @Test
    public void editUserProfile_ShouldEditTheProfile(){
        user.setPassword("123456");
        userServiceModel.setUsername("pesho123");
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenReturn(Optional.of(user));
        Mockito.when(bCryptPasswordEncoder.matches("123456", "123456"))
                .thenReturn(true);
        Mockito.when(bCryptPasswordEncoder.encode("123456"))
                .thenReturn("123456");
        service.editUserProfile(userServiceModel, userProfileEditBindingModel);
        assertEquals(user.getEmail(), userServiceModel.getEmail());
    }


    @Test(expected = UsernameAlreadyTakenException.class)
    public void registerUser_ThrowExceptionIfUsernameAlreadyExist(){
        Mockito.when(userRepository.findByUsername("pesho123"))
                .thenThrow(UsernameAlreadyTakenException.class);
        service.registerUser(userServiceModel);
    }

    @Test
    public void findUserById_ShouldReturnUserIfExist(){
        Mockito.when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));
        service.findUserById("1");
    }

    @Test(expected = UserNotFoundException.class)
    public void findUserById_ThrowExceptionIfNotExist(){
        Mockito.when(service.findUserById("1"))
                .thenThrow(UserNotFoundException.class);
    }



}