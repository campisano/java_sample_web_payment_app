package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void test_creation() {
        Transaction transaction = new Transaction("number");

        Assertions.assertEquals("number", transaction.getNumber());
    }

    @Test
    public void test_creation_empty_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("");
        });
    }

    @Test
    public void test_creation_null_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null);
        });
    }
}
