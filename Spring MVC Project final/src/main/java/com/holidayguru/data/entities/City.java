package com.holidayguru.data.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cities")
public class City extends BaseEntity{

    private String name;

    private Country country;

    public City() {
    }

    public City(String name, Country country){
        this.name = name;
        this.country = country;
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


    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
