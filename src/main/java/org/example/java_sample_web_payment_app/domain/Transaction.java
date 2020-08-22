package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Transaction {
    private String number;

    public Transaction(String number) {
        ensureCreable(number);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private static void ensureCreable(String number) {
        if (number == null || number.length() == 0) {
            throw new IllegalArgumentException(MessageFormat.format("Number {0} is invalid", number));
        }
    }
}
