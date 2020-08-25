package org.example.java_sample_web_payment_app.application.ports.out;

import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.TransactionDTO;
import org.example.java_sample_web_payment_app.domain.Transaction;

public interface TransactionsRepositoryPort {

    Optional<Transaction.Type> findTypeByTypeId(Long operationTypeId);

    Long generateUniqueTransactionId();

    void add(TransactionDTO dto);
}
