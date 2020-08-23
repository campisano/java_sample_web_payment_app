package org.example.java_sample_web_payment_app.adapters.repositories.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountModel {
    public final static String ID_SEQ_NAME = "accounts_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ_NAME)
    private long id;

    @Column(nullable = false, unique = true)
    private String documentNumber;

    AccountModel() {
    }

    public AccountModel(Long id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
