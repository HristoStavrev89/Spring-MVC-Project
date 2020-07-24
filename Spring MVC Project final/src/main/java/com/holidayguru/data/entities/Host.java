package com.holidayguru.data.entities;

import com.holidayguru.data.entities.enums.Activity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hosts")
public class Host extends BaseEntity{

    private Activity activity;
    private String description;
    private City city;

    private User user;
    private boolean isAvailable;
    private boolean isFree;

    private Set<Comment> comments;
    private Set<Image> images;

    //todo da dobavq list sus comentari koito 6te pazi hosta kakto i images

    public Host() {
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

    @Column
    @NotNull
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Column
    @NotNull
    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
