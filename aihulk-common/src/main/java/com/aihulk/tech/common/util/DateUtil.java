package com.aihulk.tech.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:42
 * @Description: 日期工具类
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getCurDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String getCurDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String str, DateTimeFormatter formatter) {
        return LocalDateTime.parse(str, formatter);
    }

    public static LocalDate parseDate(String str, DateTimeFormatter formatter) {
        return LocalDate.parse(str, formatter);
    }

    public static String format(TemporalAccessor temporalAccessor, DateTimeFormatter formatter) {
        return formatter.format(temporalAccessor);
    }

    public static String formatDate(TemporalAccessor temporalAccessor) {
        return format(temporalAccessor, DATE_FORMATTER);
    }

    public static String formatDateTime(TemporalAccessor temporalAccessor) {
        return format(temporalAccessor, DATE_TIME_FORMATTER);
    }
}
