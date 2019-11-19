package com.aihulk.tech.core.logic;

import com.aihulk.tech.common.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: ExpressHelper
 * @projectName aihulk
 * @description: ExpressHelper
 * @date 2019-07-0119:11
 */
public class ExpressHelper {

    private static final String AND_LOGIC_KEY = "and";

    private static final String OR_LOGIC_KEY = "or";

    public static Express parse(String logicStr) {
        Map map = JsonUtil.parseObject(logicStr, Map.class);
        return parse(map);
    }

    public static Express parse(Map<String, Object> map) {
        if (map.containsKey(AND_LOGIC_KEY)) {
            List<Map<String, Object>> logicsMap = (List<Map<String, Object>>) map.get(AND_LOGIC_KEY);
            List<Express> expresses = logicsMap.stream().map(ExpressHelper::parse).collect(Collectors.toList());
            return new AndExpress(expresses);
        } else if (map.containsKey(OR_LOGIC_KEY)) {
            List<Map<String, Object>> logicsMap = (List<Map<String, Object>>) map.get(OR_LOGIC_KEY);
            List<Express> expresses = logicsMap.stream().map(ExpressHelper::parse).collect(Collectors.toList());
            return new OrExpress(expresses);
        } else {
            SimpleExpress simpleExpress = new SimpleExpress();
            Object src = map.get(SimpleExpress.EXPRESS_KEYWORD_SRC);
            String op = (String) map.get(SimpleExpress.EXPRESS_KEYWORD_OP);
            Object dest = map.get(SimpleExpress.EXPRESS_KEYWORD_TARGET);
            simpleExpress.setSrc(src);
            simpleExpress.setOp(Operation.parse(op));
            simpleExpress.setTarget(dest);
            return simpleExpress;
        }
    }
}
