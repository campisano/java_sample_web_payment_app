package org.example.java_sample_web_payment_app.domain;

import java.text.MessageFormat;

public class Account {
    private Long accountId;
    private String documentNumber;

    public Account(Long accountId, String documentNumber) {
        ensureCreable(accountId, documentNumber);
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    private static void ensureCreable(Long accountId, String documentNumber) {
        if (accountId == null) {
            throw new IllegalArgumentException(MessageFormat.format("Account id [{0}] is invalid", accountId));
        }

        if (documentNumber == null || documentNumber.length() == 0) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Document number [{0}] is invalid", documentNumber));
        }
    }
}
