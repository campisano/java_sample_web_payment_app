package org.example.java_sample_web_payment_app.application.usecases;

import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RetrieveAccountUsecaseTest {

    @Test
    public void test_execute() throws Exception {
        Long inputAccountId = Long.valueOf(1);
        AccountDTO foundAccount = new AccountDTO() {{ accountId = Long.valueOf(1); documentNumber = "document_number"; }};
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
        RetrieveAccountUsecase usecase = new RetrieveAccountUsecase(repository);

        AccountDTO account = usecase.execute(inputAccountId);

        Assertions.assertEquals(inputAccountId, account.accountId);

        Mockito.verify(repository, Mockito.times(1))
                .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(inputAccountId)));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void test_execute_not_exists() throws Exception {
        Long inputAccountId = Long.valueOf(1);
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.findByAccountId(Mockito.any())).thenReturn(Optional.empty());
        RetrieveAccountUsecase usecase = new RetrieveAccountUsecase(repository);

        Assertions.assertThrows(AccountIdNotExistsException.class, () -> {
            usecase.execute(inputAccountId);
        });

        Mockito.verify(repository, Mockito.times(1))
                .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(inputAccountId)));
        Mockito.verifyNoMoreInteractions(repository);
    }
}
