package org.example.java_sample_web_payment_app.adapters.repositories;

import java.util.Optional;

import org.example.java_sample_web_payment_app.adapters.repositories.models.TransactionModel;
import org.example.java_sample_web_payment_app.application.dtos.TransactionDTO;
import org.example.java_sample_web_payment_app.application.ports.out.TransactionsRepositoryPort;
import org.example.java_sample_web_payment_app.domain.Transaction;
import org.example.java_sample_web_payment_app.domain.Transaction.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class JPATransactionsRepository implements TransactionsRepositoryPort {

    private SpringJPATransactionsRepository repository;

    public JPATransactionsRepository(SpringJPATransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long generateUniqueTransactionId() {
        return repository.getNextTransactionId();
    }

    @Override
    public void add(TransactionDTO transaction) {
        TransactionModel model = new TransactionModel(transaction.transactionId, transaction.accountId,
                transaction.operationTypeId, transaction.amount, transaction.eventDate);
        repository.save(model);
    }

    @Override
    public Optional<Type> findTypeByTypeId(Long operationTypeId) {
        switch (operationTypeId.intValue()) {
        case 1:
            return Optional.of(Transaction.Type.CASH);
        case 2:
            return Optional.of(Transaction.Type.INSTALLMENT);
        case 3:
            return Optional.of(Transaction.Type.WITHDRAWAL);
        case 4:
            return Optional.of(Transaction.Type.PAYMENT);
        default:
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findTypeIdByType(Type type) {
        switch (type) {
        case CASH:
            return Optional.of(Long.valueOf(1));
        case INSTALLMENT:
            return Optional.of(Long.valueOf(2));
        case WITHDRAWAL:
            return Optional.of(Long.valueOf(3));
        case PAYMENT:
            return Optional.of(Long.valueOf(4));
        default:
            return Optional.empty();
        }
    }
}

@Repository
interface SpringJPATransactionsRepository
        extends org.springframework.data.repository.Repository<TransactionModel, Long> {

    @Query(value = "SELECT nextval('" + TransactionModel.ID_SEQ_NAME + "')", nativeQuery = true)
    Long getNextTransactionId();

    public TransactionModel save(TransactionModel model);
}
