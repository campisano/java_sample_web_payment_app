package org.example.java_sample_web_payment_app.adapters.controllers.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HTTPAccountsGetResponse {
    public Long accountId;
    public String documentNumber;

    @Override
    public String toString() {
        return getClass().getName() + " [account_id=" + accountId + ", document_number=" + documentNumber + "]";
    }
}
