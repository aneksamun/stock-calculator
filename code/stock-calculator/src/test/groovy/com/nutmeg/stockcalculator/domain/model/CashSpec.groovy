package com.nutmeg.stockcalculator.domain.model

import spock.lang.Specification

class CashSpec extends Specification {

    def 'on create suppose to have CASH abbreviation and 0 assets'() {
        given: 'new cash asset'
        def cash = new Cash()

        expect: 'condition to be met'
        cash.asset == 'CASH'
        cash.holding == 0
    }
}
