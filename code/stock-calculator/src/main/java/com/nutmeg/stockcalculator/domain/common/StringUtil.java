package com.nutmeg.stockcalculator.domain.common;

import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class StringUtil {

    public static boolean isBlank(String string) {
        return isNull(string) || string.trim().isEmpty();
    }
}
