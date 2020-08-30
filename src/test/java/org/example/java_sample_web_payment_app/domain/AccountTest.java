package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void test_creation() throws Exception {
        Account account = new Account(Long.valueOf(1), "document_number", new Money(5000));

        Assertions.assertEquals(Long.valueOf(1), account.getAccountId());
        Assertions.assertEquals("document_number", account.getDocumentNumber());
    }

    @Test
    public void test_creation_null_account_id() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(null, "document_number", new Money(5000));
        });
    }

    @Test
    public void test_creation_empty_document_number() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(Long.valueOf(1), "", new Money(5000));
        });
    }

    @Test
    public void test_creation_null_document_number() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(Long.valueOf(1), null, new Money(5000));
        });
    }

    @Test
    public void test_creation_null_credit_limit() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(Long.valueOf(1), "document_number", null);
        });
    }

    @Test
    public void test_creation_negative_credit_limit() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(Long.valueOf(1), "document_number", new Money(-100));
        });
    }

    @Test
    public void test_operate_still_positive() throws Exception {
        Account account = new Account(Long.valueOf(1), "document_number", new Money(100));

        account.operate(new Money(10));

        Assertions.assertEquals(new Money(110), account.getCreditLimit());
    }

    @Test
    public void test_operate_negative() throws Exception {
        Account account = new Account(Long.valueOf(1), "document_number", new Money(100));

        account.operate(new Money(-10));

        Assertions.assertEquals(new Money(90), account.getCreditLimit());
    }

    @Test
    public void test_operate_to_negative() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Account(Long.valueOf(1), "document_number", new Money(100)).operate(new Money(-101));
        });
    }
}
