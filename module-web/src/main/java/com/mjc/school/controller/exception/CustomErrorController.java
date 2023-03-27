package com.mjc.school.controller.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError() {
        return new ResponseEntity<>("An error occurred. Think before acting.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public String getErrorPath() {
        return "/error";
    }
}

