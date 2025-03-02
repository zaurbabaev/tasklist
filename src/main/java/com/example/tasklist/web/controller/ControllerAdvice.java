package com.example.tasklist.web.controller;

import com.example.tasklist.domain.exception.AccessDeniedException;
import com.example.tasklist.domain.exception.ExceptionBody;
import com.example.tasklist.domain.exception.ImageUploadException;
import com.example.tasklist.domain.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(final ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(final IllegalStateException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(final IllegalArgumentException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        return new ExceptionBody("Validation failed",
                e.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(final ConstraintViolationException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        Map<String, List<String>> errors = e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));
        exceptionBody.setErrors(errors);
        return exceptionBody;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAuthentication(final AuthenticationException e) {
        return new ExceptionBody("Authentication failed");
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleImageUpload(ImageUploadException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(final Exception e) {
        e.printStackTrace();
        return new ExceptionBody("Internal Error.");
    }

}
