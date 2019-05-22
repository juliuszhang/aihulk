package com.aihulk.tech.action;

import lombok.Getter;
import lombok.Setter;

/**
 * 跳转到同一规则集下的某条规则执行
 */
@Getter
@Setter
public class JumpToRule implements Action {

    private Integer ruleId;

}
