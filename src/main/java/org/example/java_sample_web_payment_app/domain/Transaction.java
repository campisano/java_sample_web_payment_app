package org.example.java_sample_web_payment_app.domain;

import java.time.LocalDateTime;

public class Transaction {

    public enum Type {
        CASH, INSTALLMENT, WITHDRAWAL, PAYMENT
    };

    private Long transactionId;
    private Account account;
    private Type type;
    private Money amount;
    private LocalDateTime eventDate;

    public Transaction(Long transactionId, Account account, Type type, Money amount,
                       LocalDateTime eventDate)
    throws DomainValidationException {
        ensureCreable(transactionId, account, type, amount, eventDate);
        this.transactionId = transactionId;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.eventDate = eventDate;
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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    private static void ensureCreable(Long transactionId, Account account,
                                      Type type, Money amount,
                                      LocalDateTime eventDate) throws DomainValidationException {
        if(transactionId == null) {
            throw new DomainValidationException("Transaction id [{0}] is invalid",
                                                transactionId);
        }
        if(account == null) {
            throw new DomainValidationException("Account [{0}] is invalid", account);
        }
        if(type == null) {
            throw new DomainValidationException("Type [{0}] is invalid", type);
        }
        if(amount == null || amount.isZero()) {
            throw new DomainValidationException("Amount [{0}]' is invalid", amount);
        }
        if(eventDate == null) {
            throw new DomainValidationException("EventDate [{0}] is invalid", eventDate);
        }

        if(type.equals(Type.PAYMENT)) {
            if(amount.isNegative()) {
                throw new DomainValidationException("Payment transaction must have a positive amount");
            }
        } else {
            if(amount.isPositive()) {
                throw new DomainValidationException("Only Payment transaction can have a positive amount");
            }
        }
    }
}
