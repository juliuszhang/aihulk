package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.ScriptCodeType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    public static final String CODE_TYPE_JS = ScriptCodeType.JS.getName();
    public static final String CODE_TYPE_PY = ScriptCodeType.PY.getName();
    public static final String CODE_TYPE_BASIC = ScriptCodeType.BASIC.getName();

    //result type enum
    public static final String RESULT_TYPE_NUMBER = DataType.NUMBER.getName();
    public static final String RESULT_TYPE_STRING = DataType.STRING.getName();
    public static final String RESULT_TYPE_OBJECT = DataType.OBJECT.getName();
    public static final String RESULT_TYPE_ARRAY_NUMBER = DataType.ARRAY_NUMBER.getName();
    public static final String RESULT_TYPE_ARRAY_STRING = DataType.ARRAY_STRING.getName();
    public static final String RESULT_TYPE_ARRAY_OBJECT = DataType.ARRAY_OBJECT.getName();


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
