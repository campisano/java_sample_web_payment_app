package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Transaction {

    public enum Type {
        CASH, INSTALLMENT, WITHDRAWAL, PAYMENT
    };

    private Account account;
    private String number;
    private Type type;
    private Money value;

    public Transaction(Account account, String number, Type type, Money value) {
        ensureCreable(account, number, type, value);
        this.account = account;
        this.number = number;
        this.type = type;
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public String getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    public Money getValue() {
        return value;
    }

    private static void ensureCreable(Account account, String number, Type type, Money value) {
        if (account == null) {
            throw new IllegalArgumentException("Account 'null' is invalid");
        }
        if (number == null || number.length() == 0) {
            throw new IllegalArgumentException(MessageFormat.format("Number '{0}' is invalid", number));
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
