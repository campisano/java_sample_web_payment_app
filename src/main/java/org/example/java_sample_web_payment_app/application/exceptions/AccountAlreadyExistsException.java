package org.example.java_sample_web_payment_app.application.exceptions;

import java.text.MessageFormat;

public class AccountAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistsException(String documentNumber) {
        super(MessageFormat.format("Account with document number [{0}] already exists", documentNumber));
    }
}
