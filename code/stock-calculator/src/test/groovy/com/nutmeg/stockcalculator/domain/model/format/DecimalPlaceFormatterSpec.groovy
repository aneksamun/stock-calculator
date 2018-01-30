package com.nutmeg.stockcalculator.domain.model.format

import spock.lang.Specification
import spock.lang.Unroll

class DecimalPlaceFormatterSpec extends Specification {

    @Unroll
    def 'test decimal rounding: #input'() {
        expect: 'to be not longer than 4 decimal places after decimal separator'
        DecimalPlaceFormatter.round(input.doubleValue()) == output.doubleValue()

        where:
        input   | output
        3.11111 | 3.1111
        1.2     | 1.2
        1.12589 | 1.1259
        1.12582 | 1.1258
    }
}
