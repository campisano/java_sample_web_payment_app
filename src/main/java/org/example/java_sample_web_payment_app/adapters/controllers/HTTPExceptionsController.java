package org.example.java_sample_web_payment_app.adapters.controllers;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class HTTPExceptionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            HTTPExceptionsController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(Exception exception,
            HttpServletRequest request) {
        LOGGER.error("Request: {}, exception: {}", getRequestData(request), exception);

        CustomErrorResponse error = new CustomErrorResponse();
        error.message = "Not found: " + request.getRequestURI();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class,
                        HttpRequestMethodNotSupportedException.class
                      })
    public ResponseEntity<CustomErrorResponse> handleJsonParserException(
        Exception exception,
        HttpServletRequest request) {
        LOGGER.error("Request: {}, exception: {}", getRequestData(request), exception);

        CustomErrorResponse error = new CustomErrorResponse();
        error.message = "Bad request";
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleOthers(Exception exception,
            HttpServletRequest request) {
        LOGGER.error("Request: {}, exception: {}", getRequestData(request), exception);

        CustomErrorResponse error = new CustomErrorResponse();
        error.message = "Internal server error";
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRequestData(HttpServletRequest request) {
        try {
            return request.getReader().lines().collect(Collectors.joining());
        } catch(Exception ex) {
            return "[request not available]";
        }
    }
}

class CustomErrorResponse {
    public String message;
}