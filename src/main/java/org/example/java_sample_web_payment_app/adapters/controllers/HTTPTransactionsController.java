package org.example.java_sample_web_payment_app.adapters.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPTransactionsPostRequest;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.OperationTypeIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateTransactionUsecasePort;
import org.example.java_sample_web_payment_app.domain.DomainValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class HTTPTransactionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPTransactionsController.class);

    private CreateTransactionUsecasePort createTransactionUsecase;

    public HTTPTransactionsController(CreateTransactionUsecasePort createTransactionUsecase) {
        this.createTransactionUsecase = createTransactionUsecase;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody Optional<HTTPTransactionsPostRequest> body) {
        LOGGER.info("method={}, path={}, body={}", request.getMethod(), request.getRequestURI(), body.orElse(null));

        if (!body.isPresent()) {
            LOGGER.error("request without body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            createTransactionUsecase.execute(body.get().accountId, body.get().operationTypeId, body.get().amount);
            LOGGER.info("created, request={}", body.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (DomainValidationException exception) {
            LOGGER.error("exception, message={}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        } catch (AccountIdNotExistsException | OperationTypeIdNotExistsException exception) {
            LOGGER.error("exception, message={}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
