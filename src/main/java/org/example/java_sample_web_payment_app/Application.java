package org.example.java_sample_web_payment_app;

import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.application.usecases.CreateAccountUsecase;
import org.example.java_sample_web_payment_app.application.usecases.RetrieveAccountUsecase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Service
class SpringCreateAccountUsecase extends CreateAccountUsecase {
    public SpringCreateAccountUsecase(AccountsRepositoryPort accountsRepository) {
        super(accountsRepository);
    }
}

@Service
class SpringRetrieveAccountUsecase extends RetrieveAccountUsecase {
    public SpringRetrieveAccountUsecase(AccountsRepositoryPort accountsRepository) {
        super(accountsRepository);
    }
}