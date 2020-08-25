package org.example.java_sample_web_payment_app.domain;

public class Account {
    private Long accountId;
    private String documentNumber;

    public Account(Long accountId, String documentNumber) throws DomainValidationException {
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

    private static void ensureCreable(Long accountId, String documentNumber) throws DomainValidationException {
        if (accountId == null) {
            throw new DomainValidationException("Account id [{0}] is invalid", accountId);
        }

        if (documentNumber == null || documentNumber.length() == 0) {
            throw new DomainValidationException("Document number [{0}] is invalid", documentNumber);
        }
    }
}
