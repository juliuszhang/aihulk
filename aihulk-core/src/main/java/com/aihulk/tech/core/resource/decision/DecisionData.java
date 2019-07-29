package com.aihulk.tech.core.resource.decision;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: DecisionData
 * @projectName aihulk
 * @description: 决策数据
 * 数据结构中包含一个map key表示数据名称 value为数据对象
 * 默认进件数据key为apply 如果有其他数据源 再继续往里面追加
 * @date 2019-07-29 14:03
 */
public class DecisionData {

    public static final String APPLY_DATA_KEY = "apply";

    @Getter
    private final Map<String, Object> data;

    public DecisionData() {
        data = Maps.newHashMap();
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

}
