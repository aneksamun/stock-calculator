package com.nutmeg.stockcalculator.domain.model;

import lombok.ToString;

@ToString(callSuper = true)
public final class Stock extends Holding {

    public Stock(String name, double units) {
        super(name, units);
    }
}
