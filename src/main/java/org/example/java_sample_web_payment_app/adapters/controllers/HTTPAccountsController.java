package org.example.java_sample_web_payment_app.adapters.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.adapters.controllers.responses.HTTPAccountsGetResponse;
import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.in.RetrieveAccountUsecasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class HTTPAccountsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPAccountsController.class);

    private CreateAccountUsecasePort createAccountUsecase;
    private RetrieveAccountUsecasePort retrieveAccountUsecase;

    public HTTPAccountsController(CreateAccountUsecasePort createAccountUsecase,
            RetrieveAccountUsecasePort retrieveAccountUsecase) {
        this.createAccountUsecase = createAccountUsecase;
        this.retrieveAccountUsecase = retrieveAccountUsecase;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody Optional<HTTPAccountsPostRequest> body) {
        LOGGER.info("method={}, path={}, body={}", request.getMethod(), request.getRequestURI(), body);

        if (!body.isPresent()) {
            LOGGER.error("request without body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            createAccountUsecase.execute(body.get().documentNumber);
            LOGGER.info("created, documentNumber={}", body.get().documentNumber);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (AccountAlreadyExistsException exception) {
            LOGGER.error("exception, message={}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @RequestMapping(value = "{accountId}", method = RequestMethod.GET)
    public ResponseEntity<HTTPAccountsGetResponse> get(HttpServletRequest request, @PathVariable Long accountId) {
        LOGGER.info("method={}, path={}, accountId={}", request.getMethod(), request.getRequestURI(), accountId);

        if (accountId == null || accountId.longValue() <= 0) {
            LOGGER.error("request without accountId");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            AccountDTO dto = retrieveAccountUsecase.execute(accountId);
            LOGGER.info("retrieved, accountId={}, documentNumber={}", dto.accountId, dto.documentNumber);
            return ResponseEntity.status(HttpStatus.OK).body(new HTTPAccountsGetResponse() {
                {
                    accountId = dto.accountId;
                    documentNumber = dto.documentNumber;
                }
            });
        } catch (AccountIdNotExistsException exception) {
            LOGGER.error("exception, message={}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
