package com.gmaniliapp.transactionmanager.ws.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        logger.warn("UNEXPECTED METHOD ARGUMENT NOT VALID EXCEPTION");
        return super
            .handleMethodArgumentNotValid(ex, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        logger.warn("UNEXPECTED HTTP MESSAGE NOT READABLE EXCEPTION");
        return super.handleHttpMessageNotReadable(ex, headers, HttpStatus.BAD_REQUEST, request);
    }
}
