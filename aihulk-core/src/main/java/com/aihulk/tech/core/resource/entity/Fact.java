package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.ScriptCodeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName Fact
 * @Description 事实
 * @Author yibozhang
 * @Date 2019/5/1 11:51
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Fact extends BaseResource {

    //引用事实在code中的特殊标识
    public static final String REF_FACT_SIGN = "$ref_fact_";

    public static final String FACT_SIGN = "$fact_";

    private Integer unitId;

    private String code;

    private ScriptCodeType codeType;

    private DataType resultType;
}
