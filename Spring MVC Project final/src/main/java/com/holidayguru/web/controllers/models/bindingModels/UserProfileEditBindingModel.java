package com.holidayguru.web.controllers.models.bindingModels;

import com.holidayguru.data.entities.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.util.Set;

public class UserProfileEditBindingModel {


    private String username;
    private String password;

    private String newPassword;
    private String confirmPassword;

    private String email;
    private String firstName;
    private String secondName;



    public UserProfileEditBindingModel() {
    }

    @Length(min = 5, max = 20, message = "Username length must be between 5 and 20 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Email(message = "Enter valid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 3, max = 20, message = "Must be between 3 and 20 characters.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 3, max = 20, message = "Must be between 3 and 20 characters.")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
