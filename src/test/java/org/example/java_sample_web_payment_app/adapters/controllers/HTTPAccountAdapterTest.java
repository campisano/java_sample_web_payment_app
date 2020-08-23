package org.example.java_sample_web_payment_app.adapters.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.example.java_sample_web_payment_app.adapters.controllers.HTTPAccountsController;
import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HTTPAccountAdapterTest {

    @Test
    public void test_post() throws Exception {
        CreateAccountUsecasePort usecase = Mockito.mock(CreateAccountUsecasePort.class);
        HTTPAccountsController controller = new HTTPAccountsController(usecase);
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ document_number = "12345678900"; }};

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(usecase, Mockito.times(1)).execute(Mockito.any());
        Mockito.verifyNoMoreInteractions(usecase);
    }

    @Test
    public void test_post_exists() throws Exception {
        CreateAccountUsecasePort usecase = Mockito.mock(CreateAccountUsecasePort.class);
        Mockito.doThrow(AccountAlreadyExistsException.class).when(usecase).execute(Mockito.any());
        HTTPAccountsController controller = new HTTPAccountsController(usecase);
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ document_number = "12345678900"; }};

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.of(body));

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        Mockito.verify(usecase, Mockito.times(1)).execute(Mockito.any());
        Mockito.verifyNoMoreInteractions(usecase);
    }

    @Test
    public void test_post_empty() throws Exception {
        CreateAccountUsecasePort usecase = Mockito.mock(CreateAccountUsecasePort.class);
        HTTPAccountsController controller = new HTTPAccountsController(usecase);

        ResponseEntity<?> response = controller.post(mockRequest(), Optional.empty());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(usecase, Mockito.times(0)).execute(Mockito.any());
        Mockito.verifyNoMoreInteractions(usecase);
    }

    private HttpServletRequest mockRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        Mockito.when(request.getRequestURI()).thenReturn("/authors");
        return request;
    }
}
