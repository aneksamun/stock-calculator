package com.nutmeg.stockcalculator.infrastructure.parser;

import com.nutmeg.stockcalculator.domain.model.Cash;
import com.nutmeg.stockcalculator.domain.model.TransactionType;
import com.nutmeg.stockcalculator.domain.model.format.LocalDateFormatter;
import com.nutmeg.stockcalculator.domain.validation.ValidationErrors;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

import static com.nutmeg.stockcalculator.domain.common.StringUtil.isBlank;
import static com.nutmeg.stockcalculator.domain.validation.ValidationError.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class Fields {

    final ValidationErrors errors = new ValidationErrors();

    String account;

    Optional<LocalDate> date;

    Optional<TransactionType> transactionType;

    Optional<Double> units;

    Optional<Double> price;

    String asset;

    static FieldsBuilder of(String record) {
        return new FieldsBuilder(record);
    }

    static class FieldsBuilder {

        private final String record;
        private String separator = ",";
        private String dateFormat = "yyyyMMdd";

        private static final int ACCOUNT_INDEX = 0;
        private static final int DATE_INDEX = 1;
        private static final int TXN_TYPE_INDEX = 2;
        private static final int UNITS_INDEX = 3;
        private static final int PRICE_INDEX = 4;
        private static final int ASSET_INDEX = 5;
        private static final int FIELDS_COUNT = 6;

        FieldsBuilder(String record) {
            this.record = record;
        }

        FieldsBuilder separatedBy(String separator) {
            this.separator = separator;
            return this;
        }

        FieldsBuilder dateFormatIs(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        Fields fetch() {
            Fields fields = new Fields();
            String[] rows = record.split(separator);

            if (rows.length != FIELDS_COUNT) {
                fields.errors.add(INVALID_RECORD, FIELDS_COUNT);
                return fields;
            }

            fields.account = rows[ACCOUNT_INDEX].trim();
            fields.date = tryParse(() -> LocalDateFormatter.fromString(rows[DATE_INDEX].trim(), dateFormat));
            fields.transactionType = tryParse(() -> TransactionType.valueOf(rows[TXN_TYPE_INDEX].trim()));
            fields.units = tryParse(() -> Double.valueOf(rows[UNITS_INDEX].trim()));
            fields.price = tryParse(() -> Double.valueOf(rows[PRICE_INDEX].trim()));
            fields.asset = rows[ASSET_INDEX].trim();

            if (isBlank(fields.account)) {
                fields.errors.add(FIELD_MISSING, "account");
            }

            if (!fields.date.isPresent()) {
                fields.errors.add(INVALID_DATE, rows[DATE_INDEX], dateFormat);
            }

            if (!fields.transactionType.isPresent()) {
                fields.errors.add(UNKNOWN_TXN, rows[TXN_TYPE_INDEX]);
            }

            if (!fields.units.isPresent()) {
                fields.errors.add(BAD_UNITS, rows[UNITS_INDEX]);
            }

            if (!fields.price.isPresent()) {
                fields.errors.add(BAD_PRICE, rows[PRICE_INDEX]);
            }

            if (isBlank(fields.asset)) {
                fields.errors.add(FIELD_MISSING, "asset");

            } else if (fields.asset.equalsIgnoreCase(Cash.ABBREVIATION) &&
                       fields.price.map(price -> price != Cash.VALID_PRICE_UNIT)
                                   .orElse(Boolean.TRUE)) {
                fields.errors.add(ILLEGAL_PRICE);
            }

            return fields;
        }

        private static <T> Optional<T> tryParse(Supplier<T> supplier) {
            try {
                return Optional.of(supplier.get());
            }
            catch (Exception exception) {
                return Optional.empty();
            }
        }
    }
}
