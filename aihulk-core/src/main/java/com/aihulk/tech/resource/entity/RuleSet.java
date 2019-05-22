package com.aihulk.tech.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName RuleSet
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:23
 * @Version 1.0
 */
@Data
public class RuleSet extends BaseResource {

    private List<Rule> rules = Lists.newArrayList();

}
