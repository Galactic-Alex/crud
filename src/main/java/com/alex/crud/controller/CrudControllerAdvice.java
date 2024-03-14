package com.alex.crud.controller;

import com.alex.crud.service.UserServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CrudControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserServiceException.class})
    private ResponseEntity<Object> handleServiceExceptions(RuntimeException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();

        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
