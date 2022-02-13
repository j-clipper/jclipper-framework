package jclipper.common.time;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wf2311
 */
public class Formatter {
    /**
     * 格式
     */
    private final String value;

    /**
     * 是否只用于转换格式化
     */
    private final boolean formatOnly;

    /**
     * 日期类别
     */
    private final Type type;

    /**
     * 匹配规则
     * <p>
     * 只对时间单位的长度限制进行匹配，不检验取值范围有效性。例如
     * 2017-04-01可以匹配{@link DateStyle#YYYYMMDD}
     * 2017-04-41也可以匹配{@link DateStyle#YYYYMMDD}
     * </p>
     *
     * @see Regex
     */
    private final String regex;

    /**
     * 严格的匹配规则
     * <p>
     * 同时对时间单位的长度限制和取值范围进行匹配。例如
     * 2017-04-01可以匹配{@link DateStyle#YYYYMMDD}
     * 2017-04-41无法匹配{@link DateStyle#YYYYMMDD}
     * </p>
     *
     * @see StrictRegex
     */
    private final String strictRegex;

    /**
     * 长度是否严格
     * <p>
     * 此参数用于时间单元没有分隔符的匹配，例如：
     * {@link DateStyle#YYYYMMDD}的<code>strictRegex=true</code>,其值的长度必须为8，20170401匹配成功，而201741无法匹配；
     * {@link DateStyle#YYYY_MM_DD}的<code>strictRegex=false</code>,其值的长度不固定，2017-04-01匹配成功，2017-4-1也可以匹配成功；
     * </p>
     */
    private final boolean lengthStrict;


    private final List<Part> contains;

    public Formatter(String value) {
        this(value, Type.DATETIME);
    }

    public Formatter(String value, Type type) {
        this(value, type, false);
    }

    public Formatter(String value, boolean formatOnly) {
        this(value, Type.DATETIME, formatOnly);
    }

    public Formatter(String value, Type type, boolean formatOnly) {
        this(value, type, formatOnly, false);
    }

    public Formatter(String value, Type type, boolean formatOnly, boolean lengthStrict) {
        this.value = value;
        this.formatOnly = formatOnly;
        this.type = type;
        this.lengthStrict = lengthStrict;
        if (lengthStrict) {
            this.regex = regexNum(value.length());
        } else {
            this.regex = regex0();
        }
        this.strictRegex = strictRegex0();
        this.contains = contains0();
    }

    /**
     * 获取 value 的值
     *
     * @see Formatter#value
     */
    public String value() {
        return value;
    }

    /**
     * 获取 formatOnly 的值
     *
     * @see Formatter#formatOnly
     */
    public boolean formatOnly() {
        return formatOnly;
    }

    /**
     * 获取 type 的值
     *
     * @see Formatter#type
     */
    public Type type() {
        return type;
    }

    /**
     * 获取 regex 的值
     *
     * @see Formatter#regex
     */
    public String regex() {
        return regex;
    }

    /**
     * 获取 strictRegex 的值
     *
     * @see Formatter#strictRegex
     */
    public String strictRegex() {
        return strictRegex;
    }

    /**
     * 获取 lengthStrict 的值
     *
     * @see Formatter#lengthStrict
     */
    public boolean lengthStrict() {
        return lengthStrict;
    }

    /**
     * 获取 contains 的值
     *
     * @see Formatter#contains
     */
    public List<Part> contains() {
        return contains;
    }

    private String regex0() {
        String r = value
                .replace(Part.YEAR.value, Regex.YEAR.value)
                .replace(Part.MONTH.value, Regex.MONTH.value)
                .replace(Part.DAY.value, Regex.DAY.value)
                .replace(Part.HOUR.value, Regex.HOUR.value)
                .replace(Part.MINUTE.value, Regex.MINUTE.value)
                .replace(Part.SECOND.value, Regex.SECOND.value);
        return fillRegex(r);
    }

    private String strictRegex0() {
        String r = value
                .replace(Part.YEAR.value, StrictRegex.YEAR.value)
                .replace(Part.MONTH.value, StrictRegex.MONTH.value)
                .replace(Part.DAY.value, StrictRegex.DAY.value)
                .replace(Part.HOUR.value, StrictRegex.HOUR.value)
                .replace(Part.MINUTE.value, StrictRegex.MINUTE.value)
                .replace(Part.SECOND.value, StrictRegex.SECOND.value);
        return fillRegex(r);
    }

    private List<Part> contains0() {
        List<Part> list = new ArrayList<>();
        Arrays.stream(value.split("")).distinct()
                .forEach(s -> {
                    Part part = Part.find(s);
                    if (part != null && !list.contains(part)) {
                        list.add(part);
                    }
                });
        return list;
    }

    private static String regexNum(int size) {
        return fillRegex("\\d{" + size + "}");
    }

    private static String fillRegex(String regex) {
        return "^" + regex + "$";
    }

    /**
     * 时间单元匹配规则
     * <p>
     * 只对时间单位的长度限制进行匹配，不检验取值范围有效性.
     * 年：4位数字
     * 月：1~2位数字
     * 日：1~2位数字
     * 时：1~2位数字
     * 分：1~2位数字
     * 秒：1~2位数字
     * </p>
     */
    protected enum Regex {
        YEAR("(\\d{4})"),
        MONTH("(\\d{1,2})"),
        DAY("(\\d{1,2})"),
        HOUR("(\\d{1,2})"),
        MINUTE("(\\d{1,2})"),
        SECOND("(\\d{1,2})");

        private final String value;

        Regex(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    /**
     * 严格的时间单元匹配规则
     * <p>
     * 同时对时间单位的长度限制和取值范围进行匹配
     * 年：4位数字
     * 月：1~2位数字,取值1~12
     * 日：1~2位数字,取值1~31
     * 时：1~2位数字,取值0~24
     * 分：1~2位数字,取值0~59
     * 秒：1~2位数字,取值0~59
     * </p>
     */
    protected enum StrictRegex {
        YEAR("(\\d{4})"),
        MONTH("(0?[1-9]|1[012])"),
        DAY("(0?[1-9]|[12][0-9]|3[01])"),
        HOUR("([0-9]|[01][0-9]|2[0-4])"),
        MINUTE("([0-9]|[0-5][0-9])"),
        SECOND("([0-9]|[0-5][0-9])");

        private final String value;

        StrictRegex(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    protected enum Part {
        YEAR("yyyy"),
        MONTH("MM"),
        DAY("dd"),
        HOUR("HH"),
        MINUTE("mm"),
        SECOND("ss");

        private final String value;

        Part(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static Part find(String value) {
            return Arrays.stream(Part.values())
                    .filter(t -> t.value.contains(value))
                    .findAny().orElse(null);
        }
    }

    protected enum Type {
        DATETIME(LocalDateTime.class),
        DATE(LocalDate.class),
        TIME(LocalTime.class),
        YEAR_MONTH(YearMonth.class),
        MONTH_DAY(MonthDay.class);

        private Class<?> value;

        Type(Class<?> value) {
            this.value = value;
        }

        public Class<?> value() {
            return value;
        }

        public static Type find(Object value) {
            return Arrays.stream(Type.values())
                    .filter(t -> t.value.equals(value))
                    .findAny().orElse(null);
        }
    }
}
