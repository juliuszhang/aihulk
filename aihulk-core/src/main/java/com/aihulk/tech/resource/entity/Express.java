package com.aihulk.tech.resource.entity;

import com.aihulk.tech.context.DecisionContext;
import com.aihulk.tech.logic.EvalAble;
import com.aihulk.tech.logic.Operation;
import com.aihulk.tech.util.JsonUtil;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:49
 * @Description: 定义规则中的表达式
 */
@Data
public class Express extends BaseEntity implements EvalAble {

    private static final String FEATURE_KEYWORD = "$feature_";

    private static final String EXPRESS_KEYWORD_SRC = "src";
    private static final String EXPRESS_KEYWORD_OP = "op";
    private static final String EXPRESS_KEYWORD_TARGET = "dest";

    private Object src;

    private Operation op;

    private Object target;

    public Express() {
    }

    public Express(String expressStr) {
        Express express = Express.parse(expressStr);
        this.src = express.src;
        this.op = express.op;
        this.target = express.target;
    }


    @Override
    public boolean eval() {
        if (src instanceof Express && target instanceof Express) {
            return op.eval(((Express) src).eval(), ((Express) target).eval());
        } else {
            if (isFeature(src)) {
                src = getFeatureVal(src);
            }
            if (isFeature(target)) {
                target = getFeatureVal(target);
            }
            return op.eval(src, target);
        }
    }

    private boolean isFeature(Object src) {
        return src instanceof String && ((String) src).contains(FEATURE_KEYWORD);
    }

    private Object getFeatureVal(Object src) {
        Integer featureId = Integer.parseInt(src.toString().replace(FEATURE_KEYWORD, ""));
        return DecisionContext.getFeature(featureId);
    }

    public static Express parse(String expressStr) {
        Express express = JsonUtil.parseObject(expressStr, Express.class);
        if (express.getSrc() instanceof LinkedHashMap
                && ((LinkedHashMap) express.getSrc()).containsKey(EXPRESS_KEYWORD_SRC)) {
            //recursive to parse express
            express.setSrc(parse(JsonUtil.toJsonString(express.getSrc())));
        }
        if (express.getTarget() instanceof LinkedHashMap
                && ((LinkedHashMap) express.getTarget()).containsKey(EXPRESS_KEYWORD_TARGET)) {
            //recursive to parse express
            express.setTarget(parse(JsonUtil.toJsonString(express.getTarget())));
        }
        return express;
    }


}
