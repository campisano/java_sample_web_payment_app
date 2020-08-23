package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void test_creation() {
        Account account = new Account(Long.valueOf(1), "document_number");

        Assertions.assertEquals(Long.valueOf(1), account.getAccountId());
        Assertions.assertEquals("document_number", account.getDocumentNumber());
    }

    @Test
    public void test_creation_zero_account_id() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(Long.valueOf(0), "document_number");
        });
    }

    @Test
    public void test_creation_null_account_id() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(null, "document_number");
        });
    }

    @Test
    public void test_creation_empty_document_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(Long.valueOf(1), "");
        });
    }

    @Test
    public void test_creation_null_document_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(Long.valueOf(1), null);
        });
    }
}
