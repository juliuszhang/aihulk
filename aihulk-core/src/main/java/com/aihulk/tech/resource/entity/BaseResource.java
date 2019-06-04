package com.aihulk.tech.resource.entity;

import lombok.Data;

/**
 * @ClassName BaseResource
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:26
 * @Version 1.0
 */
@Data
public abstract class BaseResource extends BaseEntity {

    private String name;

    private String nameEn;

    private String desc;

}
