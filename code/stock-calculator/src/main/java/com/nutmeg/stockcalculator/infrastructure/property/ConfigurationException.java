package com.nutmeg.stockcalculator.infrastructure.property;

import com.nutmeg.stockcalculator.domain.common.ServiceError;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(Throwable cause, ServiceError error, Object... args) {
        this(error.getErrorMessage(args), cause);
    }

    public ConfigurationException(Throwable cause, ServiceError error) {
        this(error.getErrorMessage(), cause);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(ServiceError error, Object... args) {
        this(error.getErrorMessage(args));
    }

    public ConfigurationException(ServiceError error) {
        this(error.getErrorMessage());
    }

    public ConfigurationException(String message) {
        super(message);
    }
}
