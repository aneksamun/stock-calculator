package com.nutmeg.stockcalculator.domain.model.format;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class DecimalPlaceFormatter {

    private static final int PLACES = 4;

    public static double round(double decimal) {
        BigDecimal bigDecimal = new BigDecimal(decimal);
        return bigDecimal.setScale(PLACES, HALF_UP).doubleValue();
    }
}
