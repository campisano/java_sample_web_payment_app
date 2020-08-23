package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Account {
    private String documentNumber;

    public Account(String documentNumber) {
        ensureCreable(documentNumber);
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    private static void ensureCreable(String documentNumber) {
        if (documentNumber == null || documentNumber.length() == 0) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Document number [{0}] is invalid", documentNumber));
        }
    }
}
