package com.aihulk.tech.resource.entity;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DecisionUnit
 * @Description <p>
 * 决策单元会将所有规则集组织成一个图，图中的每个顶点就是规则集，每条边就是是否能到达下一个顶点的条件
 * <p/>
 * @Author yibozhang
 * @Date 2019/5/1 12:25
 * @Version 1.0
 */
@Data
public class DecisionUnit extends BaseResource {

    private RuleSet ruleSet;

    private Map<RuleSet, List<ConditionEdge>> conditions = Maps.newHashMap();

    @Data
    public static class ConditionEdge {

        private RuleSet target;

        //满足该条件才表示target可达
        private Express express;

        /**
         * 测试start->target是否连通
         *
         * @return
         */
        public boolean connected() {
            return express.eval();
        }
    }

}
