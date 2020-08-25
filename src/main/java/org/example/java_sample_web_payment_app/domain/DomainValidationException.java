package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class DomainValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DomainValidationException(String message, Object... arguments) {
        super(MessageFormat.format(message, arguments));
    }
}
