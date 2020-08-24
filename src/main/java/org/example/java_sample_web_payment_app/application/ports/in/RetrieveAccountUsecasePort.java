package org.example.java_sample_web_payment_app.application.ports.in;

import org.example.java_sample_web_payment_app.application.dtos.AccountDTO;
import org.example.java_sample_web_payment_app.application.exceptions.AccountIdNotExistsException;

public interface RetrieveAccountUsecasePort {

    AccountDTO execute(Long accountId) throws AccountIdNotExistsException;
}
