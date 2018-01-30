package com.nutmeg.stockcalculator.helper

class Resources {

    private static final VALID_TRANSACTIONS_FILE_NAME = 'transactions/valid.csv'
    private static final INVALID_TRANSACTIONS_FILE_NAME = 'transactions/invalid.csv'

    static def loadValidTransactionsFile() {
        loadResource VALID_TRANSACTIONS_FILE_NAME
    }

    static def loadInvalidTransactionsFile() {
        loadResource INVALID_TRANSACTIONS_FILE_NAME
    }

    static def loadResource(String fileName) {
        URL url = ClassLoader.getSystemResource fileName
        assert url : "File '${fileName}' not found"
        return new File(url.toURI())
    }
}
