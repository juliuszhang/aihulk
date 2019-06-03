package com.aihulk.tech.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhangyibo
 * @title: RuleSetMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "operator")
    private String operator;

}
