package org.example.java_sample_web_payment_app.application.ports.out;

import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;

public interface AccountsRepositoryPort {

    boolean existsDocumentNumber(String documentNumber);

    Long generateUniqueAccountId();

    void add(AccountDTO account);

    Optional<AccountDTO> findByAccountId(Long accountId);

    void update(AccountDTO account);
}
