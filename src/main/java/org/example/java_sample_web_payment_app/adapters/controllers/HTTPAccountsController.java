package org.example.java_sample_web_payment_app.adapters.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class HTTPAccountsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPAccountsController.class);

    private CreateAccountUsecasePort usecase;

    public HTTPAccountsController(CreateAccountUsecasePort usecase) {
        this.usecase = usecase;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody Optional<HTTPAccountsPostRequest> body) {
        LOGGER.info("method={}, path={}, body={}", request.getMethod(), request.getRequestURI(), body);

        if (!body.isPresent()) {
            LOGGER.error("request without body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            usecase.execute(body.get().document_number);
            LOGGER.info("created, document_number={}", body.get().document_number);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (AccountAlreadyExistsException exception) {
            LOGGER.error("exception, message={}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
