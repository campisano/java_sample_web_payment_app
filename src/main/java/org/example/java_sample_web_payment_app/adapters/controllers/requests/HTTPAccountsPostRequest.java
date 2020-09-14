package org.example.java_sample_web_payment_app.adapters.controllers.requests;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HTTPAccountsPostRequest {
    public String documentNumber;
    public BigDecimal creditLimit;

    @Override
    public String toString() {
        return getClass().getName() + " [documentNumber=" + documentNumber +
               ", creditLimit=" + creditLimit + "]";
    }
}
