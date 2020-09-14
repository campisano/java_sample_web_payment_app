package org.example.java_sample_web_payment_app.application.exceptions;

import java.text.MessageFormat;

public class OperationTypeIdNotExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public OperationTypeIdNotExistsException(Long operationTypeId) {
        super(MessageFormat.format("OperationType with id [{0}] not exists",
                                   operationTypeId));
    }
}
