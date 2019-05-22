package com.aihulk.tech.resource.entity;

import lombok.Data;

/**
 * @ClassName BaseEntity
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 11:51
 * @Version 1.0
 */
@Data
public abstract class BaseEntity {

    private Integer id;

    private String createTime;

    private String updateTime;

    private String operator;

}
