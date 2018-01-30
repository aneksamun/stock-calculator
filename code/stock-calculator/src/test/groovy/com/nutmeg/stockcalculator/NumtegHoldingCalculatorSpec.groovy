package com.nutmeg.stockcalculator

import com.nutmeg.stockcalculator.domain.model.Transaction
import com.nutmeg.stockcalculator.domain.parser.TransactionFileParser
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static com.nutmeg.stockcalculator.domain.model.TransactionType.*

class NumtegHoldingCalculatorSpec extends Specification {

    def parser = Mock(TransactionFileParser)

    @Subject
    def calculator = new NumtegHoldingCalculator(parser)

    def 'test calculations'() {
        given: 'a file'
        def file = new File('')

        and: 'date'
        def now = LocalDate.now()

        when: 'calculations are performed'
        def statement = calculator.calculateHoldings file, now

        then: 'has expected amount'
        statement['test'][0].asset == 'CASH'
        statement['test'][0].holding == 4990.8781.doubleValue()

        and: 'stock holding'
        statement['test'][1].asset == 'STOCK'
        statement['test'][1].holding == 1.0.doubleValue()

        parser.parseTill(now, file) >> [

                Transaction.of(DEP)
                        .by('test')
                        .on(now.minusDays(4))
                        .forAsset('CASH')
                        .spent(1)
                        .size(10000)
                        .create(),

                Transaction.of(WDR)
                        .by('test')
                        .on(now.minusDays(3))
                        .forAsset('CASH')
                        .spent(1)
                        .size(5000)
                        .create(),

                Transaction.of(BOT)
                        .by('test')
                        .on(now.minusDays(2))
                        .forAsset('STOCK')
                        .spent(20.3398.doubleValue())
                        .size(3)
                        .create(),

                Transaction.of(DIV)
                        .by('test')
                        .on(now.minusDays(1))
                        .forAsset('STOCK')
                        .spent(1)
                        .size(0.3575.doubleValue())
                        .create(),

                Transaction.of(SLD)
                        .by('test')
                        .on(now)
                        .forAsset('STOCK')
                        .spent(25.77.doubleValue())
                        .size(2)
                        .create()
        ]
    }

    def 'customer can be overdrawn'() {
        when: 'calculations are performed'
        def statement = calculator.calculateHoldings new File(''), LocalDate.now()

        then: 'zero cash balance reported'
        statement['test'][0].asset == 'CASH'
        statement['test'][0].holding == -100

        parser.parseTill(_, _) >> [

                Transaction.of(BOT)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('STOCK')
                        .spent(1)
                        .size(100)
                        .create(),
        ]
    }

    def 'always reports cash balance'() {
        when: 'calculations are performed'
        def statement = calculator.calculateHoldings new File(''), LocalDate.now()

        then: 'zero cash balance reported'
        statement['test'][0].asset == 'CASH'
        statement['test'][0].holding == 0

        parser.parseTill(_, _) >> [

                Transaction.of(DEP)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('CASH')
                        .spent(1)
                        .size(10000)
                        .create(),

                Transaction.of(WDR)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('CASH')
                        .spent(1)
                        .size(10000)
                        .create(),
        ]
    }

    def 'zero holding not included'() {
        when: 'calculations are performed'
        def statement = calculator.calculateHoldings new File(''), LocalDate.now()

        then: 'only cash balance reported'
        statement['test'].size() == 1

        statement['test'][0].asset == 'CASH'
        statement['test'][0].holding == 100



        parser.parseTill(_, _) >> [

                Transaction.of(DEP)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('CASH')
                        .spent(1)
                        .size(100)
                        .create(),

                Transaction.of(BOT)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('STOCK')
                        .spent(1)
                        .size(100)
                        .create(),

                Transaction.of(SLD)
                        .by('test')
                        .on(LocalDate.now())
                        .forAsset('STOCK')
                        .spent(1)
                        .size(100)
                        .create()
        ]
    }
}
