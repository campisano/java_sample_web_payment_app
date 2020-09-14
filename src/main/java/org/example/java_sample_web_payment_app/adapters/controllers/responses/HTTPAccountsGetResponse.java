package org.example.java_sample_web_payment_app.adapters.controllers.responses;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HTTPAccountsGetResponse {
    public Long accountId;
    public String documentNumber;
    public BigDecimal creditLimit;

    @Override
    public String toString() {
        return getClass().getName() + " [accountId=" + accountId + ", documentNumber=" +
               documentNumber
               + ", creditLimit=" + creditLimit + "]";
    }
}
