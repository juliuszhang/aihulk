package com.aihulk.tech.resource.entity;

import lombok.Data;

@Data
public class FeatureRelation extends BaseEntity {

    private Integer featureId;

    private Integer refFeatureId;

}
