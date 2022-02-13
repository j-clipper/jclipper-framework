package jclipper.springboot.java.time.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wf2311
 * @since  2017/05/15 09:50.
 */
public final class LocalTimeConverter implements Converter<String, LocalTime> {

    private final DateTimeFormatter formatter;

    public LocalTimeConverter(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public LocalTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, formatter);
    }
}
