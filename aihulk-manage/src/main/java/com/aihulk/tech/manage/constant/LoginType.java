package com.aihulk.tech.manage.constant;

import com.aihulk.tech.common.util.RegexUtil;

/**
 * @author zhangyibo
 * @title: LoginType
 * @projectName aihulk
 * @description: loginType
 * @date 2019-06-2811:54
 */
public enum LoginType {
    EMAIL,
    MOBILE,
    USERNAME;

    public static LoginType parse(String account) {
        if (RegexUtil.isEmail(account)) {
            return EMAIL;
        } else if (RegexUtil.isMobile(account)) {
            return MOBILE;
        } else {
            return USERNAME;
        }
    }

}
