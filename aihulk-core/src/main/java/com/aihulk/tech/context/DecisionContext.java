package com.aihulk.tech.context;


import com.aihulk.tech.resource.entity.Feature;

import java.util.Collections;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:51
 * @Description:
 */
public class DecisionContext {

    private static final ThreadLocal<Map<Integer, Feature>> FEATURE_MAP
            = ThreadLocal.withInitial(() -> Collections.EMPTY_MAP);

    public static void setFeatureMap(Map<Integer, Feature> featureMap) {
        if (featureMap == null) return;
        FEATURE_MAP.set(featureMap);
    }

    public static Feature getFeatureMap(Integer featureId) {
        return FEATURE_MAP.get().get(featureId);
    }

}
