package org.example.java_sample_web_payment_app.adapters.repositories;

import java.util.Optional;

import org.example.java_sample_web_payment_app.adapters.repositories.models.AccountModel;
import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class JPAAccountsRepository implements AccountsRepositoryPort {

    private SpringJPAAccountsRepository repository;

    public JPAAccountsRepository(SpringJPAAccountsRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsDocumentNumber(String documentNumber) {
        return repository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public Long generateUniqueAccountId() {
        return repository.getNextAccountId();
    }

    @Override
    public void add(AccountDTO account) {
        AccountModel model = new AccountModel(account.accountId, account.documentNumber,
                                              account.creditLimit);
        repository.save(model);
    }

    @Override
    public Optional<AccountDTO> findByAccountId(Long accountId) {
        Optional<AccountModel> account = repository.findByAccountId(accountId);

        if(account.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new AccountDTO() {
            {
                accountId = account.get().getId();
                documentNumber = account.get().getDocumentNumber();
                creditLimit = account.get().getCreditLimit();
            }
        });
    }

    @Override
    public void update(AccountDTO accountDto) {
        Optional<AccountModel> model = repository.findByAccountId(accountDto.accountId);
        model.get().setDocumentNumber(accountDto.documentNumber);
        model.get().setCreditLimit(accountDto.creditLimit);
        repository.save(model.get());
    }
}

@Repository
interface SpringJPAAccountsRepository extends
    org.springframework.data.repository.Repository<AccountModel, Long> {

    @Query(value = "SELECT nextval('" + AccountModel.ID_SEQ_NAME + "')",
           nativeQuery = true)
    Long getNextAccountId();

    boolean existsByDocumentNumber(String documentNumber);

    AccountModel save(AccountModel model);

    Optional<AccountModel> findByAccountId(Long id);
}
