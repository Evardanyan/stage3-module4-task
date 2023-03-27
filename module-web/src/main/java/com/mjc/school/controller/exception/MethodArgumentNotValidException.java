//package com.mjc.school.controller.exception;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.validation.BindingResult;
//
//import java.net.BindException;
//
//public class MethodArgumentNotValidException extends BindException {
//
//    private final MethodParameter parameter;
//
//    // Constructor with message, cause, and binding result
//    public MethodArgumentNotValidException(MethodParameter parameter, BindingResult bindingResult) {
//        super(String.valueOf(bindingResult));
//        this.parameter = parameter;
//    }
//
//    // Getter method for parameter
//    public MethodParameter getParameter() {
//        return this.parameter;
//    }
//
//
//
//    // Other methods
//    // ...
//}
