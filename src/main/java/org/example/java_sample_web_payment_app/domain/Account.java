package org.example.java_sample_web_payment_app.domain;

public class Account {
    private Long accountId;
    private String documentNumber;
    private Money creditLimit;

    public Account(Long accountId, String documentNumber, Money creditLimit) throws DomainValidationException {
        ensureCreable(accountId, documentNumber);
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.creditLimit = creditLimit;
    }

    // TODO is default constructor a good idea?
    public Account(Long accountId, String documentNumber) throws DomainValidationException {
        ensureCreable(accountId, documentNumber);
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.creditLimit = new Money(5000);
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    private static void ensureCreable(Long accountId, String documentNumber) throws DomainValidationException {
        if (accountId == null) {
            throw new DomainValidationException("Account id [{0}] is invalid", accountId);
        }

        if (documentNumber == null || documentNumber.length() == 0) {
            throw new DomainValidationException("Document number [{0}] is invalid", documentNumber);
        }
    }

    public void subtract(Money amount) throws DomainValidationException {
        if (creditLimit.getValue().compareTo(amount.getValue()) < 0) {
            throw new DomainValidationException("Subtraction of [{0}] cannot result in a negative amount",
                    amount.getValue());
        }

        creditLimit = new Money(creditLimit.getValue().subtract(amount.getValue()));
    }
}
