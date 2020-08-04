package com.holidayguru.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Host not found.")
public class HostNotFoundException extends RuntimeException {


    public HostNotFoundException() {
    }

    public HostNotFoundException(String message) {
        super(message);
    }
}
