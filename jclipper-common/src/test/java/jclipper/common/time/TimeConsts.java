package jclipper.common.time;

import jclipper.common.time.DateStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wf2311
 */
public class TimeConsts {
    public final static Map<DateStyle, String> map = new HashMap<>();

    static {
        map.put(DateStyle.YYYY_MM_DD_HH_MM_SS, "2017-04-21 13:27:59");
        map.put(DateStyle.SLASH_YYYY_MM_DD, "2017/04/21");
        map.put(DateStyle.YYYY_MM_DD, "2017-04-21");
        map.put(DateStyle.SLASH_YYYY_MM_DD_HH_MM_SS, "2017/04/21 13:27:59");
        map.put(DateStyle.YYYY_MM, "2017-04");
        map.put(DateStyle.YYYY_MM_DD_HH_MM, "2017-04-21 13:27");
        map.put(DateStyle.YYYY_MM_DD_HH, "2017-04-21 13");
        map.put(DateStyle.YYYYMM, "201704");
        map.put(DateStyle.YYYYMMDD, "20170421");
        map.put(DateStyle.YYYYMMDDHH, "2017042113");
        map.put(DateStyle.YYYYMMDDHHMM, "201704211327");
        map.put(DateStyle.YYYYMMDDHHMMSS, "20170421132759");
        map.put(DateStyle.SLASH_YYYY_MM, "2017/04");
        map.put(DateStyle.SLASH_YYYY_MM_DD_HH, "2017/04/21 13");
        map.put(DateStyle.SLASH_YYYY_MM_DD_HH_MM, "2017/04/21 13:27");
        map.put(DateStyle.CN_HH_MM_SS, "13时27分59秒");
        map.put(DateStyle.CN_HH_MM, "13时27分");
        map.put(DateStyle.CN_YYYY_MM, "2017年04月");
        map.put(DateStyle.CN_YYYY_MM_DD, "2017年04月21日");
        map.put(DateStyle.CN2_YYYY_MM_DD, "2017年04月21号");
        map.put(DateStyle.CN_YYYY_MM_DD_HH, "2017年04月21日 13点");
        map.put(DateStyle.CN_2_YYYY_MM_DD_HH, "2017年04月21日 13时");
        map.put(DateStyle.CN2_YYYY_MM_DD_HH, "2017年04月21号 13点");
        map.put(DateStyle.CN2_2_YYYY_MM_DD_HH, "2017年04月21号 13时");
        map.put(DateStyle.CN_YYYY_MM_DD_HH_MM, "2017年04月21日 13:27");
        map.put(DateStyle.CN_2_YYYY_MM_DD_HH_MM, "2017年04月21日 13点27分");
        map.put(DateStyle.CN_3_YYYY_MM_DD_HH_MM, "2017年04月21日 13时27分");
        map.put(DateStyle.CN2_YYYY_MM_DD_HH_MM, "2017年04月21号 13:27");
        map.put(DateStyle.CN2_2_YYYY_MM_DD_HH_MM, "2017年04月21号 13点27分");
        map.put(DateStyle.CN2_3_YYYY_MM_DD_HH_MM, "2017年04月21号 13时27分");
        map.put(DateStyle.CN_YYYY_MM_DD_HH_MM_SS, "2017年04月21日 13:27:59");
        map.put(DateStyle.CN_2_YYYY_MM_DD_HH_MM_SS, "2017年04月21日 13点27分59秒");
        map.put(DateStyle.CN_3_YYYY_MM_DD_HH_MM_SS, "2017年04月21日 13时27分59秒");
        map.put(DateStyle.CN2_YYYY_MM_DD_HH_MM_SS, "2017年04月21号 13:27:59");
        map.put(DateStyle.CN2_2_YYYY_MM_DD_HH_MM_SS, "2017年04月21号 13点27分59秒");
        map.put(DateStyle.CN2_3_YYYY_MM_DD_HH_MM_SS, "2017年04月21号 13时27分59秒");
        map.put(DateStyle.MM_DD, "04-21");
        map.put(DateStyle.HH_MM_SS, "13:27:59");
        map.put(DateStyle.SLASH_MM_DD, "04/21");
        map.put(DateStyle.CN_MM_DD, "04月21日");
        map.put(DateStyle.CN2_MM_DD, "04月21号");
    }
}
