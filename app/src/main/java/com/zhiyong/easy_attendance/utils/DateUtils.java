package com.zhiyong.easy_attendance.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final String PATTERN_REPORT = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS_U = "yyyy-MM-dd HH:mm:ss";

    public static String formatDateTime(long time, String pattern, String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(new Date(time * 1000L));
    }

    public static Date getCurrentTime(String timezone) {
        return Calendar.getInstance(TimeZone.getTimeZone(timezone)).getTime();
    }
}
