package com.nutmeg.stockcalculator.domain.validation;

import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@RequiredArgsConstructor
public enum ValidationError {
    FIELD_MISSING("Field '%s' is missing"),
    BAD_UNITS("Error parsing units. Expected number got '%s'."),
    BAD_PRICE("Error parsing price. Expected number got '%s'."),
    ILLEGAL_PRICE("Illegal price detected. Should have value 1 for cash transactions."),
    INVALID_RECORD("Invalid columns count per record. Expected %d."),
    INVALID_DATE("Error parsing date '%s'. Expected format '%s'."),
    UNKNOWN_TXN("Cannot recognize transaction '%s'.");

    private final String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return format(errorMessage, args);
    }
}
