package com.nutmeg.stockcalculator.domain.parser;

import com.nutmeg.stockcalculator.domain.validation.ValidationErrors;

@FunctionalInterface
public interface ParserNotificationListener {

    void handleLineParseError(int line, ValidationErrors errors);
}
