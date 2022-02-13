package jclipper.common.time;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java8 时间工具类
 *
 * @author wf2311
 */
public final class DateHelper {
    private static final Logger log = Logger.getLogger("dateHelper");

    /**
     * 默认格式样式： {@link DateStyle#YYYY_MM_DD_HH_MM_SS}
     */
    public static final DateStyle DEFAULT_FORMATTER_STYLE = DateStyle.YYYY_MM_DD_HH_MM_SS;

    /**
     * 默认转换格式
     */
    public static final DateTimeFormatter DEFAULT_FORMATTER = formatter(DEFAULT_FORMATTER_STYLE);

    /**
     * 时区
     */
    private static final Locale LOCALE = Locale.CHINA;

    private DateHelper() {
    }

    /**
     * 创建{@link DateTimeFormatter}
     */
    public static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.LENIENT);
    }

    /**
     * 创建{@link DateTimeFormatter}
     */
    public static DateTimeFormatter formatter(Formatter formatter) {
        return formatter(formatter.value());
    }

    /**
     * 创建{@link DateTimeFormatter}
     */
    public static DateTimeFormatter formatter(DateStyle dateStyle) {
        return formatter(dateStyle.value());
    }

    private static Formatter.Type type(Class<?> support) {
        Formatter.Type type = Formatter.Type.find(support);
        if (type == null) {
            throw new IllegalArgumentException("不支持的转换类型：" + support);
        }
        return type;
    }

    /**
     * 从{@link DateStyle}中匹配时间格式。如果不存在，返回<code>null</code>
     *
     * @see DateHelper#styleByPattern(String)
     * @see DateHelper#styleByRegex(String)
     * @see DateHelper#styleByStrictRegex(String)
     * <strong>
     * 采用{@link DateHelper#styleByStrictRegex(String)}进行格式匹配
     * </strong>
     */
    public static DateStyle style(String text) {
        return styleByStrictRegex(text);
    }

    /**
     * 通过日期类型和格式从{@link DateStyle}中匹配时间格式。如果不存在，返回<code>null</code>
     * <strong>
     * 此方法花费的转换时间要5倍于其他方法
     * </strong>
     *
     * @see Formatter.Type
     */
    @Deprecated
    private static DateStyle styleByPattern(String text) {
        if (text == null || "".equals(text.trim())) {
            return null;
        }
        DateStyle style;
        style = style0(Formatter.Type.DATETIME, s -> LocalDateTime.parse(text, formatter(s)) != null);
        if (style != null) {
            return style;
        }
        style = style0(Formatter.Type.DATE, s -> LocalDate.parse(text, formatter(s)) != null);
        if (style != null) {
            return style;
        }
        style = style0(Formatter.Type.TIME, s -> LocalTime.parse(text, formatter(s)) != null);
        if (style != null) {
            return style;
        }
        style = style0(Formatter.Type.YEAR_MONTH, s -> YearMonth.parse(text, formatter(s)) != null);
        if (style != null) {
            return style;
        }
        style = style0(Formatter.Type.MONTH_DAY, s -> MonthDay.parse(text, formatter(s)) != null);
        if (style != null) {
            return style;
        }
        return null;
    }

    /**
     * 通过正则表达式从{@link DateStyle}中匹配时间格式。如果不存在，返回<code>null</code>
     * <strong>
     * 只对时间单位的长度限制进行匹配，不检验取值范围有效性。例如：
     * <pre>
     *     DateStyle.YYYY_MM_DD.equal(styleByRegex("2017-04-39"))==true;
     * </pre>
     * </strong>
     *
     * @see DateStyle#regex()
     * @see Formatter.Regex
     */
    public static DateStyle styleByRegex(String text) {
        if (text == null || "".equals(text.trim())) {
            return null;
        }
        return Arrays.stream(DateStyle.values())
                .filter(style -> !style.showOnly() && text.matches(style.regex()))
                .findAny().orElse(null);
    }

    /**
     * 通过严格的正则表达式从{@link DateStyle}中匹配时间格式。如果不存在，返回<code>null</code>
     * <strong>
     * 此方法同时对时间单位的长度限制和取值范围进行匹配。例如：
     * <pre>
     *     DateStyle.YYYY_MM_DD.equal(styleByRegex("2017-04-39"))==false;
     *     DateStyle.YYYY_MM_DD.equal(styleByRegex("2017-4-19"))==true;
     * </pre>
     * </strong>
     *
     * @see DateStyle#strictRegex()
     * @see Formatter.StrictRegex
     */
    public static DateStyle styleByStrictRegex(String text) {
        if (text == null || "".equals(text.trim())) {
            return null;
        }
        return Arrays.stream(DateStyle.values())
                .filter(style -> !style.showOnly() && text.matches(style.regex()) && text.matches(style.strictRegex()))
                .findAny().orElse(null);
    }

    private static DateStyle style0(Formatter.Type type, Predicate<DateStyle> predicate) {
        return Arrays.stream(DateStyle.values())
                .filter(style -> {
                    if (style.showOnly() || !type.equals(style.type())) {
                        return false;
                    }
                    try {
                        return predicate.test(style);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    return false;
                })
                .findAny().orElse(null);
    }

    //========================================格式转换=====================================

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     */
    public static LocalDateTime parse(String text, String pattern) {
        return parse(text, new Formatter(pattern));
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换或者不是<strong>预设的日期时间格式</strong>，返回<code>null</code>
     * <p>
     * <strong>预设的日期时间格式</strong>是指
     * {@link DateStyle#type()} == {@link Formatter.Type#DATETIME}
     * </p>
     */
    public static LocalDateTime parseStrict(String text) {
        DateStyle style = style(text);
        if (style == null || !Formatter.Type.DATETIME.equals(style.type())) {
            return null;
        }
        return parse(text, style);
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     *
     * @throws IllegalArgumentException
     */
    public static LocalDateTime parse(String text) {
        DateStyle style = style(text);
        if (style == null) {
            return null;
        }
        return parse(text, style);
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     *
     * @throws IllegalArgumentException
     */
    public static LocalDateTime parseByRegex(String text) {
        DateStyle style = style(text);
        if (style == null) {
            return null;
        }
        Formatter formatter = style.formatter();
        return parse(text, formatter);
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     *
     * @throws IllegalArgumentException
     */
    public static LocalDateTime parse(String text, DateStyle style) {
        return parse(text, style.value(), style.type().value());
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     *
     * @throws IllegalArgumentException
     */
    public static LocalDateTime parse(String text, Formatter formatter) {
        int year = Year.now().getValue();
        int month = 1;
        int day = 1;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int[] timeParts = formatter.lengthStrict() ? timePartStrict(text, formatter) : timePart(text, formatter);
        if (timeParts == null || timeParts.length != formatter.contains().size()) {
            return null;
        }
        for (int i = 0; i < formatter.contains().size(); i++) {
            switch (formatter.contains().get(i)) {
                case YEAR:
                    year = timeParts[i];
                    break;
                case MONTH:
                    month = timeParts[i];
                    break;
                case DAY:
                    day = timeParts[i];
                    break;
                case HOUR:
                    hour = timeParts[i];
                    break;
                case MINUTE:
                    minute = timeParts[i];
                    break;
                case SECOND:
                    second = timeParts[i];
                    break;
                default:
                    break;
            }
        }
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * 将时间字符串{@link String}转为{@link LocalDateTime}。如果无法转换，返回<code>null</code>
     *
     * @param <T> 取值{@link Formatter.Type#value()}
     * @throws IllegalArgumentException
     */
    public static <T> LocalDateTime parse(String text, String pattern, Class<T> clazz) {
        Formatter.Type type = type(clazz);
        try {
            switch (type) {
                case DATE:
                    LocalDate localDate = LocalDate.parse(text, formatter(pattern));
                    return ofLocalDate(localDate);
                case DATETIME:
                    return LocalDateTime.parse(text, formatter(pattern));
                case TIME:
                    LocalTime localTime = LocalTime.parse(text, formatter(pattern));
                    return ofLocalTime(localTime);
                case MONTH_DAY:
                    MonthDay monthDay = MonthDay.parse(text, formatter(pattern));
                    return ofMonthDay(monthDay);
                case YEAR_MONTH:
                    YearMonth yearMonth = YearMonth.parse(text, formatter(pattern));
                    return ofYearMonth(yearMonth);
                default:
                    return null;
            }
        } catch (Exception ignored) {
            log.warning(ignored.getMessage());
            return null;
        }
    }


    /**
     * 将时间字符串{@link String}转为指定时间类型。如果无法转换，返回<code>null</code>
     *
     * @param <T> 取值{@link Formatter.Type#value()}
     * @throws IllegalArgumentException
     */
    public static <T> T parseToObject(String text, Class<T> clazz) {
        DateStyle style = style(text);
        if (style == null) {
            return null;
        }
        return parseToObject(text, style, clazz);
    }

    /**
     * 将时间字符串{@link String}转为指定时间类型。如果无法转换，返回<code>null</code>
     *
     * @param <T> 取值{@link Formatter.Type#value()}
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseToObject(String text, String pattern, Class<T> clazz) {
        Formatter.Type type = type(clazz);
        try {
            switch (type) {
                case DATE:
                    return (T) LocalDate.parse(text, formatter(pattern));
                case DATETIME:
                    return (T) LocalDateTime.parse(text, formatter(pattern));
                case TIME:
                    return (T) LocalTime.parse(text, formatter(pattern));
                case MONTH_DAY:
                    return (T) MonthDay.parse(text, formatter(pattern));
                case YEAR_MONTH:
                    return (T) YearMonth.parse(text, formatter(pattern));
                default:
                    return null;
            }
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * 将时间字符串{@link String}转为指定时间类型。如果无法转换，返回<code>null</code>
     *
     * @param <T> 取值{@link Formatter.Type#value()}
     * @throws IllegalArgumentException
     */
    public static <T> T parseToObject(String text, DateStyle style, Class<T> clazz) {
        return parseToObject(text, style.value(), clazz);
    }

    private static int[] timePart(String text, Formatter formatter) {
        Matcher matcher = Pattern.compile(formatter.strictRegex()).matcher(text);
        if (matcher.find()) {
            int[] part = new int[matcher.groupCount()];
            for (int i = 0; i < matcher.groupCount(); i++) {
                part[i] = Integer.parseInt(matcher.group(i + 1));
            }
            return part;
        }
        return new int[0];
    }

    private static int[] timePartStrict(String text, Formatter formatter) {
        if (!formatter.lengthStrict()) {
            throw new IllegalArgumentException("非法参数");
        }
        int start = 0;
        int end = 0;
        List<Integer> list = new ArrayList<>();
        for (Formatter.Part part : Formatter.Part.values()) {
            if (formatter.value().contains(part.value())) {
                end += part.value().length();
                list.add(Integer.valueOf(text.substring(start, end)));
                start = end + 1;
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 将{@link LocalDateTime}格式化为字符串{@link String}。默认格式{@link #DEFAULT_FORMATTER_STYLE}
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_FORMATTER);
    }

    /**
     * 将{@link LocalDateTime}格式化为字符串{@link String}
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(formatter(pattern));
    }

    /**
     * 将{@link LocalDateTime}格式化为字符串{@link String}
     */
    public static String format(LocalDateTime dateTime, DateStyle style) {
        return dateTime.format(formatter(style));
    }

    /**
     * 将{@link LocalDate}格式化为字符串{@link String}。默认格式{@link DateStyle#YYYY_MM_DD}
     */
    public static String format(LocalDate date) {
        return date.format(formatter(DateStyle.YYYY_MM_DD));
    }

    /**
     * 将{@link LocalDate}格式化为字符串{@link String}。
     */
    public static String format(LocalDate date, String pattern) {
        return date.format(formatter(pattern));
    }

    /**
     * 将{@link LocalDate}格式化为字符串{@link String}。
     */
    public static String format(LocalDate date, DateStyle style) {
        if (!Formatter.Type.DATE.equals(style.type())) {
            throw new IllegalArgumentException();
        }
        return date.format(formatter(style));
    }

    /**
     * 将{@link LocalTime}格式化为字符串{@link String}。默认格式{@link DateStyle#HH_MM_SS}
     */
    public static String format(LocalTime time) {
        return time.format(formatter(DateStyle.YYYY_MM_DD));
    }

    /**
     * 将{@link LocalTime}格式化为字符串{@link String}。
     */
    public static String format(LocalTime time, String pattern) {
        return time.format(formatter(pattern));
    }

    /**
     * 将{@link LocalTime}格式化为字符串{@link String}。
     */
    public static String format(LocalTime time, DateStyle style) {
        if (!Formatter.Type.TIME.equals(style.type())) {
            throw new IllegalArgumentException();
        }
        return time.format(formatter(style));
    }

    /**
     * 将{@link MonthDay}格式化为字符串{@link String}。默认格式{@link DateStyle#MM_DD}
     */
    public static String format(MonthDay monthDay) {
        return monthDay.format(formatter(DateStyle.MM_DD));
    }

    /**
     * 将{@link MonthDay}格式化为字符串{@link String}。
     */
    public static String format(MonthDay monthDay, String pattern) {
        return monthDay.format(formatter(pattern));
    }

    /**
     * 将{@link MonthDay}格式化为字符串{@link String}。
     */
    public static String format(MonthDay monthDay, DateStyle style) {
        if (!Formatter.Type.MONTH_DAY.equals(style.type())) {
            throw new IllegalArgumentException();
        }
        return monthDay.format(formatter(style));
    }

    /**
     * 将{@link YearMonth}格式化为字符串{@link String}。默认格式{@link DateStyle#YYYY_MM}
     */
    public static String format(YearMonth yearMonth) {
        return yearMonth.format(formatter(DateStyle.YYYY_MM));
    }

    /**
     * 将{@link YearMonth}格式化为字符串{@link String}。
     */
    public static String format(YearMonth yearMonth, String pattern) {
        return yearMonth.format(formatter(pattern));
    }

    /**
     * 将{@link YearMonth}格式化为字符串{@link String}。
     */
    public static String format(YearMonth yearMonth, DateStyle style) {
        if (!Formatter.Type.YEAR_MONTH.equals(style.type())) {
            throw new IllegalArgumentException();
        }
        return yearMonth.format(formatter(style));
    }

    //======================================格式转换 结束===================================

    //========================================时间变更=====================================

    /**
     * 所在日期的当年开始时刻
     */
    public static LocalDateTime startOfYear(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException();
        }
        return LocalDateTime.of(LocalDate.of(dateTime.getYear(), 1, 1), LocalTime.MIN);
    }

    /**
     * 所在日期的当年开始时刻
     */
    public static String startOfYear(String text) {
        DateStyle style = style(text);
        if (style == null) {
            return null;
        }
        return format(startOfYear(parse(text, style)), style);
    }


    /**
     * 所在日期的当年结束时刻
     */
    public static LocalDateTime endOfYear(LocalDateTime dateTime) {
        return LocalDateTime.of(LocalDate.of(dateTime.getYear(), 12, 31), LocalTime.MAX);
    }

    /**
     * 所在日期的当月开始时刻
     */
    public static LocalDateTime startOfMonth(LocalDateTime dateTime) {
        return LocalDateTime.of(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), 1), LocalTime.MIN);
    }

    /**
     * 所在日期的当月结束时刻
     */
    public static LocalDateTime endOfMonth(LocalDateTime dateTime) {
        return startOfMonth(dateTime).plusMonths(1).minusNanos(1);
    }


    /**
     * 所在日期当周的某天相同时刻
     */
    public static LocalDateTime timeOfWeek(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time.with(dayOfWeek);
    }

    /**
     * 当周的开始时刻
     */
    public static LocalDateTime startOfWeek(LocalDateTime time) {
        return timeOfWeek(time, DayOfWeek.MONDAY).with(LocalTime.MIN);
    }

    /**
     * 当周的结束时刻
     */
    public static LocalDateTime endOfWeek(LocalDateTime time) {
        return timeOfWeek(time, DayOfWeek.SUNDAY).with(LocalTime.MAX);
    }

    /**
     * 所在日期当周的某天
     */
    public static LocalDate dayOfWeek(LocalDate day, DayOfWeek dayOfWeek) {
        return day.with(dayOfWeek);
    }

    /**
     * 当周的第一天(周一)
     */
    public static LocalDate startDayOfWeek(LocalDate day) {
        return dayOfWeek(day, DayOfWeek.MONDAY);
    }

    /**
     * 当周的最后一天(周日)
     */
    public static LocalDate endDayOfWeek(LocalDate day) {
        return dayOfWeek(day, DayOfWeek.SUNDAY);
    }

    /**
     * 所在日期的当天开始时刻
     */
    public static LocalDateTime startOfDay(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.MIN);
    }

    /**
     * 所在日期的结束开始时刻
     */
    public static LocalDateTime endOfDay(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.MAX);
    }

    /**
     * 所在日期的当周的第几天
     */
    public static LocalDateTime dayOfWeek(LocalDateTime dateTime, int dayOfWeek) {
        if (dayOfWeek > 7 || dayOfWeek < 1) {
            throw new IllegalArgumentException();
        }
        return dateTime.plusDays((long) (dayOfWeek - dateTime.getDayOfWeek().getValue()));
    }

    /**
     * 所在日期的当周的第几天
     */
    public static LocalDateTime dayOfWeek(LocalDateTime dateTime, DayOfWeek dayOfWeek) {
        if (dayOfWeek == null || dateTime == null) {
            throw new IllegalArgumentException();
        }
        return dateTime.plusDays((long) (dayOfWeek.getValue() - dateTime.getDayOfWeek().getValue()));
    }

    /**
     * 所在日期的当周的第几天
     */
    public static LocalDate dayOfWeek(LocalDate date, int dayOfWeek) {
        if (date == null || dayOfWeek > 7 || dayOfWeek < 1) {
            throw new IllegalArgumentException();
        }
        return date.plusDays((long) (dayOfWeek - date.getDayOfWeek().getValue()));
    }

    //======================================时间变更 结束===================================


    //========================================类型转换=====================================

    /**
     * {@link Date}转{@link LocalDateTime}
     */
    public static LocalDateTime ofDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    /**
     * {@link LocalDateTime}转{@link Date}
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link LocalTime}转{@link Date},日期为当天
     *
     * @return 今天的指定时刻
     */
    public static Date toDate(LocalTime time) {
        if (time == null) {
            return null;
        }
        return toDate(ofLocalTime(time));
    }

    /**
     * {@link LocalDate}转{@link Date},时间为00:00:00
     *
     * @return 指定日期的零点
     */
    public static Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return toDate(ofLocalDate(date));
    }

    /**
     * {@link LocalDate}转{@link LocalDateTime}
     *
     * @return 当天的开始时刻
     */
    public static LocalDateTime ofLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * {@link LocalDateTime}转{@link LocalDate}
     */
    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDate();
    }

    /**
     * {@link YearMonth}转{@link LocalDateTime}
     *
     * @return 所在月开始时刻
     */
    public static LocalDateTime ofYearMonth(YearMonth yearMonth) {
        if (yearMonth == null) {
            return null;
        }
        return LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN);
    }

    /**
     * {@link LocalDateTime}转{@link YearMonth}
     */
    public static YearMonth toYearMonth(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return YearMonth.of(dateTime.getYear(), dateTime.getMonth());
    }

    /**
     * {@link MonthDay}转{@link LocalDateTime}
     *
     * @return 当前年所在月日的开始时刻
     */
    public static LocalDateTime ofMonthDay(MonthDay monthDay) {
        if (monthDay == null) {
            return null;
        }
        return LocalDateTime.of(monthDay.atYear(LocalDateTime.now().getYear()), LocalTime.MIN);
    }

    /**
     * {@link LocalDateTime}转{@link MonthDay}
     */
    public static MonthDay toMonthDay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return MonthDay.of(dateTime.getMonth(), dateTime.getDayOfMonth());
    }

    /**
     * {@link YearMonth}转{@link LocalDateTime}
     *
     * @return 当天的所在时刻
     */
    public static LocalDateTime ofLocalTime(LocalTime time) {
        if (time == null) {
            return null;
        }
        return LocalDateTime.of(LocalDate.now(), time);
    }

    /**
     * {@link LocalDateTime}转{@link LocalTime}
     */
    public static LocalTime toLocalTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalTime();
    }

    /**
     * {@link LocalDate}转{@link Date}
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    //======================================类型转换 结束===================================


    //======================================other 开始===================================


    /**
     * 将时间转为时间戳(毫秒)
     */
    public static long toTimestamp(LocalDateTime time) {
        if (time == null) {
            return 0;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当前时间的时间戳(毫秒)
     */
    public static long timestamp() {
        return toTimestamp(LocalDateTime.now());
    }

    /**
     * 将时间转为时间戳(秒)
     */
    public static long toUnixTimestamp(LocalDateTime time) {
        return toTimestamp(time) / 1000;
    }

    /**
     * 获取当前时间的时间戳(秒)
     */
    public static long unixTimestamp() {
        return timestamp() / 1000;
    }

    //======================================other 结束===================================


}
