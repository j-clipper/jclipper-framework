package jclipper.common.time;

import junit.framework.TestCase;
import org.junit.Assert;

import java.time.Month;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.stream.IntStream;

/**
 * @author wangfeng
 * @time 2017/04/21 15:28.
 */
public class DateHelperTest extends TestCase {

    public void test_style() {
        Assert.assertEquals(DateStyle.YYYY_MM_DD_HH_MM_SS, DateHelper.style("2017-04-27 21:29:32"));
        Assert.assertEquals(DateStyle.SLASH_YYYY_MM_DD, DateHelper.style("2017/04/27"));
        Assert.assertEquals(DateStyle.YYYY_MM_DD, DateHelper.style("2017-04-27"));
        Assert.assertEquals(DateStyle.SLASH_YYYY_MM_DD_HH_MM_SS, DateHelper.style("2017/04/27 21:29:32"));
        Assert.assertEquals(DateStyle.YYYY_MM, DateHelper.style("2017-04"));
        Assert.assertEquals(DateStyle.YYYY_MM_DD_HH_MM, DateHelper.style("2017-04-27 21:29"));
        Assert.assertEquals(DateStyle.YYYY_MM_DD_HH, DateHelper.style("2017-04-27 21"));
        Assert.assertEquals(DateStyle.YYYYMM, DateHelper.style("201704"));
        Assert.assertEquals(DateStyle.YYYYMMDD, DateHelper.style("20170427"));
        Assert.assertEquals(DateStyle.YYYYMMDDHH, DateHelper.style("2017042721"));
        Assert.assertEquals(DateStyle.YYYY_MM_DD_HH_MM, DateHelper.style("2015-10-11 14:14"));
        Assert.assertEquals(DateStyle.MM_DD, DateHelper.style("10-11"));
    }

    public void test_style11() {
        Assert.assertEquals(DateStyle.MM_DD, DateHelper.style("10-11"));
    }

    public void test_style1() {
        IntStream.range(0, 1).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> Assert.assertEquals(key, DateHelper.style(value))));
    }

    public void test_style3() throws Exception {
        IntStream.range(0, 10000).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> Assert.assertEquals(key, DateHelper.style(value))));
    }

