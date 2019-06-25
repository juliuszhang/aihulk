package com.aihulk.tech.core.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Resource
 * @Description biz下最大的资源对象
 * @Author yibozhang
 * @Date 2019/5/1 11:50
 * @Version 1.0
 */
@Data
public class Resource {

    private List<DecisionChain> decisionChains = Lists.newArrayList();

}
