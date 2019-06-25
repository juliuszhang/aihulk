package com.aihulk.tech.core.resource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName FactRelation
 * @Description 事实之间的引用关系
 * @Author yibozhang
 * @Date 2019/6/5 11:51
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FactRelation extends BaseEntity {

    private Integer featureId;

    private Integer refFeatureId;

}
