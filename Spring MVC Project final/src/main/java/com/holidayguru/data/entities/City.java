package com.holidayguru.data.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cities")
public class City extends BaseEntity{

    private String name;
    private String region;
    private Country country;

    public City() {
    }

    @Column
    @NotNull(message = "The name of the city cannot be null.")
    @Length(min = 3, message = "The name must be with more than 3 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column
    @NotNull(message = "The region cannot be null.")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
