package com.nutmeg.stockcalculator.domain.parser;

import com.nutmeg.stockcalculator.domain.model.Transaction;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;

public interface TransactionFileParser {

    Collection<Transaction> parseTill(LocalDate date, File file);

    boolean addListener(ParserNotificationListener listener);
    boolean removeListener(ParserNotificationListener listener);
}
