package com.aihulk.tech.core.action;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.MergeStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName OutPut
 * @Description 变量输出动作
 * @Author yibozhang
 * @Date 2019/6/5 11:51
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class OutPut extends Action {

    private String key;

    private String objExp;

    private DataType dataType;

    private MergeStrategy unitMergeStrategy;

    private MergeStrategy chainMergeStrategy;


}
