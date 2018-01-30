package com.nutmeg.stockcalculator.infrastructure.property

import spock.lang.Specification

class ParserPropertiesSpec extends Specification {

    def 'test read properties'() {
        given: 'properties'
        def properties = ParserProperties.read()

        expect: 'date to be read properly'
        properties.dateFormat == 'yyyyMMdd'

        and: 'separator to be read properly'
        properties.fieldsSeparator == ','
    }
}
