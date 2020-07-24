package com.holidayguru.data.entities.enums;

public enum Activity {

    SPORT("Sport"),
    COUSIN("Cousine"),
    ARCHITECTURE("Architecture"),
    CULTURE("Culture"),
    ART("Art"),
    OTHER("Other");

    Activity(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
