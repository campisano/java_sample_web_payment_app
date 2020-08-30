package org.example.java_sample_web_payment_app.adapters.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.adapters.controllers.responses.HTTPAccountsGetResponse;
import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.in.RetrieveAccountUsecasePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HTTPAccountsControllerTest {

    private CreateAccountUsecasePort createAccountUsecase;
    private RetrieveAccountUsecasePort retrieveAccountUsecase;

    @BeforeEach
    private void prepareNewMocks() {
        createAccountUsecase = Mockito.mock(CreateAccountUsecasePort.class);
        retrieveAccountUsecase = Mockito.mock(RetrieveAccountUsecasePort.class);
    }

    @AfterEach
    private void noMoreMockInterations() {
        Mockito.verifyNoMoreInteractions(Mockito.ignoreStubs(createAccountUsecase, retrieveAccountUsecase));
    }

    @Test
    public void test_post() throws Exception {
        HTTPAccountsController controller = new HTTPAccountsController(createAccountUsecase, null);
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ documentNumber = "12345678900"; creditLimit = new BigDecimal(5000); }};

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(createAccountUsecase, Mockito.times(1)).execute(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_post_exists() throws Exception {
        Mockito.doThrow(AccountAlreadyExistsException.class).when(createAccountUsecase).execute(Mockito.any(),
                Mockito.any());
        HTTPAccountsController controller = new HTTPAccountsController(createAccountUsecase, null);
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ documentNumber = "12345678900"; creditLimit = new BigDecimal(5000); }};

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Mockito.verify(createAccountUsecase, Mockito.times(1)).execute(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_post_empty() throws Exception {
        HTTPAccountsController controller = new HTTPAccountsController(createAccountUsecase, null);

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.empty());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(createAccountUsecase, Mockito.times(0)).execute(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_get() throws Exception {
        AccountDTO expectedAccount = new AccountDTO() {{ accountId = Long.valueOf(1); documentNumber = "12345678900"; creditLimit = new BigDecimal(5000); }};
        Mockito.doReturn(expectedAccount).when(retrieveAccountUsecase).execute(Mockito.any());
        HTTPAccountsController controller = new HTTPAccountsController(null, retrieveAccountUsecase);

        ResponseEntity<HTTPAccountsGetResponse> response = controller.get(mockRequest(), Long.valueOf(1));

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedAccount.accountId, response.getBody().accountId);
        Assertions.assertEquals(expectedAccount.documentNumber, response.getBody().documentNumber);
        Assertions.assertEquals(expectedAccount.creditLimit, response.getBody().creditLimit);
        Mockito.verify(retrieveAccountUsecase, Mockito.times(1)).execute(Mockito.any());
    }

    @Test
    public void test_get_not_exists() throws Exception {
        Mockito.doThrow(AccountIdNotExistsException.class).when(retrieveAccountUsecase).execute(Mockito.any());
        HTTPAccountsController controller = new HTTPAccountsController(null, retrieveAccountUsecase);

        ResponseEntity<HTTPAccountsGetResponse> response = controller.get(mockRequest(), Long.valueOf(1));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
        Mockito.verify(retrieveAccountUsecase, Mockito.times(1)).execute(Mockito.any());
    }

    @Test
    public void test_get_empty() throws Exception {
        HTTPAccountsController controller = new HTTPAccountsController(null, retrieveAccountUsecase);

        ResponseEntity<HTTPAccountsGetResponse> response = controller.get(mockRequest(), null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
        Mockito.verify(retrieveAccountUsecase, Mockito.times(0)).execute(Mockito.any());
    }

    private HttpServletRequest mockRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        Mockito.when(request.getRequestURI()).thenReturn("/authors");
        return request;
    }
}
