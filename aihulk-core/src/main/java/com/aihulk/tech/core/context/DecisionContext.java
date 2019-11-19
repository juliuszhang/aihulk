package com.aihulk.tech.core.context;



import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:51
 * @Description:
 */
public class DecisionContext {

    private static final ThreadLocal<Map<Integer, Object>> FACT_MAP
            = ThreadLocal.withInitial(HashMap::new);

    /*进见数据以及数据源数据*/
    private static final ThreadLocal<Map<String, Object>> DATA = new ThreadLocal<>();

    public static void setFactMap(Map<Integer, Object> factMap) {
        if (factMap == null) return;
        FACT_MAP.set(factMap);
    }

    public static Object getFactMap(Integer factId) {
        return FACT_MAP.get().get(factId);
    }

    public static void addFact(Integer factId, Object val) {
        FACT_MAP.get().put(factId, val);
    }

    public static Map<String, Object> getData() {
        return DATA.get();
    }

    public static void setData(Map<String, Object> data) {
        DATA.set(data);
    }

}
