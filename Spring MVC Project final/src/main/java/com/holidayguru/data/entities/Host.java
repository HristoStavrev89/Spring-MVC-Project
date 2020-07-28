package com.holidayguru.data.entities;

import com.holidayguru.data.entities.enums.Activity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hosts")
public class Host extends BaseEntity{

    private String name;
    private Activity activity;
    private String description;
    private City city;

    private String image;

    private LocalDate availableFrom;
    private LocalDate until;

    private User user;


//    private Set<Comment> comments;


    //todo da dobavq list sus comentari koito 6te pazi hosta kakto i images

    public Host() {
    }

    @NotNull
    @Column(name = "name")
    @Length(min = 6, message = "The name must be more than 6 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @NotNull
    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image_url")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NotNull
    @Column(name = "available_from")
    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    @NotNull
    @Column(name = "unitl")
    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }

    @ManyToOne
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    public Set<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(Set<Comment> comments) {
//        this.comments = comments;
//    }




}
