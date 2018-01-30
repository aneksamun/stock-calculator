package com.nutmeg.stockcalculator.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum TransactionType {
    BOT("Stock purchase"),
    SLD("Stock sale"),
    DIV("Dividend payment"),
    DEP("Deposit"),
    WDR("Withdrawal");

    @Getter
    private final String description;
}
