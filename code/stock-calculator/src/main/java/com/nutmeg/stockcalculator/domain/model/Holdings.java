package com.nutmeg.stockcalculator.domain.model;

import com.nutmeg.stockcalculator.domain.exception.StatementException;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.ArrayList;

import static com.nutmeg.stockcalculator.domain.common.ServiceError.STOCK_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Holdings extends ArrayList<Holding> {

    public Holding getCash() {
        return this.get(0);
    }

    public Holding getStockFor(String asset) {
        return stream().filter(item -> item.getAsset().equalsIgnoreCase(asset))
                       .findFirst()
                       .orElseThrow(() -> new StatementException(STOCK_NOT_FOUND, asset));
    }

    public static Holdings create() {
        val holdings = new Holdings();
        holdings.add(new Cash());
        return holdings;
    }
}
