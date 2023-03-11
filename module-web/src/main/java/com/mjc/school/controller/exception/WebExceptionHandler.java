package com.mjc.school.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = { MyCustomException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMyCustomException(MyCustomException ex) {
        // Handle MyCustomException
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
        // Handle all other exceptions
    }
}
