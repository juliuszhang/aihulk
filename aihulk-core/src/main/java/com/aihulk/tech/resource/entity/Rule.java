package com.aihulk.tech.resource.entity;

import com.aihulk.tech.action.Action;
import com.aihulk.tech.logic.EvalAble;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Rule
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:20
 * @Version 1.0
 */
@Data
public class Rule extends BaseResource implements EvalAble {

    private Express express;

    private Action action;

    private List<Feature> features = Lists.newArrayList();

    @Override
    public boolean eval() {
        return express.eval();
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
