package org.example.java_sample_web_payment_app.application.usecases;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateAccountUsecaseTest {

    @Test
    public void test_execute() throws Exception {
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.exists(Mockito.any())).thenReturn(false);
        CreateAccountUsecase usecase = new CreateAccountUsecase(repository);

        usecase.execute("document_number");

        Mockito.verify(repository, Mockito.times(1))
                .exists(Mockito.argThat((AccountDTO dto) -> dto.documentNumber.contentEquals("document_number")));
        Mockito.verify(repository, Mockito.times(1))
                .add(Mockito.argThat((AccountDTO dto) -> dto.documentNumber.contentEquals("document_number")));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void test_execute_exists() throws Exception {
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.exists(Mockito.any())).thenReturn(true);
        CreateAccountUsecase usecase = new CreateAccountUsecase(repository);

        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {
            usecase.execute("document_number");
        });

        Mockito.verify(repository, Mockito.times(1))
                .exists(Mockito.argThat((AccountDTO dto) -> dto.documentNumber.contentEquals("document_number")));
        Mockito.verifyNoMoreInteractions(repository);
    }
}
