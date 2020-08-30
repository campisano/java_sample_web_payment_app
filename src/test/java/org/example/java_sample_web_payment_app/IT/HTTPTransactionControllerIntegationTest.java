package org.example.java_sample_web_payment_app.IT;

import java.math.BigDecimal;
import java.util.Optional;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPTransactionsPostRequest;
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
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
public class HTTPTransactionControllerIntegationTest {

    @Container
    public static final PostgreSQLContainer<?> postgresContainer = CustomPostgresqlContainer.getInstance();

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test_post() throws Exception {
        HTTPAccountsPostRequest body1 = new HTTPAccountsPostRequest();
        body1.documentNumber = "12345678900";
        body1.creditLimit = new BigDecimal(5000);
        ResponseEntity<?> response1 = rest.postForEntity("/accounts", body1, null);
        Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        HTTPTransactionsPostRequest body2 = new HTTPTransactionsPostRequest();
        body2.accountId = Long.valueOf(1);
        body2.operationTypeId = Long.valueOf(4);
        body2.amount = BigDecimal.valueOf(12345, 2);

        ResponseEntity<?> response2 = rest.postForEntity("/transactions", body2, null);

        Assertions.assertEquals(HttpStatus.CREATED, response2.getStatusCode());
    }

    @Test
    public void test_post_not_exists() throws Exception {
        HTTPTransactionsPostRequest body = new HTTPTransactionsPostRequest();
        body.accountId = Long.valueOf(1);
        body.operationTypeId = Long.valueOf(4);
        body.amount = BigDecimal.valueOf(12345, 2);

        ResponseEntity<?> response = rest.postForEntity("/transactions", body, null);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void test_post_empty() throws Exception {
        ResponseEntity<?> response = rest.postForEntity("/transactions", Optional.empty(), null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
