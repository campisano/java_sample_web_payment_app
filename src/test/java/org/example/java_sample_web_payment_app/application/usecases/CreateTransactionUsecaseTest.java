package org.example.java_sample_web_payment_app.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.dtos.TransactionDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;
import org.example.java_sample_web_payment_app.application.exceptions.OperationTypeIdNotExistsException;
import org.example.java_sample_web_payment_app.application.ports.out.AccountsRepositoryPort;
import org.example.java_sample_web_payment_app.application.ports.out.TimeRepositoryPort;
import org.example.java_sample_web_payment_app.application.ports.out.TransactionsRepositoryPort;
import org.example.java_sample_web_payment_app.domain.DomainValidationException;
import org.example.java_sample_web_payment_app.domain.Transaction;
import org.example.java_sample_web_payment_app.domain.Transaction.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateTransactionUsecaseTest {

    private AccountsRepositoryPort accountsRepo;
    private TimeRepositoryPort timeRepo;
    private TransactionsRepositoryPort transactionsRepo;

    @BeforeEach
    private void prepareNewMocks() {
        accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        timeRepo = Mockito.mock(TimeRepositoryPort.class);
        transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
    }

    @AfterEach
    private void noMoreMockInterations() {
        Mockito.verifyNoMoreInteractions(Mockito.ignoreStubs(accountsRepo, transactionsRepo, timeRepo));
    }

    @Test
    public void test_execute() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(5000);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long foundTypeId = Long.valueOf(4);
        Long generatedTransactionId = Long.valueOf(1);

        // arrange output
        {
            Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
            Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
            Mockito.when(transactionsRepo.findTypeIdByType(Mockito.any())).thenReturn(Optional.of(foundTypeId));
            Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);
            Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);
        }

        // act
        {
            CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));
        }

        // assert inputs
        {
            Mockito.verify(accountsRepo, Mockito.times(1))
                    .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(Long.valueOf(1))));
            Mockito.verify(transactionsRepo, Mockito.times(1))
                    .findTypeByTypeId(Mockito.argThat((Long operationId) -> operationId.equals(Long.valueOf(4))));
            Mockito.verify(transactionsRepo, Mockito.times(1))
                    .findTypeIdByType(Mockito.argThat((Type operation) -> operation.equals(Type.PAYMENT)));
            Mockito.verify(transactionsRepo, Mockito.times(1)).generateUniqueTransactionId();
            Mockito.verify(timeRepo, Mockito.times(1)).getCurrentTime();
        }

        // assert changes
        {
            Mockito.verify(transactionsRepo, Mockito.times(1))
                    .add(Mockito.argThat((TransactionDTO dto) -> dto.accountId.equals(Long.valueOf(1))
                            && dto.operationTypeId.equals(Long.valueOf(4))
                            && dto.amount.equals(BigDecimal.valueOf(12345, 2))),
                            Mockito.argThat((AccountDTO dto) -> dto.accountId.equals(foundAccount.accountId)
                                    && dto.documentNumber.contentEquals(foundAccount.documentNumber) && dto.creditLimit
                                            .equals(foundAccount.creditLimit.subtract(BigDecimal.valueOf(12345, 2)))));
        }
    }

    @Test
    public void test_execute_account_not_exists() throws Exception {
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.empty());

        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);
        Assertions.assertThrows(AccountIdNotExistsException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));
        });

        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_execute_operation_not_exists() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(5000);
            }
        };

        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.empty());

        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);
        Assertions.assertThrows(OperationTypeIdNotExistsException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));
        });

        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_execute_operation_check_limit() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(100);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long foundTypeId = Long.valueOf(4);
        Long generatedTransactionId = Long.valueOf(1);

        // arrange output
        {
            Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
            Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
            Mockito.when(transactionsRepo.findTypeIdByType(Mockito.any())).thenReturn(Optional.of(foundTypeId));
            Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);
            Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);
        }

        // act
        {
            CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(30));
        }

        // assert changes
        {
            Mockito.verify(transactionsRepo, Mockito.times(1)).add(
                    Mockito.argThat((TransactionDTO dto) -> dto.accountId.equals(Long.valueOf(1))
                            && dto.operationTypeId.equals(Long.valueOf(4))
                            && dto.amount.equals(BigDecimal.valueOf(30))),
                    Mockito.argThat((AccountDTO account) -> account.creditLimit.equals(BigDecimal.valueOf(70))));
        }
    }

    @Test
    public void test_execute_operation_check_limit_exceded() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(70);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long generatedTransactionId = Long.valueOf(1);

        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
        Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);
        Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);

        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);
        Assertions.assertThrows(DomainValidationException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(80));
        });

        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any(), Mockito.any());
    }
}
