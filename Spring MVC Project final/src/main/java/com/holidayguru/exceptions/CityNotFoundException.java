package com.holidayguru.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "City not found.")
public class CityNotFoundException extends RuntimeException {


    public CityNotFoundException() {
    }

    public CityNotFoundException(String message) {
        super(message);
    }

}
