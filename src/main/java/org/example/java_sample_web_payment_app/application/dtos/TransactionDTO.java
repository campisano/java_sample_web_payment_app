package org.example.java_sample_web_payment_app.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    public Long transactionId;
    public Long accountId;
    public Long operationTypeId;
    public BigDecimal amount;
    public LocalDateTime eventDate;
}
