package org.example.java_sample_web_payment_app.application.usecases;

import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.in.RetrieveAccountUsecasePort;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;

public class RetrieveAccountUsecase implements RetrieveAccountUsecasePort {

    private AccountsRepositoryPort accountsRepository;

    public RetrieveAccountUsecase(AccountsRepositoryPort accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public AccountDTO execute(Long accountId) throws AccountIdNotExistsException {

        Optional<AccountDTO> account = accountsRepository.findByAccountId(accountId);

        if(account.isEmpty()) {
            throw new AccountIdNotExistsException(accountId);
        }

        return account.get();
    }
}
