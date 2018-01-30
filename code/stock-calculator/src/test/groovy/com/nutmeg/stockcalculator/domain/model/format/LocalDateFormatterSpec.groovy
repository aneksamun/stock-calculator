package com.nutmeg.stockcalculator.domain.model.format

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class LocalDateFormatterSpec extends Specification {

    @Unroll
    def 'test date convert to string: #input'() {
        expect: 'to have correct date format'
        LocalDateFormatter.toString(input) == output

        where:
        input                      | output
        LocalDate.of(2017, 2, 1)   | "20170201"
        LocalDate.of(2018, 01, 28) | "20180128"
    }

    @Unroll
    def 'test string convert to date: #input'() {
        expect: 'to have correct date'
        LocalDateFormatter.fromString(input) == output

        where:
        input      | output
        "20170201" | LocalDate.of(2017, 2, 1)
        "20180128" | LocalDate.of(2018, 01, 28)
    }
}
