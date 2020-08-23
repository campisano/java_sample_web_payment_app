package org.example.java_sample_web_payment_app.IT;

import java.util.Optional;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class HTTPAccountAdapterIntegationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test_post() {
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ document_number = "12345678900"; }};

        ResponseEntity<?> response = rest.postForEntity("/accounts", body, null);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void test_post_exists() {
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {{ document_number = "12345678900"; }};
        ResponseEntity<?> response1 = rest.postForEntity("/accounts", body, null);
        Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());

        ResponseEntity<?> response2 = rest.postForEntity("/accounts", body, null);

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response2.getStatusCode());
    }

    @Test
    public void test_post_empty() {
        ResponseEntity<?> response = rest.postForEntity("/accounts", Optional.empty(), null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
