package com.holidayguru.web.controllers.models.bindingModels;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class HostAddBindingModel {

    private String name;
    private String activity;
    private String description;
    private String city;

    private MultipartFile image;

    private LocalDate availableFrom;
    private LocalDate until;

    private boolean isFree;


    public HostAddBindingModel() {
    }


    @Length(min = 6, message = "The name must be more than 6 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Length(min = 20, message = "The description must be more than 20 characters.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }




    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @FutureOrPresent(message = "The date cannot be in the past.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getAvailableFrom() {
        return availableFrom;
    }


    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    @FutureOrPresent(message = "The date cannot be in the past.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }


}
