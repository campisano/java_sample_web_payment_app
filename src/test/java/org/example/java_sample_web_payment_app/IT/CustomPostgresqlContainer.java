package org.example.java_sample_web_payment_app.IT;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresqlContainer extends PostgreSQLContainer<CustomPostgresqlContainer> {

    private static CustomPostgresqlContainer container;

    private CustomPostgresqlContainer() {
        super("postgres:12.4-alpine");
    }

    public static CustomPostgresqlContainer getInstance() {
        if (container == null) {
            container = new CustomPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TESTCONTAINER_PG_URL", container.getJdbcUrl());
        System.setProperty("TESTCONTAINER_PG_USERNAME", container.getUsername());
        System.setProperty("TESTCONTAINER_PG_PASSWORD", container.getPassword());
    }
}