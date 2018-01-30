package com.nutmeg.stockcalculator.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static com.nutmeg.stockcalculator.domain.model.format.DecimalPlaceFormatter.round;
import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@NoArgsConstructor(access = PRIVATE)
public final class Transaction {

    private String account;

    private LocalDate date;

    private TransactionType type;

    private String asset;

    private double units;

    private double price;

    public double calculateAmount() {
        return round(price * units);
    }

    public static TransactionBuilder of(TransactionType type) {
        return new TransactionBuilder(type);
    }

    public static class TransactionBuilder {

        private final Transaction transaction = new Transaction();

        private TransactionBuilder(TransactionType type) {
            transaction.type = type;
        }

        public TransactionBuilder by(String account) {
            transaction.account = account;
            return this;
        }

        public TransactionBuilder on(LocalDate date) {
            transaction.date = date;
            return this;
        }

        public TransactionBuilder forAsset(String asset) {
            transaction.asset = asset;
            return this;
        }

        public TransactionBuilder spent(double price) {
            transaction.price = round(price);
            return this;
        }

        public TransactionBuilder size(double units) {
            transaction.units = round(units);
            return this;
        }

        public Transaction create() {
            return transaction;
        }
    }
}
