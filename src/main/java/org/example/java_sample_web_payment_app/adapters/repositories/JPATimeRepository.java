package org.example.java_sample_web_payment_app.adapters.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.example.java_sample_web_payment_app.application.ports.out.TimeRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class JPATimeRepository implements TimeRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    public JPATimeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public LocalDateTime getCurrentTime() {
        Query query = entityManager.createNativeQuery("select current_timestamp");
        Timestamp timestamp = (Timestamp) query.getSingleResult();
        return timestamp.toLocalDateTime();
    }
}