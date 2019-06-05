package com.aihulk.tech.context;


import java.util.Collections;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:51
 * @Description:
 */
public class DecisionContext {

    private static final ThreadLocal<Map<Integer, Object>> FEATURE_MAP
            = ThreadLocal.withInitial(() -> Collections.EMPTY_MAP);

    private static final ThreadLocal<String> DATA = new ThreadLocal<>();


    public static void setFeatureMap(Map<Integer, Object> featureMap) {
        if (featureMap == null) return;
        FEATURE_MAP.set(featureMap);
    }

    public static Object getFeature(Integer featureId) {
        return FEATURE_MAP.get().get(featureId);
    }

    public static String getData() {
        return DATA.get();
    }

    public static void setData(String data) {
        DATA.set(data);
    }

}
