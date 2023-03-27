package com.mjc.school.controller.exception;

import com.mjc.school.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        String errorMessage = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Invalid value for parameter '%s': '%s'. Must be of type '%s'.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest().body(errorMessage);
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
//        System.out.println("Handling NotFoundException: " + e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleEntityNotFoundException(NotFoundException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(NOT_FOUND).body(exceptionDetails);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ExceptionDetails> handleException(Exception ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(exceptionDetails);
    }


}
