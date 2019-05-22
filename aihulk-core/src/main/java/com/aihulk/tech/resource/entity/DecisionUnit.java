package com.aihulk.tech.resource.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DecisionUnit
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:25
 * @Version 1.0
 */
@Data
public class DecisionUnit extends BaseResource{

    private List<RuleSet> ruleSets;

}
