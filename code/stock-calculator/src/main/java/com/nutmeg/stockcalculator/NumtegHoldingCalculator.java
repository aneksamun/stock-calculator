package com.nutmeg.stockcalculator;

import com.nutmeg.stockcalculator.domain.model.Holding;
import com.nutmeg.stockcalculator.domain.model.Holdings;
import com.nutmeg.stockcalculator.domain.model.Stock;
import com.nutmeg.stockcalculator.domain.model.Transaction;
import com.nutmeg.stockcalculator.domain.parser.TransactionFileParser;
import com.nutmeg.stockcalculator.domain.validation.ValidationErrors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class NumtegHoldingCalculator implements HoldingCalculator {

    private final TransactionFileParser parser;

    @Override
    public Map<String, List<Holding>> calculateHoldings(@NonNull File transactionFile,
                                                        @NonNull LocalDate date) {
        parser.addListener(this);

        try {
            val statement = new HashMap<String, List<Holding>>();
            val transactions = parser.parseTill(date, transactionFile);

            for (Transaction transaction : transactions) {
                statement.computeIfAbsent(transaction.getAccount(), createCustomerAccount());

                switch (transaction.getType()) {
                    case BOT:
                        statement.compute(transaction.getAccount(), decreaseCashBalance(transaction).andThen(buyStock(transaction)));
                        break;
                    case DEP:
                        statement.compute(transaction.getAccount(), increaseCashBalance(transaction));
                        break;
                    case DIV:
                        statement.compute(transaction.getAccount(), increaseCashBalance(transaction));
                        break;
                    case SLD:
                        statement.compute(transaction.getAccount(), increaseCashBalance(transaction).andThen(sellStock(transaction)));
                        break;
                    case WDR:
                        statement.compute(transaction.getAccount(), decreaseCashBalance(transaction));
                        break;
                }
            }

            return statement;

        } finally {
            parser.removeListener(this);
        }
    }

    @Override
    public void handleLineParseError(int line, ValidationErrors errors) {
        log.error("Error parsing line {}. Details: {}", line, errors.toString());
    }

    private static Function<String, List<Holding>> createCustomerAccount() {
        return account -> Holdings.create();
    }

    private static BiFunction<String, List<Holding>, List<Holding>> increaseCashBalance(Transaction transaction) {
        return (account, items) -> {
            val cash = ((Holdings) items).getCash();
            cash.add(transaction.calculateAmount());
            return items;
        };
    }

    private static BiFunction<String, List<Holding>, List<Holding>> decreaseCashBalance(Transaction transaction) {
        return (account, items) -> {
            val cash = ((Holdings) items).getCash();
            cash.subtract(transaction.calculateAmount());
            return items;
        };
    }

    private static Function<List<Holding>, List<Holding>> buyStock(Transaction transaction) {
        return holdings -> {
            holdings.add(new Stock(transaction.getAsset(), transaction.getUnits()));
            return holdings;
        };
    }

    private static Function<List<Holding>, List<Holding>> sellStock(Transaction transaction) {
        return items -> {
            val stock = ((Holdings) items).getStockFor(transaction.getAsset());
            stock.subtract(transaction.getUnits());
            if (stock.getHolding() == 0) {
                items.remove(stock);
            }
            return items;
        };
    }
}
