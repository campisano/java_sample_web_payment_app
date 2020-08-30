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
@Table(name = "accounts")
@SequenceGenerator(name = AccountModel.ID_SEQ_GEN, sequenceName = AccountModel.ID_SEQ_NAME, initialValue = 1, allocationSize = 1)
public class AccountModel {
    public final static String ID_SEQ_NAME = "accounts_account_id_seq";
    protected final static String ID_SEQ_GEN = ID_SEQ_NAME + "_gen";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ_GEN)
    @Column(columnDefinition = "bigserial", nullable = false, unique = true, updatable = true)
    private Long accountId;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String documentNumber;

    @Column(columnDefinition = "numeric", nullable = false)
    private BigDecimal creditLimit;

    AccountModel() {
    }

    public AccountModel(Long accountId, String documentNumber, BigDecimal creditLimit) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.creditLimit = creditLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }
}
