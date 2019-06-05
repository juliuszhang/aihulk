package com.aihulk.tech.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
@Table(name = "rule")
@Data
public class Unit extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "action")
    private String action;

    @Column(name = "express")
    private String express;

}
