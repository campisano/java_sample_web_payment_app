package org.example.java_sample_web_payment_app.adapters;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class HTTPExceptionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPExceptionsController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(Exception exception, WebRequest request) {
        LOGGER.error("Request: {}, exception: {}", Objects.toString(request), exception.getMessage());

        CustomErrorResponse error = new CustomErrorResponse();
        error.message = "Not found: " + request.getContextPath();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleOthers(Exception exception, WebRequest request) {
        LOGGER.error("Request: {}, exception: {}", Objects.toString(request), exception.getMessage());

        CustomErrorResponse error = new CustomErrorResponse();
        error.message = "Internal server error";
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

class CustomErrorResponse {
    public String message;
}