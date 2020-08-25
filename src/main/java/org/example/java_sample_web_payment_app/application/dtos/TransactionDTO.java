package org.example.java_sample_web_payment_app.application.dtos;

import java.math.BigDecimal;

public class TransactionDTO {
    public Long transactionId;
    public Long accountId;
    public Long operationTypeId;
    public BigDecimal amount;
}
