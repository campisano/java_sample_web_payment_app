package org.example.java_sample_web_payment_app.application.usecases;

import java.math.BigDecimal;
import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.dtos.TransactionDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.OperationTypeIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateTransactionUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.application.ports.out.TransactionsRepositoryPort;
import org.example.java_sample_web_payment_app.domain.Account;
import org.example.java_sample_web_payment_app.domain.DomainValidationException;
import org.example.java_sample_web_payment_app.domain.Money;
import org.example.java_sample_web_payment_app.domain.Transaction;

public class CreateTransactionUsecase implements CreateTransactionUsecasePort {

    private TransactionsRepositoryPort transactionsRepository;
    private AccountsRepositoryPort accountsRepository;

    public CreateTransactionUsecase(AccountsRepositoryPort accountsRepository,
            TransactionsRepositoryPort transactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public void execute(Long accountId, Long operationTypeId, BigDecimal amount)
            throws DomainValidationException, AccountIdNotExistsException, OperationTypeIdNotExistsException {

        Optional<AccountDTO> account = accountsRepository.findByAccountId(accountId);

        if (account.isEmpty()) {
            throw new AccountIdNotExistsException(accountId);
        }

        Optional<Transaction.Type> type = transactionsRepository.findTypeByTypeId(operationTypeId);

        if (type.isEmpty()) {
            throw new OperationTypeIdNotExistsException(operationTypeId);
        }

        Long transactionId = transactionsRepository.generateUniqueTransactionId();
        new Transaction(transactionId, new Account(account.get().accountId, account.get().documentNumber), type.get(),
                new Money(amount));

        TransactionDTO dto = new TransactionDTO();
        dto.transactionId = transactionId;
        dto.accountId = accountId;
        dto.operationTypeId = operationTypeId;
        dto.amount = amount;

        transactionsRepository.add(dto);
    }
}
