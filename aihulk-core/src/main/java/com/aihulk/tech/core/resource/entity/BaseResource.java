package com.aihulk.tech.core.resource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName BaseResource
 * @Description BaseResource
 * @Author yibozhang
 * @Date 2019/5/1 12:26
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseResource extends BaseEntity {

    protected String name;

    protected String nameEn;

    protected String desc;

}
