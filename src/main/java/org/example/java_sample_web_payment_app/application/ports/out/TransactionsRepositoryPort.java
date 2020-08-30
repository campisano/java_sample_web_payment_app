package org.example.java_sample_web_payment_app.application.ports.out;

import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.dtos.TransactionDTO;
import org.example.java_sample_web_payment_app.domain.Transaction;

public interface TransactionsRepositoryPort {

    Long generateUniqueTransactionId();

    void add(TransactionDTO transaction, AccountDTO account);

    Optional<Transaction.Type> findTypeByTypeId(Long operationTypeId);

    Optional<Long> findTypeIdByType(Transaction.Type type);
}
