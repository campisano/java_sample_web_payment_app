package org.example.java_sample_web_payment_app;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.application.usecases.CreateAccountUsecase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Repository
class SpringAccountsRepository implements AccountsRepositoryPort {

    @Override
    public boolean exists(AccountDTO account) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void add(AccountDTO account) {
        // TODO Auto-generated method stub
    }
}

@Service
class SpringCreateAccountUsecase extends CreateAccountUsecase {

    public SpringCreateAccountUsecase(AccountsRepositoryPort accountsRepository) {
        super(accountsRepository);
    }
}
