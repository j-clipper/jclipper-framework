package jclipper.common.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.IntStream;

/**
 * @author wf2311
 */
public class DateUtilsTest {

    @Mock
    ThreadLocal<SimpleDateFormat> threadLocal;
    @Mock
    Object object;
    //Field DEFAULT_LOCALE of type Locale - was not mocked since Mockito doesn't mock a Final class
    @InjectMocks
    DateUtils dateUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsDate() throws Exception {
        boolean result = DateUtils.isDate("2017年04月21日 13:27:59");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testGetDateStyle() throws Exception {
        IntStream.range(0, 100000).parallel().forEach(
                i -> TimeConsts.map.forEach((key, value) -> Assert.assertEquals(key, DateUtils.style(value)))
        );
    }

    @Test
    public void testGetDateStyle2() throws Exception {
        DateStyle dateStyle = DateUtils.style("2017年04月21日 13:27:59");
        System.out.println(dateStyle);
    }

    private GregorianCalendar calendar = new GregorianCalendar(2017, Calendar.APRIL, 21, 11, 48, 59);
    private String date = "2017-04-21 11:48:59";

    @Test
    public void testParseToCalendar() throws Exception {
        Calendar result = DateUtils.parseToCalendar(calendar.getTime());
        Assert.assertEquals(calendar, result);
    }

    @Test
    public void testParseToCalendar2() throws Exception {
        Calendar result = DateUtils.parseToCalendar(date);
        Assert.assertEquals(calendar, result);
    }

    @Test
    public void testParseToDate() throws Exception {
        Date result = DateUtils.parseToDate(date);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testParseToDate2() throws Exception {
        Date result = DateUtils.parseToDate(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testParseToDate3() throws Exception {
        Date result = DateUtils.parseToDate(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testParseToLong() throws Exception {
        long result = DateUtils.parseToLong(calendar.getTime());
        Assert.assertEquals(calendar.getTime().getTime(), result);
    }

    @Test
    public void testParseToLong2() throws Exception {
        long result = DateUtils.parseToLong(date);
        Assert.assertEquals(calendar.getTime().getTime(), result);
    }

    @Test
    public void testParseToString() throws Exception {
        String result = DateUtils.parseToString(calendar.getTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
        Assert.assertEquals(date, result);
    }

    @Test
    public void testParseToString2() throws Exception {
        String result = DateUtils.parseToString(calendar.getTime());
        Assert.assertEquals(date, result);
    }

    @Test
    public void testParseToString3() throws Exception {
        String result = DateUtils.parseToString(calendar.getTime(), DateStyle.YYYY_MM);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToDefaultString() throws Exception {
        String result = DateUtils.parseToDefaultString(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString4() throws Exception {
        String result = DateUtils.parseToString(date, "newPattern");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString5() throws Exception {
        String result = DateUtils.parseToString(date, DateStyle.YYYY_MM);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString6() throws Exception {
        String result = DateUtils.parseToString(date, "oldPattern", "newPattern");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString7() throws Exception {
        String result = DateUtils.parseToString(date, DateStyle.YYYY_MM, "newPattern");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString8() throws Exception {
        String result = DateUtils.parseToString(date, "oldPattern", DateStyle.YYYY_MM);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToString9() throws Exception {
        String result = DateUtils.parseToString(date, DateStyle.YYYY_MM, DateStyle.YYYY_MM);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testParseToDate4() throws Exception {
        Date result = DateUtils.parseToDate("2017-04-21 11:48");
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testParseToTimestamp() throws Exception {
        Timestamp result = DateUtils.parseToTimestamp(calendar.getTime());
        Assert.assertEquals(null, result);
    }

    @Test
    public void testAddYear() throws Exception {
        String result = DateUtils.addYear(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddYear2() throws Exception {
        Date result = DateUtils.addYear(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddMonth() throws Exception {
        String result = DateUtils.addMonth(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddMonth2() throws Exception {
        Date result = DateUtils.addMonth(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddDay() throws Exception {
        String result = DateUtils.addDay(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddDay2() throws Exception {
        Date result = DateUtils.addDay(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddHour() throws Exception {
        String result = DateUtils.addHour(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddHour2() throws Exception {
        Date result = DateUtils.addHour(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddMinute() throws Exception {
        String result = DateUtils.addMinute(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddMinute2() throws Exception {
        Date result = DateUtils.addMinute(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddSecond() throws Exception {
        String result = DateUtils.addSecond(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddSecond2() throws Exception {
        Date result = DateUtils.addSecond(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testAddMillis() throws Exception {
        String result = DateUtils.addMillis(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testAddMillis2() throws Exception {
        Date result = DateUtils.addMillis(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetYear() throws Exception {
        int result = DateUtils.getYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetYear2() throws Exception {
        int result = DateUtils.getYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDaysOfYear() throws Exception {
        int result = DateUtils.getDaysOfYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDaysOfYear2() throws Exception {
        int result = DateUtils.getDaysOfYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetFirstDayOfYear() throws Exception {
        String result = DateUtils.getFirstDayOfYear(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetFirstDayOfYear2() throws Exception {
        Date result = DateUtils.getFirstDayOfYear(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfYear() throws Exception {
        String result = DateUtils.getStartOfYear(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetStartOfYear2() throws Exception {
        Date result = DateUtils.getStartOfYear(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetLastDayOfYear() throws Exception {
        String result = DateUtils.getLastDayOfYear(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetLastDayOfYear2() throws Exception {
        Date result = DateUtils.getLastDayOfYear(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfYear() throws Exception {
        String result = DateUtils.getEndOfYear(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEndOfYear2() throws Exception {
        Date result = DateUtils.getEndOfYear(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetPassDayOfYear() throws Exception {
        int result = DateUtils.getPassDayOfYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetPassDayOfYear2() throws Exception {
        int result = DateUtils.getPassDayOfYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfYear() throws Exception {
        int result = DateUtils.getRemainDayOfYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfYear2() throws Exception {
        int result = DateUtils.getRemainDayOfYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetFirstDayOfSeason() throws Exception {
        String result = DateUtils.getFirstDayOfSeason(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetFirstDayOfSeason2() throws Exception {
        Date result = DateUtils.getFirstDayOfSeason(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfSeason() throws Exception {
        String result = DateUtils.getStartOfSeason(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetStartOfSeason2() throws Exception {
        Date result = DateUtils.getStartOfSeason(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetLastDayOfSeason() throws Exception {
        String result = DateUtils.getLastDayOfSeason(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetLastDayOfSeason2() throws Exception {
        Date result = DateUtils.getLastDayOfSeason(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfSeason() throws Exception {
        String result = DateUtils.getEndOfSeason(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEndOfSeason2() throws Exception {
        Date result = DateUtils.getEndOfSeason(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetDaysOfSeason() throws Exception {
        int result = DateUtils.getDaysOfSeason(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDaysOfSeason2() throws Exception {
        int result = DateUtils.getDaysOfSeason(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetSeason() throws Exception {
        int result = DateUtils.getSeason(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetSeason2() throws Exception {
        int result = DateUtils.getSeason(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetSeasonDate() throws Exception {
        Date[] result = DateUtils.getSeasonDate(date);
        Assert.assertArrayEquals(new Date[]{calendar.getTime()}, result);
    }

    @Test
    public void testGetSeasonDate2() throws Exception {
        Date[] result = DateUtils.getSeasonDate(calendar.getTime());
        Assert.assertArrayEquals(new Date[]{calendar.getTime()}, result);
    }

    @Test
    public void testGetPassDayOfSeason() throws Exception {
        int result = DateUtils.getPassDayOfSeason(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetPassDayOfSeason2() throws Exception {
        int result = DateUtils.getPassDayOfSeason(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfSeason() throws Exception {
        int result = DateUtils.getRemainDayOfSeason(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfSeason2() throws Exception {
        int result = DateUtils.getRemainDayOfSeason(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetMonth() throws Exception {
        int result = DateUtils.getMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetMonth2() throws Exception {
        int result = DateUtils.getMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDefinedMonth() throws Exception {
        Month result = DateUtils.getDefinedMonth(date);
        Assert.assertEquals(Month.JANUARY, result);
    }

    @Test
    public void testGetDefinedMonth2() throws Exception {
        Month result = DateUtils.getDefinedMonth(calendar.getTime());
        Assert.assertEquals(Month.JANUARY, result);
    }

    @Test
    public void testGetDayOfMonth() throws Exception {
        int result = DateUtils.getDayOfMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDayOfMonth2() throws Exception {
        int result = DateUtils.getDayOfMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetPassDayOfMonth() throws Exception {
        int result = DateUtils.getPassDayOfMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetPassDayOfMonth2() throws Exception {
        int result = DateUtils.getPassDayOfMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfMonth() throws Exception {
        int result = DateUtils.getRemainDayOfMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetRemainDayOfMonth2() throws Exception {
        int result = DateUtils.getRemainDayOfMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetFirstDayOfMonth() throws Exception {
        String result = DateUtils.getFirstDayOfMonth(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetFirstDayOfMonth2() throws Exception {
        Date result = DateUtils.getFirstDayOfMonth(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfMonth() throws Exception {
        String result = DateUtils.getStartOfMonth(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetStartOfMonth2() throws Exception {
        Date result = DateUtils.getStartOfMonth(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetLastDayOfMonth() throws Exception {
        String result = DateUtils.getLastDayOfMonth(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetLastDayOfMonth2() throws Exception {
        Date result = DateUtils.getLastDayOfMonth(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfMonth() throws Exception {
        Date result = DateUtils.getEndOfMonth(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfMonth2() throws Exception {
        String result = DateUtils.getEndOfMonth(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetOtherDayOfMonth() throws Exception {
        Date result = DateUtils.getOtherDayOfMonth(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetOtherDayOfMonth2() throws Exception {
        String result = DateUtils.getOtherDayOfMonth(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetDayOfWeek() throws Exception {
        Date result = DateUtils.getDayOfWeek(calendar.getTime(), Week.MONDAY);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetDayOfWeek2() throws Exception {
        String result = DateUtils.getDayOfWeek(date, Week.MONDAY);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetWeekDay() throws Exception {
        int result = DateUtils.getWeekDay(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeekDay2() throws Exception {
        int result = DateUtils.getWeekDay(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeekOfYear() throws Exception {
        int result = DateUtils.getWeekOfYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeekOfYear2() throws Exception {
        int result = DateUtils.getWeekOfYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeeksOfYear() throws Exception {
        int result = DateUtils.getWeeksOfYear(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeeksOfYear2() throws Exception {
        int result = DateUtils.getWeeksOfYear(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeekOfMonth() throws Exception {
        int result = DateUtils.getWeekOfMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeekOfMonth2() throws Exception {
        int result = DateUtils.getWeekOfMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeeksOfMonth() throws Exception {
        int result = DateUtils.getWeeksOfMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetWeeksOfMonth2() throws Exception {
        int result = DateUtils.getWeeksOfMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDayOfWeekInMonth() throws Exception {
        int result = DateUtils.getDayOfWeekInMonth(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDayOfWeekInMonth2() throws Exception {
        int result = DateUtils.getDayOfWeekInMonth(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDefinedWeekOfWeek() throws Exception {
        Week result = DateUtils.getDefinedWeekOfWeek(date);
        Assert.assertEquals(Week.MONDAY, result);
    }

    @Test
    public void testGetDefinedWeekOfWeek2() throws Exception {
        Week result = DateUtils.getDefinedWeekOfWeek(calendar.getTime());
        Assert.assertEquals(Week.MONDAY, result);
    }

    @Test
    public void testGetMondayOfWeek() throws Exception {
        String result = DateUtils.getMondayOfWeek(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetMondayOfWeek2() throws Exception {
        Date result = DateUtils.getMondayOfWeek(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetSundayOfWeek() throws Exception {
        String result = DateUtils.getSundayOfWeek(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetSundayOfWeek2() throws Exception {
        Date result = DateUtils.getSundayOfWeek(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfWeek() throws Exception {
        String result = DateUtils.getStartOfWeek(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetStartOfWeek2() throws Exception {
        Date result = DateUtils.getStartOfWeek(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfWeek() throws Exception {
        String result = DateUtils.getEndOfWeek(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEndOfWeek2() throws Exception {
        Date result = DateUtils.getEndOfWeek(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetOtherDayOfWeek() throws Exception {
        String result = DateUtils.getOtherDayOfWeek(date, 0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetOtherDayOfWeek2() throws Exception {
        Date result = DateUtils.getOtherDayOfWeek(calendar.getTime(), 0);
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetDay() throws Exception {
        int result = DateUtils.getDay(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDay2() throws Exception {
        int result = DateUtils.getDay(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetHour() throws Exception {
        int result = DateUtils.getHour(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetHour2() throws Exception {
        int result = DateUtils.getHour(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetMinute() throws Exception {
        int result = DateUtils.getMinute(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetMinute2() throws Exception {
        int result = DateUtils.getMinute(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetSecond() throws Exception {
        int result = DateUtils.getSecond(date);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetSecond2() throws Exception {
        int result = DateUtils.getSecond(calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetDate() throws Exception {
        String result = DateUtils.getDate(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetDate2() throws Exception {
        String result = DateUtils.getDate(calendar.getTime());
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetTime() throws Exception {
        String result = DateUtils.getTime(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetTime2() throws Exception {
        String result = DateUtils.getTime(calendar.getTime());
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetIntervalDays() throws Exception {
        int result = DateUtils.getIntervalDays(date, "otherDate");
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetIntervalDays2() throws Exception {
        int result = DateUtils.getIntervalDays(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testIsLeapYear() throws Exception {
        boolean result = DateUtils.isLeapYear(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsLeapYear2() throws Exception {
        boolean result = DateUtils.isLeapYear(calendar.getTime());
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsLeapYear3() throws Exception {
        boolean result = DateUtils.isLeapYear(date);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testPastTimeFormat() throws Exception {
        String result = DateUtils.pastTimeFormat(calendar.getTime());
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testPastTimeFormat2() throws Exception {
        String result = DateUtils.pastTimeFormat(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testPastTimeFormat3() throws Exception {
        String result = DateUtils.pastTimeFormat(1L);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testMillisToSeconds() throws Exception {
        long result = DateUtils.millisToSeconds(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToMinutes() throws Exception {
        long result = DateUtils.millisToMinutes(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToHours() throws Exception {
        long result = DateUtils.millisToHours(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToWeeks() throws Exception {
        long result = DateUtils.millisToWeeks(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToDays() throws Exception {
        long result = DateUtils.millisToDays(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToMonths() throws Exception {
        long result = DateUtils.millisToMonths(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testMillisToYears() throws Exception {
        long result = DateUtils.millisToYears(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testDifferMills() throws Exception {
        long result = DateUtils.differMills(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testDifferSeconds() throws Exception {
        long result = DateUtils.differSeconds(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testDifferMinuts() throws Exception {
        long result = DateUtils.differMinuts(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testDifferHours() throws Exception {
        long result = DateUtils.differHours(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testDifferDays() throws Exception {
        long result = DateUtils.differDays(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testDifferWeeks() throws Exception {
        long result = DateUtils.differWeeks(calendar.getTime(), calendar.getTime());
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testHasCommon() throws Exception {
        boolean result = dateUtils.hasCommon(calendar.getTime(), calendar.getTime(), calendar.getTime(), calendar.getTime());
        Assert.assertEquals(true, result);
    }

    @Test
    public void testGetStartOfDay() throws Exception {
        Date result = DateUtils.getStartOfDay(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfDay2() throws Exception {
        String result = DateUtils.getStartOfDay(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetStartOfNextDay() throws Exception {
        Date result = DateUtils.getStartOfNextDay(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetStartOfNextDay2() throws Exception {
        String result = DateUtils.getStartOfNextDay(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEndOfDay() throws Exception {
        Date result = DateUtils.getEndOfDay(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfDay2() throws Exception {
        String result = DateUtils.getEndOfDay(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEndOfPrevDay() throws Exception {
        Date result = DateUtils.getEndOfPrevDay(calendar.getTime());
        Assert.assertEquals(calendar.getTime(), result);
    }

    @Test
    public void testGetEndOfPrevDay2() throws Exception {
        String result = DateUtils.getEndOfPrevDay(date);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme