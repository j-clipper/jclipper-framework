package jclipper.springboot.java.time.converter;

import jclipper.springboot.java.time.properties.JavaTimePatternProperties;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wf2311
 * @since 2017/05/15 09:50.
 */
public final class DateConverter implements Converter<String, Date> {
    /**
     * 日期正则表达式
     */
    private static final String DATE_REGEX = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    /**
     * 时间正则表达式
     */
    private static final String TIME_REGEX = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";

    /**
     * 日期和时间正则表达式
     */
    private static final String DATE_TIME_REGEX = DATE_REGEX + "\\s" + TIME_REGEX;

    /**
     * 13位时间戳正则表达式
     */
    private static final String TIME_STAMP_REGEX = "1\\d{12}";

    /**
     * 年和月正则表达式
     */
    private static final String YEAR_MONTH_REGEX = "[1-9]\\d{3}-(0[1-9]|1[0-2])";


    private final JavaTimePatternProperties properties;

    public DateConverter(JavaTimePatternProperties properties) {
        this.properties = properties;
    }

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        if (source.matches(TIME_STAMP_REGEX)) {
            return new Date(Long.parseLong(source));
        }
        DateFormat format;
        if (source.matches(DATE_TIME_REGEX)) {
            format = new SimpleDateFormat(properties.getDateTime());
        } else if (source.matches(DATE_REGEX)) {
            format = new SimpleDateFormat(properties.getDate());
        } else if (source.matches(YEAR_MONTH_REGEX)) {
            format = new SimpleDateFormat(properties.getYearMonth());
        } else {
            throw new IllegalArgumentException();
        }
        try {
            return format.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
