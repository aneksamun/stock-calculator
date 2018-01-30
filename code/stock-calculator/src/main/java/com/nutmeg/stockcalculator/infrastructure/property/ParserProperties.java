package com.nutmeg.stockcalculator.infrastructure.property;

import lombok.Getter;
import lombok.val;

import java.io.InputStream;
import java.util.Properties;

import static com.nutmeg.stockcalculator.domain.common.ServiceError.PROPERTIES_READ_ERROR;

public final class ParserProperties {

    @Getter
    private final String fieldsSeparator;

    @Getter
    private final String dateFormat;

    private ParserProperties() {
        final String propertiesFileName = "parser.properties";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            try (InputStream stream = classLoader.getResourceAsStream(propertiesFileName)) {
                val properties = new Properties();
                properties.load(stream);

                fieldsSeparator = readFieldsSeparator(properties);
                dateFormat = readDateFormat(properties);

            }
        } catch (Exception e) {
            throw new ConfigurationException(PROPERTIES_READ_ERROR, e.getMessage());
        }
    }

    private String readFieldsSeparator(Properties properties) {
        final String key = "fields.separator";
        return properties.getProperty(key);
    }

    private String readDateFormat(Properties properties) {
        final String key = "date.format";
        return properties.getProperty(key);
    }

    public static ParserProperties read() {
        return new ParserProperties();
    }
}
