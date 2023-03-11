package com.mjc.school.controller.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(Long id) {
        super(String.format("Resource with ID %d not found", id));
    }


}
