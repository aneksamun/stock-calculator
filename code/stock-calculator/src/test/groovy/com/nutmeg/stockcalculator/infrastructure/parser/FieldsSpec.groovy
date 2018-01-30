package com.nutmeg.stockcalculator.infrastructure.parser

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static com.nutmeg.stockcalculator.domain.model.TransactionType.DEP
import static com.nutmeg.stockcalculator.domain.validation.ValidationError.*

class FieldsSpec extends Specification {

    def 'fail to fetch invalid size record'() {
        given: 'record with 3 columns'
        def record = 'only, three, fields'

        when: 'record is parsed'
        def fields = Fields.of(record)
                .separatedBy(',')
                .fetch()

        then: 'an error returned'
        fields.errors.every { it == INVALID_RECORD.getErrorMessage(6) }
    }

    def 'fail to fetch invalid record'() {
        given: 'record with invalid values'
        def record = "${account}, ${date}, ${transactionType}, ${units}, ${price}, ${asset}"

        when: 'record is parsed'
        def fields = Fields.of(record).fetch()

        then: 'an errors are returned'
        fields.errors.any {
            it == FIELD_MISSING.getErrorMessage('account') ||
                    it == INVALID_DATE.getErrorMessage(date, 'yyyyMMdd') ||
                    it == UNKNOWN_TXN.getErrorMessage(transactionType) ||
                    it == BAD_PRICE.getErrorMessage(price) ||
                    it == BAD_UNITS.getErrorMessage(units) ||
                    it == FIELD_MISSING.getErrorMessage('asset')
        }

        where:
        account | date         | transactionType | units  | price  | asset
        ''      | '01.01.2020' | 'UNKNOWN'       | 'none' | 'none' | ''
    }

    def 'fail to fetch record with with invalid asset price'() {
        given: 'CASH transaction with invalid price'
        def record = "NEAA0000, 20170101, DEP, 100, 2, CASH"

        when: 'transaction is parsed'
        def fields = Fields.of(record).fetch()

        then: 'an error returned'
        fields.errors.every { it == ILLEGAL_PRICE.getErrorMessage() }
    }

    def 'successfully fetches record'() {
        given: 'valid transaction record'
        def record = "NEAA0000, 20170101, DEP, 100, 1, CASH"

        when: 'record is parsed'
        def fields = Fields.of(record).fetch()

        then: 'no errors detected'
        !fields.errors.any()

        and: 'account has expected value'
        fields.account == 'NEAA0000'

        and: 'date has expected value'
        fields.date.get() == LocalDate.of(2017, 1, 1)

        and: 'transaction type has expected value'
        fields.transactionType.get() == DEP

        and: 'units has expected value'
        fields.units.get() == 100.0.doubleValue()

        and: 'price has expected value'
        fields.price.get() == 1.0.doubleValue()

        and: 'asset has expected value'
        fields.asset == 'CASH'
    }
}
