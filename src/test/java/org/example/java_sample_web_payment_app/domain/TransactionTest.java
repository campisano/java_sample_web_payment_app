package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void test_creation() {
        Transaction transaction = new Transaction("number", Transaction.Type.CASH);

        Assertions.assertEquals("number", transaction.getNumber());
        Assertions.assertEquals(Transaction.Type.CASH, transaction.getType());
    }

    @Test
    public void test_creation_null_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null, Transaction.Type.CASH);
        });
    }

    @Test
    public void test_creation_empty_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("", Transaction.Type.CASH);
        });
    }

    @Test
    public void test_creation_null_type() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", null);
        });
    }
}
