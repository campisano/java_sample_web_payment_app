package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Transaction {

    public enum Type {
        CASH, INSTALLMENT, WITHDRAWAL, PAYMENT
    };

    private String number;
    private Type type;

    public Transaction(String number, Type type) {
        ensureCreable(number, type);
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    private static void ensureCreable(String number, Type type) {
        if (number == null || number.length() == 0) {
            throw new IllegalArgumentException(MessageFormat.format("Number {0} is invalid", number));
        }
        if (type == null) {
            throw new IllegalArgumentException("Type is null");
        }
    }
}
