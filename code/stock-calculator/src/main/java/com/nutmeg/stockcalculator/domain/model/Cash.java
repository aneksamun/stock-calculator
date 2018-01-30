package com.nutmeg.stockcalculator.domain.model;

import lombok.ToString;

@ToString(callSuper = true)
public final class Cash extends Holding {

    public static final String ABBREVIATION = "CASH";
    public static final double VALID_PRICE_UNIT = 1.0;

    public Cash() {
        super(ABBREVIATION, 0);
    }
}
