package com.aihulk.tech.resource.entity;

import com.aihulk.tech.context.DecisionContext;
import com.aihulk.tech.logic.EvalAble;
import lombok.Data;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:49
 * @Description: 定义规则中的表达式
 */
@Data
public class Express extends BaseEntity implements EvalAble {

    private static final String FEATURE_KEYWORD = "$feature_";

    private Object src;

    private Operation op;

    private Object target;

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
        Feature feature = DecisionContext.getFeatureMap(featureId);
        if (feature != null) {
            return feature.getVal();
        } else {
            return null;
        }
    }
}
