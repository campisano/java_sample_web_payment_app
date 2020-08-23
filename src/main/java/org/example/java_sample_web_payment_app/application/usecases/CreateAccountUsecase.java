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
        Account account = new Account(documentNumber);
        AccountDTO dto = new AccountDTO() {{ documentNumber = account.getDocumentNumber(); }};

        if (accountsRepository.exists(dto)) {
            throw new AccountAlreadyExistsException(dto.documentNumber);
        }

        accountsRepository.add(dto);
    }
}
