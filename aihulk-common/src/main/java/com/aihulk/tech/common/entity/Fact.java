package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: Fact
 * @projectName aihulk
 * @description: 特征
 * @date 2019-06-0314:18
 */
@Data
@TableName(value = "fact")
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

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "code")
    private String code;

    @TableField(value = "code_type")
    private String codeType;

    @TableField(value = "result_type")
    private String resultType;

}
