package com.mjc.school.controller.exception;

import java.time.ZonedDateTime;

public class ExceptionDetails {

    private String message;

    private String exceptionName;

    private ZonedDateTime timeStamp;

    public ExceptionDetails() {
    }

    public ExceptionDetails(String message, String exceptionName, ZonedDateTime timeStamp) {
        this.message = message;
        this.exceptionName = exceptionName;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
