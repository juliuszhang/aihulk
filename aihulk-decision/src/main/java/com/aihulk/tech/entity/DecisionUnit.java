package com.aihulk.tech.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhangyibo
 * @title: RuleSetMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
@Table(name = "decision_unit")
@Data
public class DecisionUnit extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;


}
