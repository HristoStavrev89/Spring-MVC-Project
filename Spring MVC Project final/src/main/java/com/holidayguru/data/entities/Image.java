package com.holidayguru.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class Image extends BaseEntity{

    private String name;
    private String description;
    private User user;
    private Host host;

    public Image() {
    }

    @Column
    @NotNull(message = "Image name cannot be null.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
