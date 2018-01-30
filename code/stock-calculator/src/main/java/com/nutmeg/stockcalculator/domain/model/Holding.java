package com.nutmeg.stockcalculator.domain.model;

import com.nutmeg.stockcalculator.domain.model.format.DecimalPlaceFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
public abstract class Holding implements Serializable {

    @Getter
    private String asset;

    @Getter
    @Setter
    private double holding;

    public void add(double units) {
        holding = DecimalPlaceFormatter.round(holding + units);
    }

    public void subtract(double units) {
        holding = DecimalPlaceFormatter.round(holding - units);
    }

    @Override
    public String toString() {
        return getAsset() + ":\t" + getHolding();
    }
}
