package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.MergeStrategy;
import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.entity.entity.Variable;
import com.aihulk.tech.entity.mapper.ActionMapper;
import com.aihulk.tech.entity.mapper.VariableMapper;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * @author zhangyibo
 * @title: ActionResourceLoaderTest
 * @projectName aihulk
 * @description: ActionResourceLoaderTest
 * @date 2019-07-0516:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ActionResourceLoaderTest {

    @Autowired
    private ActionResourceLoader actionResourceLoader = new ActionResourceLoader();

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private VariableMapper variableMapper;

    private Integer var1Id;

    private Integer var2Id;

    private Integer action1Id;

    private Integer action2Id;

    @Before
    public void setUp() throws Exception {
        insertAction();
        insertVarible();
        insertActionVariableRelation();
    }

    private void insertAction() {
        com.aihulk.tech.entity.entity.Action action = new com.aihulk.tech.entity.entity.Action();
        action.setName("输出测试变量");
        action.setNameEn("output test variable");
        action.setBusinessId(1);
        action.setType(com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT);
        actionMapper.insert(action);
        action1Id = action.getId();

        com.aihulk.tech.entity.entity.Action action2 = new com.aihulk.tech.entity.entity.Action();
        action2.setName("输出测试变量2");
        action2.setNameEn("output test variable2");
        action2.setBusinessId(1);
        action2.setType(com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT);
        actionMapper.insert(action2);
        action2Id = action2.getId();
    }

    public void insertVarible() {
        Variable variable = new Variable();
        variable.setName("测试变量1");
        variable.setNameEn("test variable 1");
        variable.setMergeStrategy(Variable.MERGE_STRATEGY_CHAIN_FIRST & Variable.MERGE_STRATEGY_UNIT_ALL);
        variable.setBusinessId(1);
        variable.setType(Variable.TYPE_NUMBER);
        variableMapper.insert(variable);
        var1Id = variable.getId();

        Variable variable2 = new Variable();
        variable2.setName("测试变量2");
        variable2.setNameEn("test variable 2");
        variable2.setMergeStrategy(Variable.MERGE_STRATEGY_CHAIN_FIRST & Variable.MERGE_STRATEGY_UNIT_LAST);
        variable2.setBusinessId(1);
        variable2.setType(Variable.TYPE_STRING);
        variableMapper.insert(variable2);
        var2Id = variable2.getId();
    }

    private void insertActionVariableRelation() {

        Map<String, Object> varMap1 = Maps.newHashMap();
        varMap1.put("actionId", action1Id);
        varMap1.put("varId", var1Id);
        varMap1.put("value", 1);

        Map<String, Object> varMap2 = Maps.newHashMap();
        varMap2.put("actionId", action2Id);
        varMap2.put("varId", var2Id);
        varMap2.put("value", "aaa");

    }


    @Test
    public void loadResource() {
        Map<Integer, List<Action>> actionMap = actionResourceLoader.loadResource(1, "");
        List<Action> actions = actionMap.entrySet().stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList());
        assertTrue(actions.size() == 2);
        actions.sort(Comparator.comparing(Action::getId));
        Action action1 = actions.get(0);
        Action action2 = actions.get(1);

        assertTrue(action1 instanceof OutPut);
        assertTrue(action2 instanceof OutPut);

        OutPut outPut1 = (OutPut) action1;
        OutPut outPut2 = (OutPut) action2;

        assertTrue(outPut1.getKey().equals("test variable 1"));
        assertTrue(outPut2.getKey().equals("test variable 2"));

        assertTrue(outPut1.getObj().equals(1));
        assertTrue(outPut2.getObj().equals("aaa"));
        assertTrue(outPut1.getUnitMergeStrategy() == MergeStrategy.ALL);
        assertTrue(outPut2.getUnitMergeStrategy() == MergeStrategy.OVERWRITE);
        assertTrue(outPut1.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
        assertTrue(outPut2.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
    }

}
