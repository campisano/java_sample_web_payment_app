package org.example.java_sample_web_payment_app.application.ports.in;

import org.example.java_sample_web_payment_app.application.exceptions.AccountAlreadyExistsException;

public interface CreateAccountUsecasePort {

    void execute(String documentNumber) throws AccountAlreadyExistsException;
}