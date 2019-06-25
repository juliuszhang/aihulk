package com.aihulk.tech.core.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName ExecuteUnitGroup
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:23
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExecuteUnitGroup extends BaseResource implements BasicUnit {

    private List<ExecuteUnit> executeUnits = Lists.newArrayList();

    @Override
    public UnitType getType() {
        return UnitType.EXECUTE_UNIT_GROUP;
    }
}
