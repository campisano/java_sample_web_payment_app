package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Transaction {

    public enum Type {
        CASH, INSTALLMENT, WITHDRAWAL, PAYMENT
    };

    private Long transactionId;
    private Account account;
    private Type type;
    private Money value;

    public Transaction(Long transactionId, Account account, Type type, Money value) {
        ensureCreable(transactionId, account, type, value);
        this.transactionId = transactionId;
        this.account = account;
        this.type = type;
        this.value = value;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public Type getType() {
        return type;
    }

    public Money getValue() {
        return value;
    }

    private static void ensureCreable(Long transactionId, Account account, Type type, Money value) {
        if (transactionId == null) {
            throw new IllegalArgumentException(MessageFormat.format("Transaction id [{0}] is invalid", transactionId));
        }
        if (account == null) {
            throw new IllegalArgumentException("Account 'null' is invalid");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type 'null' is invalid");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value 'null' is invalid");
        }

        if (type.equals(Type.PAYMENT)) {
            if (value.isNegative()) {
                throw new IllegalArgumentException("Payment transaction must have a positive value");
            }
        } else {
            if (value.isPositive()) {
                throw new IllegalArgumentException("Only Payment transaction can have a positive value");
            }
        }
    }
}
