package com.aihulk.tech.core.logic;

import com.aihulk.tech.core.context.DecisionContext;
import com.aihulk.tech.core.resource.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:49
 * @Description: 定义规则中的表达式
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Express extends BaseEntity implements Logic {

    public static final String FEATURE_KEYWORD = "$feature_";

    public static final String EXPRESS_KEYWORD_SRC = "src";
    public static final String EXPRESS_KEYWORD_OP = "op";
    public static final String EXPRESS_KEYWORD_TARGET = "dest";

    private Object src;

    private Operation op;

    private Object target;

    public Express() {
    }

    @Override
    public Boolean eval() {
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
        return DecisionContext.getFactMap(featureId);
    }



}
