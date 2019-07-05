package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UnitType {
    EXECUTE_UNIT(0),
    EXECUTE_UNIT_GROUP(1);

    @Getter
    private Integer val;

}
