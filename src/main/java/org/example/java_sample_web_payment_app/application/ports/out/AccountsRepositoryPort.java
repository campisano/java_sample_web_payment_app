package org.example.java_sample_web_payment_app.application.ports.out;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;

public interface AccountsRepositoryPort {

    boolean exists(AccountDTO account);

    void add(AccountDTO account);
}
