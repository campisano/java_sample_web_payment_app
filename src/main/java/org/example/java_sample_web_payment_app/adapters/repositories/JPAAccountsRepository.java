package org.example.java_sample_web_payment_app.adapters.repositories;

import org.example.java_sample_web_payment_app.adapters.repositories.models.AccountModel;
import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class JPAAccountsRepository implements AccountsRepositoryPort {

    private SpringJPAAccountsRepository repository;

    public JPAAccountsRepository(SpringJPAAccountsRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean exists(AccountDTO account) {
        return repository.existsByDocumentNumber(account.documentNumber);
    }

    @Override
    public void add(AccountDTO account) {
        AccountModel model = new AccountModel(account.documentNumber);
        repository.save(model);
    }
}

@Repository
interface SpringJPAAccountsRepository extends org.springframework.data.repository.Repository<AccountModel, Long> {

    boolean existsByDocumentNumber(String documentNumber);

    public AccountModel save(AccountModel model);
}
