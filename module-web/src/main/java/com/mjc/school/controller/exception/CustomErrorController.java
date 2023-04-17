package com.mjc.school.controller.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//@RestController
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    public ResponseEntity<String> handleError() {
//        return new ResponseEntity<>("An error occurred. Think before acting.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//    public String getErrorPath() {
//        return "/error";
//    }
//}
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public ResponseEntity<CustomErrorResponse> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error occurred. Think before acting.";

        if (statusCode != null) {
            httpStatus = HttpStatus.valueOf(statusCode);
            switch (httpStatus) {
                case NOT_FOUND:
                    message = "The requested URL was not found.";
                    break;
                case BAD_REQUEST:
                    message = "The request is invalid.";
                    break;
            }
        }

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    public String getErrorPath() {
        return "/error";
    }


}
