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

    //support python and js
    public static final String CODE_TYPE_JS = "js";
    public static final String CODE_TYPE_PY = "py";

    //result type enum
    public static final String RESULT_TYPE_NUMBER = "NUMBER";
    public static final String RESULT_TYPE_STRING = "STRING";
    public static final String RESULT_TYPE_OBJECT = "OBJECT";
    public static final String RESULT_TYPE_ARRAY_NUMBER = "ARRAY_NUMBER";
    public static final String RESULT_TYPE_ARRAY_STRING = "ARRAY_STRING";
    public static final String RESULT_TYPE_ARRAY_OBJECT = "ARRAY_OBJECT";

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "business_id")
    private Integer businessId;

    @Column(name = "code")
    private String code;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "result_type")
    private String resultType;

}
