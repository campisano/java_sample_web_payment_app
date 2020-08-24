package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void test_creation_cash() {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.CASH, new Money(-100));

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.CASH, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getAmount());
    }

    @Test
    public void test_creation_installment() {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.INSTALLMENT, new Money(-10));

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.INSTALLMENT, transaction.getType());
        Assertions.assertEquals(new Money(-10), transaction.getAmount());
    }

    @Test
    public void test_creation_installment_withdrawal() {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.WITHDRAWAL, new Money(-100));

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getAmount());
    }

    @Test
    public void test_creation_installment_payment() {
        Transaction transaction = new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                Transaction.Type.PAYMENT, new Money(100));

        Assertions.assertEquals(Long.valueOf(1), transaction.getTransactionId());
        Assertions.assertEquals("document_number", transaction.getAccount().getDocumentNumber());
        Assertions.assertEquals(Transaction.Type.PAYMENT, transaction.getType());
        Assertions.assertEquals(new Money(100), transaction.getAmount());
    }

    @Test
    public void test_creation_null_transaction_id() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null, new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    new Money(-1));
        });
    }

    @Test
    public void test_creation_null_account() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), null, Transaction.Type.CASH, new Money(-1));
        });
    }

    @Test
    public void test_creation_null_type() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), null, new Money(-1));
        });
    }

    @Test
    public void test_creation_null_amount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    null);
        });
    }

    @Test
    public void test_creation_cash_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.CASH,
                    new Money(1));
        });
    }

    @Test
    public void test_creation_installment_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                    Transaction.Type.INSTALLMENT, new Money(1));
        });
    }

    @Test
    public void test_creation_withdrawal_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"),
                    Transaction.Type.WITHDRAWAL, new Money(1));
        });
    }

    @Test
    public void test_creation_payment_negative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(Long.valueOf(1), new Account(Long.valueOf(1), "document_number"), Transaction.Type.PAYMENT,
                    new Money(-1));
        });
    }
}
