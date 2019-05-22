package com.aihulk.tech.action;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JumpToRuleSet implements Action {

    private Integer RuleSetId;

    public JumpToRuleSet(Integer ruleSetId) {
        RuleSetId = ruleSetId;
    }
}
