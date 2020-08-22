package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void test_creation() {
        Account account = new Account("number");

        Assertions.assertEquals("number", account.getNumber());
    }

    @Test
    public void test_creation_empty_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account("");
        });
    }

    @Test
    public void test_creation_null_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(null);
        });
    }
}
