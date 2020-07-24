package com.holidayguru.data.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{


    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Column
    @NotNull(message = "The country cannot be null.")
    @Length(min = 3, message = "The name must be with more than 3 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
