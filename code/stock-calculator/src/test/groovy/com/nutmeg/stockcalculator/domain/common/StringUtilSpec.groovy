package com.nutmeg.stockcalculator.domain.common

import spock.lang.Specification

class StringUtilSpec extends Specification {

    def 'determine string to be blank'() {
        expect: 'fail'
        StringUtil.isBlank(string)

        where:
        string | _
        null   | _
        ''     | _
        ' '    | _
    }

    def 'determine string to be not blank'() {
        expect: 'success'
        !StringUtil.isBlank( 'not blank')
    }
}
