package com.nutmeg.stockcalculator.domain.exception;

import com.nutmeg.stockcalculator.domain.common.ServiceError;

public class StatementException extends RuntimeException {

    public StatementException(ServiceError error, Object... args) {
        this(error.getErrorMessage(args));
    }

    public StatementException(String message) {
        super(message);
    }
}
