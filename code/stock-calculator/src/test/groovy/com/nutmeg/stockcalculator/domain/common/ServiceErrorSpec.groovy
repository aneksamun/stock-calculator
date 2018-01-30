package com.nutmeg.stockcalculator.domain.common

import spock.lang.Specification

import static com.nutmeg.stockcalculator.domain.common.ServiceError.PROPERTIES_READ_ERROR

class ServiceErrorSpec extends Specification {

    def 'test error message formatting'() {
        given: 'an argument'
        def arg = 'testing'

        expect: 'argument to be present in message'
        PROPERTIES_READ_ERROR.getErrorMessage(arg) =~ arg
    }
}
