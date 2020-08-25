package org.example.java_sample_web_payment_app.application.ports.in;

import java.math.BigDecimal;

import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.OperationTypeIdNotExistsException;

public interface CreateTransactionUsecasePort {

    void execute(Long accountId, Long operationTypeId, BigDecimal amount)
            throws AccountIdNotExistsException, OperationTypeIdNotExistsException;
}
