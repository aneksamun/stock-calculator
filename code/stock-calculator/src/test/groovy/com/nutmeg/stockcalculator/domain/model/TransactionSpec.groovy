package com.nutmeg.stockcalculator.domain.model

import spock.lang.Specification
import spock.lang.Unroll

import static com.nutmeg.stockcalculator.domain.model.TransactionType.DEP

class TransactionSpec extends Specification {

    @Unroll
    def 'test amount calculation: #amount'() {
        given: 'transaction'
        def transaction = Transaction
                .of(DEP)
                .size(units.doubleValue())
                .spent(price.doubleValue())
                .create()

        expect: 'to be correct'
        amount == transaction.calculateAmount()

        where:
        price  | units | amount
        2.0599 | 2.123 | 4.3732
        2.0599 | 2.111 | 4.3484
        2      | 2     | 4
    }
}
