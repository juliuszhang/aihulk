package com.aihulk.tech.resource.entity;

import lombok.Data;

/**
 * @ClassName Feature
 * @Description 特征
 * @Author yibozhang
 * @Date 2019/5/1 11:51
 * @Version 1.0
 */
@Data
public class Feature extends BaseResource {

    private String code;

    private Object val;

}
