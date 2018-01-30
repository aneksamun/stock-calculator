package com.nutmeg.stockcalculator.domain.model

import spock.lang.Specification
import spock.lang.Unroll

class HoldingsSpec extends Specification {

    @Unroll
    def 'test addition: #holding.asset'() {
        when: 'increasing holding units'
        holding.add(100)

        then: 'size expect to be increased'
        amount.doubleValue() == holding.getHolding()

        where:
        holding                      | amount
        new Cash()                   | 100
        new Stock('VUSA', 2_000_000) | 2_000_100
    }

    @Unroll
    def 'test subtraction: #holding.asset'() {
        when: 'deducting holding units'
        holding.subtract(100)

        then: 'size expect to be deducted'
        amount.doubleValue() == holding.getHolding()

        where:
        holding                      | amount
        new Cash()                   | -100
        new Stock('VUSA', 2_000_000) | 1_999_900
    }
}
