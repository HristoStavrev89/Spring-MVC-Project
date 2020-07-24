package com.holidayguru.services.implementations;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.UserRepository;
import com.holidayguru.services.interfaces.RoleService;
import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }


    //todo to continue, but first creating role service

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        //TODO Throw exception if already exist
//        if (this.userRepository.findByUsername(userServiceModel.getUsername()).isPresent()) {
//
//        }

        if (this.userRepository.count() == 0) {
            this.roleService.seedRoles();
            userServiceModel.setAuthorities(this.roleService.findAll());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userEntityOpt = userRepository.findByUsername(username);
        return userEntityOpt.
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.
                        getAuthorities());
    }

    @Override
    public UserServiceModel findByUsername(String username) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s is not found!", username)));

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, UserProfileEditBindingModel userProfileEditBindingModel) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s is not found!", userServiceModel.getUsername())));

        //todo
        if ( ! this.bCryptPasswordEncoder.matches(userServiceModel.getPassword(), user.getPassword())) {

            throw new UsernameNotFoundException("Incorrect password");
        }

       if (userProfileEditBindingModel.getNewPassword() != null) {
           user.setPassword(this.bCryptPasswordEncoder.encode(userProfileEditBindingModel.getNewPassword()));
       } else {
           user.setPassword(user.getPassword());
       }

       if (!userServiceModel.getEmail().equals(user.getEmail())) {
           user.setEmail(userServiceModel.getEmail());
       }

       if (!userServiceModel.getFirstName().equals(user.getFirstName())) {
           user.setFirstName(userServiceModel.getFirstName());
       }

       if (!userServiceModel.getSecondName().equals(user.getSecondName())) {
           user.setSecondName(userServiceModel.getSecondName());
       }

       return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }
}
