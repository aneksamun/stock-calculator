package com.nutmeg.stockcalculator.infrastructure.parser

import com.nutmeg.stockcalculator.domain.parser.ParserNotificationListener
import com.nutmeg.stockcalculator.domain.parser.TransactionFileParser
import com.nutmeg.stockcalculator.helper.Resources
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate


class CsvFileParserSpec extends Specification {

    @Subject
    private TransactionFileParser parser = new CsvFileParser()

    def 'discard transactions after certain date'() {
        given: 'transaction file with valid records'
        def file = Resources.loadValidTransactionsFile()

        and: 'date to filter'
        def date = LocalDate.of(2017, 1, 1)

        when: 'file is parsed'
        def transactions = parser.parseTill date, file

        then: 'only transaction on/before filter date are returned'
        transactions.any()
        transactions.size() == 2
    }

    def 'parse file'() {
        given: 'transaction file with valid records'
        def file = Resources.loadValidTransactionsFile()

        when: 'file is parsed'
        def transactions = parser.parseTill LocalDate.now(), file

        then: 'the lists of transactions are returned'
        transactions.any()
        transactions.size() == 8
    }

    def 'discard invalid transactions'() {
        given: 'transaction file with invalid records'
        def file = Resources.loadInvalidTransactionsFile()

        when: 'file is parsed'
        def transactions = parser.parseTill LocalDate.now(), file

        then: 'invalid transactions are discarded'
        transactions.isEmpty()
    }

    def 'receives parse errors'() {
        given: 'file with invalid transactions'
        def file = Resources.loadInvalidTransactionsFile()

        and: 'errors listener'
        def received = false
        ParserNotificationListener listener = { line, errors ->
            received = true
            assert errors.any()
            assert line > 0
        }
        parser.addListener listener

        when: 'file is parsed'
        parser.parseTill LocalDate.now(), file

        then: 'an errors are published'
        received
        parser.removeListener listener
    }
}
