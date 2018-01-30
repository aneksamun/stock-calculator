package com.nutmeg.stockcalculator.integration

import com.nutmeg.stockcalculator.NumtegHoldingCalculator
import com.nutmeg.stockcalculator.helper.Resources
import com.nutmeg.stockcalculator.infrastructure.parser.CsvFileParser
import spock.lang.Specification

import java.time.LocalDate

class CalculationSpec extends Specification {

    def calculator = new NumtegHoldingCalculator(new CsvFileParser())

    def 'should calculate 17.6849 for NEAA0000 and 10000 for NEAB0001'() {
        given: 'transactions file'
        def file = Resources.loadValidTransactionsFile()

        when: 'calculations are performed'
        def statement = calculator.calculateHoldings file, LocalDate.of(2017, 2, 1)

        then: 'NEAA0000 has cash 17.6849'
        statement['NEAA0000'][0].asset == 'CASH'
        statement['NEAA0000'][0].holding == 17.6849.doubleValue()

        and: 'VUKE holding of size 10'
        statement['NEAA0000'][1].asset == 'VUKE'
        statement['NEAA0000'][1].holding == 20.doubleValue()

        and: 'VUSE holding of size 10'
        statement['NEAA0000'][2].asset == 'VUSA'
        statement['NEAA0000'][2].holding == 10.doubleValue()

        and: 'GILS holding of size 10.5120'
        statement['NEAA0000'][3].asset == 'GILS'
        statement['NEAA0000'][3].holding == 10.5120.doubleValue()

        and: 'NEAB0001 has cash 10000'
        statement['NEAB0001'][0].asset == 'CASH'
        statement['NEAB0001'][0].holding == 10000.doubleValue()
    }

    def 'should ignore invalid transactions'() {
        given: 'invalid transactions file'
        def file = Resources.loadInvalidTransactionsFile()

        when: 'calculations are performed'
        def statement = calculator.calculateHoldings file, LocalDate.of(2017, 2, 1)

        then: 'no statements available'
        !statement.any()
    }
}
