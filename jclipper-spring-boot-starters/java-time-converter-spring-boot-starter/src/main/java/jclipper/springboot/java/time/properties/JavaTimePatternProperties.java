package jclipper.springboot.java.time.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.format.DateTimeFormatter;

/**
 * @author wf2311
 * @since  2017/05/12 20:36.
 */
@ConfigurationProperties(prefix = "java.time.pattern")
public class JavaTimePatternProperties {

    private String dateTime = "yyyy-MM-dd HH:mm:ss";
    private String date = "yyyy-MM-dd";
    private String yearMonth = "yyyy-MM";
    private String monthDay = "MM-dd";
    private String time = "HH:mm:ss";

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DateTimeFormatter dateTimeFormatter() {
        return formatter(dateTime);
    }

    public DateTimeFormatter timeFormatter() {
        return formatter(time);
    }

    public DateTimeFormatter yearMonthFormatter(){
        return formatter(yearMonth);
    }

    public DateTimeFormatter monthDayFormatter(){
        return formatter(monthDay);
    }

    public DateTimeFormatter dateFormatter() {
        return formatter(date);
    }

    private DateTimeFormatter formatter(String pattern) {
        if (pattern == null || pattern.trim().length() == 0) {
            return null;
        }
        return DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String toString() {
        return "Java8TimePatternProperties{" +
                "dateTime='" + dateTime + '\'' +
                ", date='" + date + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", monthDay='" + monthDay + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
