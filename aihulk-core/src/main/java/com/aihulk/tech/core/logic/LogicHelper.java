package com.aihulk.tech.core.logic;

import com.aihulk.tech.core.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: LogicHelper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-0119:11
 */
public class LogicHelper {

    private static final String AND_LOGIC_KEY = "and";

    private static final String OR_LOGIC_KEY = "or";

    public static Logic parse(String logicStr) {
        Map map = JsonUtil.parseObject(logicStr, Map.class);
        return parse(map);
    }

    public static Logic parse(Map<String, Object> map) {
        if (map.containsKey(AND_LOGIC_KEY)) {
            List<Map<String, Object>> logicsMap = (List<Map<String, Object>>) map.get(AND_LOGIC_KEY);
            List<Logic> logics = logicsMap.stream().map(LogicHelper::parse).collect(Collectors.toList());
            AndLogic andLogic = new AndLogic(logics);
            return andLogic;
        } else if (map.containsKey(OR_LOGIC_KEY)) {
            List<Map<String, Object>> logicsMap = (List<Map<String, Object>>) map.get(OR_LOGIC_KEY);
            List<Logic> logics = logicsMap.stream().map(LogicHelper::parse).collect(Collectors.toList());
            OrLogic orLogic = new OrLogic(logics);
            return orLogic;
        } else {
            Express express = new Express();
            Object src = map.get(Express.EXPRESS_KEYWORD_SRC);
            String op = (String) map.get(Express.EXPRESS_KEYWORD_OP);
            Object dest = map.get(Express.EXPRESS_KEYWORD_TARGET);
            express.setSrc(src);
            express.setOp(Operation.parse(op));
            express.setTarget(dest);
            return express;
        }
    }
}
