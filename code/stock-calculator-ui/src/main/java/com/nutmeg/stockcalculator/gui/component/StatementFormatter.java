package com.nutmeg.stockcalculator.gui.component;

import com.nutmeg.stockcalculator.domain.model.Holding;
import com.nutmeg.stockcalculator.domain.model.format.LocalDateFormatter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class StatementFormatter {

    public static String format(Map<String, List<Holding>> statement, LocalDate statementDate) {
        val builder = new StringBuilder();
        builder.append(String.format("Current holdings on %s\n", LocalDateFormatter.toString(statementDate)));
        statement.forEach((account, holdings) -> {
            builder.append(String.format("%s:", account)).append("\n");
            for (Holding holding : holdings) {
                builder.append(String.format("%10s\t%-7s", holding.getAsset(), holding.getHolding())).append("\n");
            }
        });
        return builder.toString();
    }
}
