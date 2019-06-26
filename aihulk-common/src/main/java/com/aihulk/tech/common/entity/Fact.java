package com.aihulk.tech.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhangyibo
 * @title: Fact
 * @projectName aihulk
 * @description: 特征
 * @date 2019-06-0314:18
 */
@Data
@Table(name = "fact")
@EqualsAndHashCode(callSuper = true)
public class Fact extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

}
