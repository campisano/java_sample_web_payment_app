package org.example.java_sample_web_payment_app.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void test_creation_cash() {
        Transaction transaction = new Transaction("number", Transaction.Type.CASH, new Money(-100));

        Assertions.assertEquals("number", transaction.getNumber());
        Assertions.assertEquals(Transaction.Type.CASH, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getValue());
    }

    @Test
    public void test_creation_installment() {
        Transaction transaction = new Transaction("number", Transaction.Type.INSTALLMENT, new Money(-10));

        Assertions.assertEquals("number", transaction.getNumber());
        Assertions.assertEquals(Transaction.Type.INSTALLMENT, transaction.getType());
        Assertions.assertEquals(new Money(-10), transaction.getValue());
    }

    @Test
    public void test_creation_installment_withdrawal() {
        Transaction transaction = new Transaction("number", Transaction.Type.WITHDRAWAL, new Money(-100));

        Assertions.assertEquals("number", transaction.getNumber());
        Assertions.assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType());
        Assertions.assertEquals(new Money(-100), transaction.getValue());
    }

    @Test
    public void test_creation_installment_payment() {
        Transaction transaction = new Transaction("number", Transaction.Type.PAYMENT, new Money(100));

        Assertions.assertEquals("number", transaction.getNumber());
        Assertions.assertEquals(Transaction.Type.PAYMENT, transaction.getType());
        Assertions.assertEquals(new Money(100), transaction.getValue());
    }

    @Test
    public void test_creation_null_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null, Transaction.Type.CASH, new Money(-1));
        });
    }

    @Test
    public void test_creation_empty_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("", Transaction.Type.CASH, new Money(-1));
        });
    }

    @Test
    public void test_creation_null_type() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", null, new Money(-1));
        });
    }

    @Test
    public void test_creation_null_value() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", Transaction.Type.CASH, null);
        });
    }

    @Test
    public void test_creation_cash_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", Transaction.Type.CASH, new Money(1));
        });
    }

    @Test
    public void test_creation_installment_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", Transaction.Type.INSTALLMENT, new Money(1));
        });
    }

    @Test
    public void test_creation_withdrawal_positive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", Transaction.Type.WITHDRAWAL, new Money(1));
        });
    }

    @Test
    public void test_creation_payment_negative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("number", Transaction.Type.PAYMENT, new Money(-1));
        });
    }
}
