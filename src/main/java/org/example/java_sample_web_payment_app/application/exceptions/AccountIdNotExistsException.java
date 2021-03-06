package org.example.java_sample_web_payment_app.application.exceptions;

import java.text.MessageFormat;

public class AccountIdNotExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountIdNotExistsException(Long accountId) {
        super(MessageFormat.format("Account with id [{0}] not exists", accountId));
    }
}
