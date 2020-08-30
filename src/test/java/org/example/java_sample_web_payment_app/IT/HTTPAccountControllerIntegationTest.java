package org.example.java_sample_web_payment_app.IT;

import java.math.BigDecimal;
import java.util.Optional;

import org.example.java_sample_web_payment_app.adapters.controllers.requests.HTTPAccountsPostRequest;
import org.example.java_sample_web_payment_app.adapters.controllers.responses.HTTPAccountsGetResponse;
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
public class HTTPAccountControllerIntegationTest {

    @Container
    public static final PostgreSQLContainer<?> postgresContainer = CustomPostgresqlContainer.getInstance();

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test_post() throws Exception {
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {
            {
                documentNumber = "12345678900";
                creditLimit = new BigDecimal(5000);
            }
        };

        ResponseEntity<?> response = rest.postForEntity("/accounts", body, null);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void test_post_exists() throws Exception {
        HTTPAccountsPostRequest body = new HTTPAccountsPostRequest() {
            {
                documentNumber = "12345678900";
                creditLimit = new BigDecimal(5000);
            }
        };
        ResponseEntity<?> response1 = rest.postForEntity("/accounts", body, null);
        Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());

        ResponseEntity<?> response2 = rest.postForEntity("/accounts", body, null);

        Assertions.assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
    }

    @Test
    public void test_post_empty() throws Exception {
        ResponseEntity<?> response = rest.postForEntity("/accounts", Optional.empty(), null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_get() throws Exception {
        Long existingAccountId = Long.valueOf(1);
        rest.postForEntity("/accounts", new HTTPAccountsPostRequest() {
            {
                documentNumber = "12345678900";
                creditLimit = new BigDecimal(5000);
            }
        }, null);

        ResponseEntity<HTTPAccountsGetResponse> response = rest.getForEntity("/accounts/{accountId}",
                HTTPAccountsGetResponse.class, existingAccountId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(existingAccountId, response.getBody().accountId);
        Assertions.assertEquals("12345678900", response.getBody().documentNumber);
        Assertions.assertEquals(new BigDecimal(5000), response.getBody().creditLimit);
    }

    @Test
    public void test_get_not_exists() throws Exception {
        Long existingAccountId = Long.valueOf(1);

        ResponseEntity<HTTPAccountsGetResponse> response = rest.getForEntity("/accounts/{accountId}",
                HTTPAccountsGetResponse.class, existingAccountId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
    }
}
