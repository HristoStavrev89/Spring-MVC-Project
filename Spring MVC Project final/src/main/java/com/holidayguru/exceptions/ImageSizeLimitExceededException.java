package com.holidayguru.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Above the file size limit.")
public class ImageSizeLimitExceededException extends RuntimeException {


    public ImageSizeLimitExceededException() {
    }

    public ImageSizeLimitExceededException(String message) {
        super(message);
    }


}
