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
        Long generatedAccountId = Long.valueOf(1);
        String inputDocumentNumber = "document_number";
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.existsDocumentNumber(Mockito.any())).thenReturn(false);
        Mockito.when(repository.generateUniqueAccountId()).thenReturn(generatedAccountId);
        CreateAccountUsecase usecase = new CreateAccountUsecase(repository);

        usecase.execute(inputDocumentNumber);

        Mockito.verify(repository, Mockito.times(1)).existsDocumentNumber(
                Mockito.argThat((String documentNumber) -> documentNumber.contentEquals(inputDocumentNumber)));
        Mockito.verify(repository, Mockito.times(1)).generateUniqueAccountId();
        Mockito.verify(repository, Mockito.times(1))
                .add(Mockito.argThat((AccountDTO dto) -> dto.accountId.equals(generatedAccountId)
                        && dto.documentNumber.contentEquals(inputDocumentNumber)));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void test_execute_exists() throws Exception {
        String inputDocumentNumber = "document_number";
        AccountsRepositoryPort repository = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(repository.existsDocumentNumber(Mockito.any())).thenReturn(true);
        Mockito.when(repository.generateUniqueAccountId()).thenReturn(Long.valueOf(1));
        CreateAccountUsecase usecase = new CreateAccountUsecase(repository);

        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {
            usecase.execute(inputDocumentNumber);
        });

        Mockito.verify(repository, Mockito.times(1)).existsDocumentNumber(
                Mockito.argThat((String documentNumber) -> documentNumber.contentEquals(inputDocumentNumber)));
        Mockito.verifyNoMoreInteractions(repository);
    }
}
