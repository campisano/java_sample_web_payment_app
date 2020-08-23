package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void test_creation() {
        Account account = new Account("document_number");

        Assertions.assertEquals("document_number", account.getDocumentNumber());
    }

    @Test
    public void test_creation_empty_document_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account("");
        });
    }

    @Test
    public void test_creation_null_document_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(null);
        });
    }
}
