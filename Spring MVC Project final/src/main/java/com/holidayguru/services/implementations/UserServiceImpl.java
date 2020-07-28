package com.holidayguru.services.implementations;
import com.holidayguru.data.entities.Role;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.UserRepository;
import com.holidayguru.exceptions.UserNotFoundException;
import com.holidayguru.services.interfaces.RoleService;
import com.holidayguru.services.interfaces.UserService;
import com.holidayguru.services.models.RoleServiceModel;
import com.holidayguru.services.models.UserServiceModel;
import com.holidayguru.web.controllers.models.bindingModels.UserProfileEditBindingModel;
import com.holidayguru.web.controllers.models.viewModels.RoleView;
import com.holidayguru.web.controllers.models.viewModels.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        //da go dovur6a
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

    @Override
    public List<UserProfileViewModel> getAllUsersWithoutRootRole(String username) {


        List<UserProfileViewModel> userProfileViewModels = this.userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserProfileViewModel userProfileViewModel = this.modelMapper.map(user, UserProfileViewModel.class);

                    Set<RoleView> roleViews = userProfileViewModel.getAuthorities()
                            .stream()
                            .map(r -> this.modelMapper.map(r, RoleView.class)).collect(Collectors.toSet());

                    userProfileViewModel.setAuthorities(roleViews);

                    return userProfileViewModel;

                })
                .filter(usr -> usr.getAuthorities().size() != 4)
                .collect(Collectors.toList());

        return userProfileViewModels;
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(String userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Can't find user."));
        this.userRepository.delete(user);
    }

    @Override
    public void changeUserRole(String userId, String role) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Can't find user."));

        RoleServiceModel roleServiceModel = this.roleService.findByAuthority(role);

        user.getAuthorities().clear();
        user.getAuthorities().add(this.modelMapper.map(roleServiceModel, Role.class));

        this.userRepository.saveAndFlush(user);
    }
}
