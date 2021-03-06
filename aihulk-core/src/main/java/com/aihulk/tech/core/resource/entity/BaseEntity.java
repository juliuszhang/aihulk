package com.aihulk.tech.core.resource.entity;

import lombok.Data;

/**
 * @ClassName BaseEntity
 * @Description BaseEntity
 * @Author yibozhang
 * @Date 2019/5/1 11:51
 * @Version 1.0
 */
@Data
public abstract class BaseEntity {

    protected Integer id;

    protected String createTime;

    protected String updateTime;

}
