package com.aihulk.tech.common.util;

import java.util.regex.Pattern;

/**
 * @author zhangyibo
 * @title: RegexUtil
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2811:29
 */
public class RegexUtil {

    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");
        return pattern.matcher(str).matches();
    }

    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$");
        return pattern.matcher(str).matches();
    }

}
