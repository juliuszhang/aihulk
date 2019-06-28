package com.aihulk.tech.common.component;

/**
 * @author zhangyibo
 * @title: OperatorHolder
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2810:40
 */
public class OperatorHolder {

    private static final ThreadLocal<String> OPERATOR_TL = new ThreadLocal<>();

    public static String getOperator() {
        return OPERATOR_TL.get();
    }

    public static void setOpertor(String operator) {
        OPERATOR_TL.set(operator);
    }
}
