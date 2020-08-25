package org.example.java_sample_web_payment_app.domain;

public class Transaction {

    public enum Type {
        CASH, INSTALLMENT, WITHDRAWAL, PAYMENT
    };

    private Long transactionId;
    private Account account;
    private Type type;
    private Money amount;

    public Transaction(Long transactionId, Account account, Type type, Money amount) throws DomainValidationException {
        ensureCreable(transactionId, account, type, amount);
        this.transactionId = transactionId;
        this.account = account;
        this.type = type;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public Type getType() {
        return type;
    }

    public Money getAmount() {
        return amount;
    }

    private static void ensureCreable(Long transactionId, Account account, Type type, Money amount)
            throws DomainValidationException {
        if (transactionId == null) {
            throw new DomainValidationException("Transaction id [{0}] is invalid", transactionId);
        }
        if (account == null) {
            throw new DomainValidationException("Account 'null' is invalid");
        }
        if (type == null) {
            throw new DomainValidationException("Type 'null' is invalid");
        }
        if (amount == null) {
            throw new DomainValidationException("Amount 'null' is invalid");
        }

        if (type.equals(Type.PAYMENT)) {
            if (amount.isNegative()) {
                throw new DomainValidationException("Payment transaction must have a positive amount");
            }
        } else {
            if (amount.isPositive()) {
                throw new DomainValidationException("Only Payment transaction can have a positive amount");
            }
        }
    }
}
