package com.nutmeg.stockcalculator.infrastructure.parser;

import com.nutmeg.stockcalculator.domain.model.Transaction;
import com.nutmeg.stockcalculator.domain.parser.ParserNotificationListener;
import com.nutmeg.stockcalculator.domain.parser.TransactionFileParser;
import com.nutmeg.stockcalculator.domain.validation.ValidationErrors;
import com.nutmeg.stockcalculator.infrastructure.property.ParserProperties;
import lombok.SneakyThrows;
import lombok.val;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public final class CsvFileParser implements TransactionFileParser {

    private final Set<ParserNotificationListener> listeners = new HashSet<>();

    private final ParserProperties properties = ParserProperties.read();

    @Override
    @SneakyThrows
    public Collection<Transaction> parseTill(LocalDate date, File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String record;
                val transactions = new ArrayList<Transaction>();

                for (int line = 1; (record = reader.readLine()) != null; line++) {
                    Fields fields = Fields.of(record)
                            .separatedBy(properties.getFieldsSeparator())
                            .dateFormatIs(properties.getDateFormat())
                            .fetch();

                    if (fields.date.isPresent() && fields.date.get().isAfter(date))
                        continue;

                    if (fields.errors.any()) {
                        publish(line, fields.errors);
                        continue;
                    }

                    Transaction transaction = Transaction
                            .of(fields.transactionType.get())
                            .by(fields.account)
                            .on(fields.date.get())
                            .forAsset(fields.asset)
                            .spent(fields.price.get())
                            .size(fields.units.get())
                            .create();

                    transactions.add(transaction);
                }

                return transactions;
            }
        }
    }

    @Override
    public boolean addListener(ParserNotificationListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeListener(ParserNotificationListener listener) {
        return listeners.remove(listener);
    }

    private void publish(int lineNumber, ValidationErrors errors) {
        listeners.forEach(listener -> listener.handleLineParseError(lineNumber, errors));
    }
}
