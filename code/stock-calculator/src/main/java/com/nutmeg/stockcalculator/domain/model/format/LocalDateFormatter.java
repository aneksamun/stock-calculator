package com.nutmeg.stockcalculator.domain.model.format;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class LocalDateFormatter {

    public static String toString(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String toString(LocalDate date) {
        return date.format(getDefault());
    }

    public static LocalDate fromString(String value, String format) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate fromString(String value) {
        return LocalDate.parse(value, getDefault());
    }

    private static DateTimeFormatter getDefault() {
        return DateTimeFormatter.ofPattern("yyyyMMdd");
    }
}
