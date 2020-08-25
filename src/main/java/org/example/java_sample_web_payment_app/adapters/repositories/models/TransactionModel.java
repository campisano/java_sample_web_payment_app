package org.example.java_sample_web_payment_app.adapters.repositories.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
@SequenceGenerator(name = TransactionModel.ID_SEQ_GEN, sequenceName = TransactionModel.ID_SEQ_NAME, initialValue = 1, allocationSize = 1)
public class TransactionModel {
    public final static String ID_SEQ_NAME = "transactions_transaction_id_seq";
    protected final static String ID_SEQ_GEN = ID_SEQ_NAME + "_gen";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ_GEN)
    @Column(nullable = false, unique = true, updatable = true, columnDefinition = "serial")
    private Long transactionId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long operationTypeId;

    @Column(nullable = false)
    private BigDecimal amount;

    TransactionModel() {
    }

    public TransactionModel(Long transactionId, Long accountId, Long operationTypeId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
