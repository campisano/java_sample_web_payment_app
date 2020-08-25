package org.example.java_sample_web_payment_app.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void test_creation_cash() throws Exception {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.CASH, new Money(-100), LocalDateTime.MIN);

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.CASH, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getAmount());
    }

    @Test
    public void test_creation_installment() throws Exception {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.INSTALLMENT, new Money(-10), LocalDateTime.MIN);

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.INSTALLMENT, transaction.getType());
        Assertions.assertEquals(new Money(-10), transaction.getAmount());
    }

    @Test
    public void test_creation_installment_withdrawal() throws Exception {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.WITHDRAWAL, new Money(-100), LocalDateTime.MIN);

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getAmount());
    }

    @Test
    public void test_creation_installment_payment() throws Exception {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.PAYMENT, new Money(100), LocalDateTime.MIN);

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.PAYMENT, transaction.getType());
        Assertions.assertEquals(new Money(100), transaction.getAmount());
    }

    @Test
    public void test_creation_null_transaction_id() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(null, new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH, new Money(-1),
                    LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_null_account() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), null, Transaction.Type.CASH, new Money(-1), LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_null_type() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), null, new Money(-1),
                    LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_null_amount() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    null, LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_null_eventdate() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    new Money(-1), null);
        });
    }

    @Test
    public void test_creation_cash_positive() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    new Money(1), LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_installment_positive() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                    Transaction.Type.INSTALLMENT, new Money(1), LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_withdrawal_positive() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                    Transaction.Type.WITHDRAWAL, new Money(1), LocalDateTime.MIN);
        });
    }

    @Test
    public void test_creation_payment_negative() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.PAYMENT,
                    new Money(-1), LocalDateTime.MIN);
        });
    }
}
