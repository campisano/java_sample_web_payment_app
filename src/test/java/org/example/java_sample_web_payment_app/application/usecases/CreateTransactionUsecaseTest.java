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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateTransactionUsecaseTest {

    @Test
    public void test_execute() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(5000, 0);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long foundTypeId = Long.valueOf(4);
        Long generatedTransactionId = Long.valueOf(1);

        AccountsRepositoryPort accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
        TransactionsRepositoryPort transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
        Mockito.when(transactionsRepo.findTypeIdByType(Mockito.any())).thenReturn(Optional.of(foundTypeId));
        Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);
        TimeRepositoryPort timeRepo = Mockito.mock(TimeRepositoryPort.class);
        Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);
        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);

        usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));

        Mockito.verify(accountsRepo, Mockito.times(1))
                .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(Long.valueOf(1))));
        Mockito.verify(transactionsRepo, Mockito.times(1))
                .findTypeByTypeId(Mockito.argThat((Long operationId) -> operationId.equals(Long.valueOf(4))));
        Mockito.verify(transactionsRepo, Mockito.times(1))
                .findTypeIdByType(Mockito.argThat((Type operation) -> operation.equals(Type.PAYMENT)));
        Mockito.verify(transactionsRepo, Mockito.times(1)).generateUniqueTransactionId();

        Mockito.verify(transactionsRepo, Mockito.times(1))
                .add(Mockito.argThat((TransactionDTO dto) -> dto.accountId.equals(Long.valueOf(1))
                        && dto.operationTypeId.equals(Long.valueOf(4))
                        && dto.amount.equals(BigDecimal.valueOf(12345, 2))));

        Mockito.verify(accountsRepo, Mockito.times(1))
                .update(Mockito.argThat((AccountDTO dto) -> dto.accountId.equals(foundAccount.accountId)
                        && dto.documentNumber.contentEquals(foundAccount.documentNumber)
                        && dto.creditLimit.equals(foundAccount.creditLimit.subtract(BigDecimal.valueOf(12345, 2)))));
        Mockito.verifyNoMoreInteractions(accountsRepo, transactionsRepo);
    }

    @Test
    public void test_execute_account_not_exists() throws Exception {
        AccountsRepositoryPort accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.empty());
        TransactionsRepositoryPort transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
        TimeRepositoryPort timeRepo = Mockito.mock(TimeRepositoryPort.class);
        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);

        Assertions.assertThrows(AccountIdNotExistsException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));
        });

        Mockito.verify(accountsRepo, Mockito.times(1))
                .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(Long.valueOf(1))));
        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any());
        Mockito.verifyNoMoreInteractions(accountsRepo, transactionsRepo, timeRepo); // TODO AVOID use of
                                                                                    // verifyNoMoreInteractions to not
                                                                                    // copy unuseful validation
    }

    @Test
    public void test_execute_operation_not_exists() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(5000, 0);
            }
        };
        AccountsRepositoryPort accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));
        TransactionsRepositoryPort transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.empty());
        TimeRepositoryPort timeRepo = Mockito.mock(TimeRepositoryPort.class);
        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);

        Assertions.assertThrows(OperationTypeIdNotExistsException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(12345, 2));
        });

        Mockito.verify(accountsRepo, Mockito.times(1))
                .findByAccountId(Mockito.argThat((Long accountId) -> accountId.equals(Long.valueOf(1))));
        Mockito.verify(transactionsRepo, Mockito.times(1))
                .findTypeByTypeId(Mockito.argThat((Long operationId) -> operationId.equals(Long.valueOf(4))));
        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any());
        Mockito.verifyNoMoreInteractions(accountsRepo, transactionsRepo, timeRepo);
    }

    // TODO remove comment
    /*
     * Para uma conta com limite 100, ao fazer um saque no valor de 30 o limite deve
     * passar a ser de 70.
     */
    @Test
    public void test_execute_operation_check_limit() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(100, 0);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long foundTypeId = Long.valueOf(4);
        Long generatedTransactionId = Long.valueOf(1);

        AccountsRepositoryPort accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));

        TransactionsRepositoryPort transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
        Mockito.when(transactionsRepo.findTypeIdByType(Mockito.any())).thenReturn(Optional.of(foundTypeId));
        Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);

        TimeRepositoryPort timeRepo = Mockito.mock(TimeRepositoryPort.class);
        Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);

        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);

        usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(30, 0));

        Mockito.verify(accountsRepo, Mockito.times(1))
                .update(Mockito.argThat((AccountDTO account) -> account.creditLimit.equals(BigDecimal.valueOf(70, 0))));
    }

    // TODO remove comment
    /*
     * Ao tentar fazer uma nova transação de saque no valor de 80 devemos recusá-la,
     * pois o limite disponível agora é de apenas 70. Assim que é emitida uma
     * transação de pagamento no valor de 20, o limite disponível aumenta para 90.
     */
    @Test
    public void test_execute_operation_check_limit_exceded() throws Exception {
        AccountDTO foundAccount = new AccountDTO() {
            {
                accountId = Long.valueOf(1);
                documentNumber = "document_number";
                creditLimit = BigDecimal.valueOf(70, 0);
            }
        };
        Transaction.Type foundType = Type.PAYMENT;
        Long generatedTransactionId = Long.valueOf(1);

        AccountsRepositoryPort accountsRepo = Mockito.mock(AccountsRepositoryPort.class);
        Mockito.when(accountsRepo.findByAccountId(Mockito.any())).thenReturn(Optional.of(foundAccount));

        TransactionsRepositoryPort transactionsRepo = Mockito.mock(TransactionsRepositoryPort.class);
        Mockito.when(transactionsRepo.findTypeByTypeId(Mockito.any())).thenReturn(Optional.of(foundType));
        Mockito.when(transactionsRepo.generateUniqueTransactionId()).thenReturn(generatedTransactionId);

        TimeRepositoryPort timeRepo = Mockito.mock(TimeRepositoryPort.class);
        Mockito.when(timeRepo.getCurrentTime()).thenReturn(LocalDateTime.MIN);

        CreateTransactionUsecase usecase = new CreateTransactionUsecase(accountsRepo, transactionsRepo, timeRepo);

        Assertions.assertThrows(DomainValidationException.class, () -> {
            usecase.execute(Long.valueOf(1), Long.valueOf(4), BigDecimal.valueOf(80, 0));
        });

        Mockito.verify(transactionsRepo, Mockito.times(0)).add(Mockito.any());
        Mockito.verify(accountsRepo, Mockito.times(0)).update(Mockito.any());
    }
}
