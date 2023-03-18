package com.mjc.school.controller.exception;

import java.time.ZonedDateTime;


public class ExceptionDetails {

    private String message;
    private ZonedDateTime timeStamp;

    public ExceptionDetails() {
    }

    public ExceptionDetails(String message, ZonedDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}

