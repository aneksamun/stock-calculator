package com.nutmeg.stockcalculator;

import com.nutmeg.stockcalculator.domain.model.Holding;
import com.nutmeg.stockcalculator.domain.parser.ParserNotificationListener;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HoldingCalculator extends ParserNotificationListener {

    Map<String, List<Holding>> calculateHoldings(File transactionFile, LocalDate date);
}
