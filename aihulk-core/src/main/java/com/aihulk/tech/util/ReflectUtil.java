package com.aihulk.tech.util;

/**
 * @ClassName ReflectUtil
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 21:42
 * @Version 1.0
 */
public class ReflectUtil {

    private static final String SET_METHOD_PREFIX = "set";
    private static final String GET_METHOD_PREFIX = "get";

    public static String getSetMethodName(String fieldName) {
        return SET_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
