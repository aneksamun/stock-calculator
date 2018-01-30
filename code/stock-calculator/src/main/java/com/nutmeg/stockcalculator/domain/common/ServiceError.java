package com.nutmeg.stockcalculator.domain.common;

import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public enum ServiceError {
    PROPERTIES_READ_ERROR("Error reading properties: %s"),
    STOCK_NOT_FOUND("Stock with name '%s' not found");

    private String errorMessage;

    public String getErrorMessage(Object... args) {
        return format(errorMessage, args);
    }
}
