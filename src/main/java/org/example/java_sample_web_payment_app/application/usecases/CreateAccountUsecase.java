package org.example.java_sample_web_payment_app.application.usecases;

import java.math.BigDecimal;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.domain.Account;
import org.example.java_sample_web_payment_app.domain.DomainValidationException;
import org.example.java_sample_web_payment_app.domain.Money;

public class CreateAccountUsecase implements CreateAccountUsecasePort {

    private AccountsRepositoryPort accountsRepository;

    public CreateAccountUsecase(AccountsRepositoryPort accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void execute(String documentNumber, BigDecimal creditLimit)
            throws DomainValidationException, AccountAlreadyExistsException {

        if (accountsRepository.existsDocumentNumber(documentNumber)) {
            throw new AccountAlreadyExistsException(documentNumber);
        }

        Long accountId = accountsRepository.generateUniqueAccountId();
        Account account = new Account(accountId, documentNumber, new Money(creditLimit));

        AccountDTO dto = new AccountDTO();
        dto.accountId = account.getAccountId();
        dto.documentNumber = account.getDocumentNumber();
        dto.creditLimit = account.getCreditLimit().getValue();

        accountsRepository.add(dto);
    }
}
