package com.aihulk.tech.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Resource
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 11:50
 * @Version 1.0
 */
@Data
public class Resource {

    private List<DecisionChain> decisionChains = Lists.newArrayList();

}
