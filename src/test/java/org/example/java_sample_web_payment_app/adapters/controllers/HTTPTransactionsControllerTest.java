package org.example.java_sample_web_payment_app.adapters.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPTransactionsPostRequest;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.OperationTypeIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateTransactionUsecasePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HTTPTransactionsControllerTest {

    private CreateTransactionUsecasePort usecase;

    @BeforeEach
    private void prepareNewMocks() {
        usecase = Mockito.mock(CreateTransactionUsecasePort.class);
    }

    @AfterEach
    private void noMoreMockInterations() {
        Mockito.verifyNoMoreInteractions(Mockito.ignoreStubs(usecase));
    }

    @Test
    public void test_post() throws Exception {
        HTTPTransactionsController controller = new HTTPTransactionsController(usecase);
        HTTPTransactionsPostRequest body = new HTTPTransactionsPostRequest() {
            {
                accountId = Long.valueOf(1);
                operationTypeId = Long.valueOf(4);
                amount = BigDecimal.valueOf(12343, 2);
            }
        };

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(usecase, Mockito.times(1)).execute(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void test_post_account_not_exists() throws Exception {
        Mockito.doThrow(AccountIdNotExistsException.class).when(usecase).execute(Mockito.any(), Mockito.any(),
                Mockito.any());
        HTTPTransactionsController controller = new HTTPTransactionsController(usecase);
        HTTPTransactionsPostRequest body = new HTTPTransactionsPostRequest() {
            {
                accountId = Long.valueOf(1);
                operationTypeId = Long.valueOf(4);
                amount = BigDecimal.valueOf(12343, 2);
            }
        };

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
        Mockito.verify(usecase, Mockito.times(1)).execute(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void test_post_operation_not_exists() throws Exception {
        Mockito.doThrow(OperationTypeIdNotExistsException.class).when(usecase).execute(Mockito.any(), Mockito.any(),
                Mockito.any());
        HTTPTransactionsController controller = new HTTPTransactionsController(usecase);
        HTTPTransactionsPostRequest body = new HTTPTransactionsPostRequest() {
            {
                accountId = Long.valueOf(1);
                operationTypeId = Long.valueOf(4);
                amount = BigDecimal.valueOf(12343, 2);
            }
        };

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
        Mockito.verify(usecase, Mockito.times(1)).execute(Mockito.any(), Mockito.any(), Mockito.any());
    }

    private HttpServletRequest mockRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        Mockito.when(request.getRequestURI()).thenReturn("/transactions");
        return request;
    }
}