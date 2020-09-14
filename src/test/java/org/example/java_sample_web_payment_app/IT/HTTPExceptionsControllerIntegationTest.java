package org.example.java_sample_web_payment_app.IT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
public class HTTPExceptionsControllerIntegationTest {

    @Container
    public static final PostgreSQLContainer<?> postgresContainer =
        CustomPostgresqlContainer.getInstance();

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void test_not_found() throws Exception {
        ResponseEntity<?> response = rest.postForEntity("/not_existent", null, null);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void test_bad_request() throws Exception {
        ResponseEntity<?> response = rest.exchange("/accounts", HttpMethod.POST,
                                     HttpEntity.EMPTY, Object.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
