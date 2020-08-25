package org.example.java_sample_web_payment_app.application.usecases;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.CreateAccountUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.domain.Account;

public class CreateAccountUsecase implements CreateAccountUsecasePort {

    private AccountsRepositoryPort accountsRepository;

    public CreateAccountUsecase(AccountsRepositoryPort accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void execute(String documentNumber) throws AccountAlreadyExistsException {

        if (accountsRepository.existsDocumentNumber(documentNumber)) {
            throw new AccountAlreadyExistsException(documentNumber);
        }

        Long accountId = accountsRepository.generateUniqueAccountId();
        new Account(accountId, documentNumber);

        AccountDTO dto = new AccountDTO();
        dto.accountId = accountId;
        dto.documentNumber = documentNumber;

        accountsRepository.add(dto);
    }
}
