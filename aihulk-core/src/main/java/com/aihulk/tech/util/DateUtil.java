package com.aihulk.tech.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:42
 * @Description: 日期工具类
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String getCurDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

}
