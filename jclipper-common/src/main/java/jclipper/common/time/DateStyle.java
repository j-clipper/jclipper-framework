package jclipper.common.time;

import java.util.List;

import static jclipper.common.time.Formatter.*;

/**
 * 日期格式
 * <strong>
 * 将最常用的转换格式放在最前面，可以提高{@link DateHelper#style(String)}的 转换效率
 * </strong>
 *
 * @author wf2311
 */
public enum DateStyle {

    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", Type.DATETIME, false),

    SLASH_YYYY_MM_DD("yyyy/MM/dd", Type.DATE, false),

    YYYY_MM_DD("yyyy-MM-dd", Type.DATE, false),

    SLASH_YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss", Type.DATETIME, false),

    YYYY_MM("yyyy-MM", Type.YEAR_MONTH, false),

    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", Type.DATETIME, false),

    YYYY_MM_DD_HH("yyyy-MM-dd HH", Type.DATETIME, false),

    YYYYMM("yyyyMM", Type.YEAR_MONTH, false, true),

    YYYYMMDD("yyyyMMdd", Type.DATE, false, true),

    YYYYMMDDHH("yyyyMMddHH", Type.DATETIME, false, true),

    YYYYMMDDHHMM("yyyyMMddHHmm", Type.DATETIME, false, true),

    YYYYMMDDHHMMSS("yyyyMMddHHmmss", Type.DATETIME, false, true),

    SLASH_YYYY_MM("yyyy/MM", Type.YEAR_MONTH, false),

    SLASH_YYYY_MM_DD_HH("yyyy/MM/dd HH", Type.DATETIME, false),

    SLASH_YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm", Type.DATETIME, false),

    CN_YYYY_MM("yyyy年MM月", Type.YEAR_MONTH, false),

    CN_YYYY_MM_DD("yyyy年MM月dd日", Type.DATE, false),

    CN_YYYY_MM_DD_HH("yyyy年MM月dd日 HH点", Type.DATETIME, false),

    CN_YYYY_MM_DD_HH_MM("yyyy年MM月dd日 HH:mm", Type.DATETIME, false),

    CN_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd日 HH:mm:ss", Type.DATETIME, false),

    CN2_YYYY_MM_DD("yyyy年MM月dd号", Type.DATE, false),

    CN2_YYYY_MM_DD_HH("yyyy年MM月dd号 HH点", Type.DATETIME, false),

    CN2_YYYY_MM_DD_HH_MM("yyyy年MM月dd号 HH:mm", Type.DATETIME, false),

    CN2_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd号 HH:mm:ss", Type.DATETIME, false),

    CN2_2_YYYY_MM_DD_HH("yyyy年MM月dd号 HH时", Type.DATETIME, false),

    CN2_2_YYYY_MM_DD_HH_MM("yyyy年MM月dd号 HH点mm分", Type.DATETIME, false),

    CN2_2_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd号 HH点mm分ss秒", Type.DATETIME, false),

    CN2_3_YYYY_MM_DD_HH_MM("yyyy年MM月dd号 HH时mm分", Type.DATETIME, false),

    CN2_3_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd号 HH时mm分ss秒", Type.DATETIME, false),

    CN_2_YYYY_MM_DD_HH("yyyy年MM月dd日 HH时", Type.DATETIME, false),

    CN_2_YYYY_MM_DD_HH_MM("yyyy年MM月dd日 HH点mm分", Type.DATETIME, false),

    CN_2_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd日 HH点mm分ss秒", Type.DATETIME, false),

    CN_3_YYYY_MM_DD_HH_MM("yyyy年MM月dd日 HH时mm分", Type.DATETIME, false),

    CN_3_YYYY_MM_DD_HH_MM_SS("yyyy年MM月dd日 HH时mm分ss秒", Type.DATETIME, false),

    MM_DD("MM-dd", Type.MONTH_DAY, false),

    MM_DD_HH_MM("MM-dd HH:mm", Type.DATETIME, true),

    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", Type.DATETIME, true),

    HH_MM("HH:mm", Type.TIME, true),

    HH_MM_SS("HH:mm:ss", Type.TIME, false),

    SLASH_MM_DD("MM/dd", Type.MONTH_DAY, false),

    SLASH_MM_DD_HH_MM("MM/dd HH:mm", Type.DATETIME, true),

    SLASH_MM_DD_HH_MM_SS("MM/dd HH:mm:ss", Type.DATETIME, true),

    CN_HH_MM_SS("HH时mm分ss秒", Type.TIME, false),

    CN_HH_MM("HH时mm分", Type.TIME, false),

    CN_MM_DD("MM月dd日", Type.MONTH_DAY, false),

    CN2_MM_DD("MM月dd号", Type.MONTH_DAY, false),

    CN_MM_DD_HH_MM("MM月dd日 HH:mm", Type.DATETIME, true),

    CN_2_MM_DD_HH_MM("MM月dd日 HH点mm分", Type.DATETIME, true),

    CN_3_MM_DD_HH_MM("MM月dd日 HH时mm分", Type.DATETIME, true),

    CN2_MM_DD_HH_MM("MM月dd号 HH:mm", Type.DATETIME, true),

    CN2_2_MM_DD_HH_MM("MM月dd号 HH点mm分", Type.DATETIME, true),

    CN2_3_MM_DD_HH_MM("MM月dd号 HH时mm分", Type.DATETIME, true),

    CN_MM_DD_HH_MM_SS("MM月dd日 HH:mm:ss", Type.DATETIME, true),

    CN_2_MM_DD_HH_MM_SS("MM月dd日 HH点mm分ss秒", Type.DATETIME, true),

    CN_3_MM_DD_HH_MM_SS("MM月dd日 HH时mm分ss秒", Type.DATETIME, true),

    CN2_MM_DD_HH_MM_SS("MM月dd号 HH:mm:ss", Type.DATETIME, true),

    CN2_2_MM_DD_HH_MM_SS("MM月dd号 HH点mm分ss秒", Type.DATETIME, true),

    CN2_3_MM_DD_HH_MM_SS("MM月dd号 HH时mm分ss秒", Type.DATETIME, true),;

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

    private final Formatter formatter;

    DateStyle(String value, Type type, boolean formatOnly) {
        this.formatter = new Formatter(value, type, formatOnly);
        this.value = formatter.value();
        this.formatOnly = formatter.formatOnly();
        this.type = formatter.type();
        this.regex = formatter.regex();
        this.strictRegex = formatter.strictRegex();
        this.lengthStrict = formatter.lengthStrict();
        this.contains = formatter.contains();
    }

    DateStyle(String value, Type type, boolean formatOnly, boolean lengthStrict) {
        this.formatter = new Formatter(value, type, formatOnly, lengthStrict);
        this.value = formatter.value();
        this.formatOnly = formatter.formatOnly();
        this.type = formatter.type();
        this.regex = formatter.regex();
        this.strictRegex = formatter.strictRegex();
        this.lengthStrict = formatter.lengthStrict();
        this.contains = formatter.contains();
    }

    public final String value() {
        return value;
    }

    public final boolean showOnly() {
        return formatOnly;
    }

    public final Type type() {
        return type;
    }

    public final String regex() {
        return regex;
    }

    public boolean lengthStrict() {
        return lengthStrict;
    }

    public final String strictRegex() {
        return strictRegex;
    }

    public final List<Part> contains() {
        return this.contains;
    }

    public final Formatter formatter() {
        return this.formatter;
    }

}