//    public void test_style4() throws Exception {
//        IntStream.range(0, 10000).parallel().forEach(
//                i -> TimeConsts.map.forEach((key, value) -> Assert.assertEquals(key, DateHelper.styleByPattern(value))));
//    }

    public void test_style51() throws Exception {
        IntStream.range(0, 1).forEach(
                i -> TimeConsts.map.forEach((key, value) -> {
                    DateStyle style = DateHelper.styleByRegex(value);
                    System.out.println(key.equals(style) + "\t" + key + "\t" + value + "\t" + style);
                }));
    }

    public void test_style6() throws Exception {
        IntStream.range(0, 100000).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> Assert.assertEquals(key, DateHelper.styleByRegex(value))));
    }

    public void testDateToLocalDate() throws Exception {
        LocalDate result = DateHelper.dateToLocalDate(new GregorianCalendar(2017, Calendar.APRIL, 21, 15, 28).getTime());
        Assert.assertEquals(LocalDate.of(2017, Month.APRIL, 21), result);
    }

    public void testLocalDateTimeToDate() throws Exception {
        Date result = DateHelper.toDate(LocalDateTime.of(2017, Month.APRIL, 21, 15, 28, 24));
        Assert.assertEquals(new GregorianCalendar(2017, Calendar.APRIL, 21, 15, 28, 24).getTime(), result);
    }

    public void test_parse_datetime() {
        String text = "2017年04月21日 16时35分";
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 16, 35, 0), DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 16, 35, 0), DateHelper.parse(text));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 16, 35, 0), DateHelper.parseToObject(text, LocalDateTime.class));
    }

    public void test_parse_date() {
        String text = "2017年04月21日";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 0, 0, 0), DateHelper.parse(text));
        Assert.assertEquals(LocalDate.of(2017, 4, 21), DateHelper.parseToObject(text, LocalDate.class));
    }

    public void test_parse_yearMonth() {
        String text = "2017年04月";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 1, 0, 0, 0), DateHelper.parse(text));
        Assert.assertEquals(YearMonth.of(2017, 4), DateHelper.parseToObject(text, YearMonth.class));
    }

    public void test_parse_monthDay() {
        String text = "04月21日";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 4, 21, 0, 0, 0), DateHelper.parse(text));
        Assert.assertEquals(MonthDay.of(4, 21), DateHelper.parseToObject(text, MonthDay.class));
    }

    public void test_parse_time() {
        String text = "16:35:10";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 35, 10)), DateHelper.parse(text));
        Assert.assertEquals(LocalTime.of(16, 35, 10), DateHelper.parseToObject(text, LocalTime.class));
    }

    public void test_parse_time_cn() {
        String text = "16时35分10秒";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 35, 10)), DateHelper.parse(text));
        Assert.assertEquals(LocalTime.of(16, 35, 10), DateHelper.parseToObject(text, LocalTime.class));
    }

    public void test_parse_time_cn2() {
        String text = "16时35分";
        Assert.assertEquals(null, DateHelper.parseStrict(text));
        Assert.assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 35)), DateHelper.parse(text));
        Assert.assertEquals(LocalTime.of(16, 35), DateHelper.parseToObject(text, LocalTime.class));
    }

    public void test_parse4() {
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDate.of(2017, 4, 21), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.SLASH_YYYY_MM_DD), LocalDate.class));
        Assert.assertEquals(LocalDate.of(2017, 4, 21), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYY_MM_DD), LocalDate.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.SLASH_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(YearMonth.of(2017, 4), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYY_MM), YearMonth.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 0, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYY_MM_DD_HH), LocalDateTime.class));
        Assert.assertEquals(YearMonth.of(2017, 4), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMM), YearMonth.class));
        Assert.assertEquals(LocalDate.of(2017, 4, 21), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMMDD), LocalDate.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 0, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMMDDHH), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMMDDHHMM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMMDDHHMMSS), LocalDateTime.class));
        Assert.assertEquals(LocalDate.of(2017, 4, 21), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.SLASH_YYYY_MM_DD), LocalDate.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 0, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.SLASH_YYYY_MM_DD_HH), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.SLASH_YYYY_MM_DD_HH_MM), LocalDateTime.class));

        Assert.assertEquals(YearMonth.of(2017, 4), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMM), YearMonth.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_2_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_3_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_2_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_3_YYYY_MM_DD_HH_MM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 0), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.YYYYMMDDHHMM), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_2_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN_3_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_2_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
        Assert.assertEquals(LocalDateTime.of(2017, 4, 21, 13, 27, 59), DateHelper.parseToObject(TimeConsts.map.get(DateStyle.CN2_3_YYYY_MM_DD_HH_MM_SS), LocalDateTime.class));
    }

    public void test_parseByRegex() {
        IntStream.range(0, 10000).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> {
                    DateHelper.parseByRegex(value);
//                    System.out.println(key.value() + "\t" + value + "\t" + DateHelper.parseByRegex(value));
                }));

    }

    public void test_parse_format() {
        String date = "2/5/2017 14点:6分";
        Formatter formatter = new Formatter("dd/MM/yyyy HH点:mm分");
        LocalDateTime parse = DateHelper.parse(date, formatter);
        Assert.assertEquals(LocalDateTime.of(2017, 5, 2, 14, 6, 0), parse);
    }

    public void test_parse_format_strict() {
        String date = "0205201706";
        Formatter formatter = new Formatter("ddMMyyyymm");
        LocalDateTime parse = DateHelper.parse(date, formatter);
        Assert.assertEquals(LocalDateTime.of(2017, 5, 2, 0, 6, 0), parse);
    }

    public void test_parseByAuto() {
        IntStream.range(0, 10000).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> {
                    DateHelper.parse(value);
//                    System.out.println(key.value() + "\t" + value + "\t" + DateHelper.parseByRegex(value));
                }));

    }

    public void test_parseByRegex2() {
        LocalDateTime time = DateHelper.parseByRegex(t2);
        System.out.println(time);
    }


    private static final LocalDateTime DT = LocalDateTime.of(2017, 4, 21, 12, 59, 59);
    private static String t1 = "2017-04-21";
    private static String t2 = "2017-04-21 16:35:10";


    public void test_addYear() {
        LocalDateTime dateTime = DT.plusYears(1);
        Assert.assertEquals(LocalDateTime.of(2018, 4, 21, 12, 59, 59),
                dateTime);
    }

    public void test_addMonth() {
        LocalDateTime dateTime = DT.plusMonths(1);
        Assert.assertEquals(LocalDateTime.of(2017, 5, 21, 12, 59, 59),
                dateTime);
    }

    public void test_addDay() {
        LocalDateTime dateTime = DT.plusDays(1);
        Assert.assertEquals(LocalDateTime.of(2017, 4, 22, 12, 59, 59),
                dateTime);
    }

    public void test_getYear() {
        int year = DT.getYear();
        Assert.assertEquals(2017, year);
    }

    public void test_getMonth() {
        int month = DT.getMonthValue();
        Assert.assertEquals(4, month);
    }

    public void test_getDayOfYear() {
        int month = DT.getDayOfYear();
        Assert.assertEquals(111, month);
    }

    public void test_getDayOfMonth() {
        int month = DT.getDayOfMonth();
        Assert.assertEquals(21, month);
    }

    public void test_getDayOfWeek() {
        DayOfWeek dayOfWeek = DT.getDayOfWeek();
        String cn = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.CHINA);
        long day = dayOfWeek.getLong(ChronoField.DAY_OF_WEEK);
        Assert.assertEquals("星期五", cn);
        Assert.assertEquals(5, day);
    }

    public void test_startOfYear() {
        LocalDateTime startOfYear = DateHelper.startOfYear(DT);
        Assert.assertEquals(LocalDateTime.of(LocalDate.of(2017, 1, 1), LocalTime.ofNanoOfDay(0)), startOfYear);
    }

    public void test_startOfYear1() {
        String s = DateHelper.startOfYear(t1);
//        Assert.assertEquals("2017年01月01日", s);
        Assert.assertEquals("2017-01-01", s);
    }


    public void test_endOfYear() {
        LocalDateTime endOfYear = DateHelper.endOfYear(DT);
        Assert.assertEquals(LocalDateTime.of(LocalDate.of(2017, 12, 31), LocalTime.MAX), endOfYear);
    }

    public void test_style2() {
        Date date = DateUtils.parseToDate("2015-10");
        System.out.println(date);
    }


    public void test_toYearMonth() {
        Assert.assertEquals("2017-04", DateHelper.toYearMonth(LocalDateTime.of(LocalDate.of(2017, 4, 1), LocalTime.now())).toString());
        Assert.assertEquals("2017-04", DateHelper.toYearMonth(LocalDateTime.of(LocalDate.of(2017, 4, 1), LocalTime.now())).toString());
    }

    public void test_addDay2() {
        LocalDate start = LocalDate.of(2017, 4, 10);
        LocalDate end = start.plusDays(100);
        System.out.println(DateHelper.format(end));
    }

    public void test_format() {
        YearMonth yearMonth = YearMonth.of(2017, 7);
        Assert.assertEquals("2017-07", DateHelper.format(yearMonth));
        Assert.assertEquals("2017年07月", DateHelper.format(yearMonth, DateStyle.CN_YYYY_MM));
    }

    public void test_timestamp() {
        System.out.println(DateHelper.timestamp());
    }

}


