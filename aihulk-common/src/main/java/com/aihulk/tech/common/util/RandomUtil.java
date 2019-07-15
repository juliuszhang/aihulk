package com.aihulk.tech.common.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhangyibo
 * @title: RandomUtil
 * @projectName aihulk
 * @description: RandomUtil
 * @date 2019-07-0916:12
 */
public class RandomUtil {

    public static String getNumber(int length, int start, int end, boolean insertZero) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int i = threadLocalRandom.nextInt(start, end);

        String result;
        if (insertZero) {
            result = String.format("%0" + length + "d", i);
        } else {
            result = String.valueOf(i);
        }

        return result;
    }
}
