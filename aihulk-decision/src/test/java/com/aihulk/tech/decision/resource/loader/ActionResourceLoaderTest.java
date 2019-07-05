package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.MergeStrategy;
import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.entity.entity.Variable;
import com.aihulk.tech.entity.mapper.ActionMapper;
import com.aihulk.tech.entity.mapper.VariableMapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author zhangyibo
 * @title: ActionResourceLoaderTest
 * @projectName aihulk
 * @description: ActionResourceLoaderTest
 * @date 2019-07-0516:07
 */
public class ActionResourceLoaderTest {

    private ActionResourceLoader actionResourceLoader = new ActionResourceLoader();

    private ActionMapper actionMapper = MybatisService.getMapper(ActionMapper.class);

    private VariableMapper variableMapper = MybatisService.getMapper(VariableMapper.class);

    private SqlSession sqlSession = MybatisService.getInstance().getSqlSession();

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
        MybatisService.addMapper(ActionVaribaleRelationMapper.class);
        ActionVaribaleRelationMapper mapper = MybatisService.getMapper(ActionVaribaleRelationMapper.class);
        Map<String, Object> varMap1 = Maps.newHashMap();
        varMap1.put("actionId", action1Id);
        varMap1.put("varId", var1Id);
        varMap1.put("value", 1);

        Map<String, Object> varMap2 = Maps.newHashMap();
        varMap2.put("actionId", action2Id);
        varMap2.put("varId", var2Id);
        varMap2.put("value", "aaa");

        mapper.insert(varMap1);
        mapper.insert(varMap2);
    }

    private interface ActionVaribaleRelationMapper {
        @Insert(value = "INSERT INTO action_variable_relation (action_id,variable_id,value,deleted) VALUES( #{actionId},#{varId},#{value},0);")
        void insert(Map<String, Object> map);
    }

    @Test
    public void loadResource() {
        List<Action> actions = actionResourceLoader.loadResource(1, "");
        assertTrue(actions.size() == 2);
        actions.sort(Comparator.comparing(Action::getId));
        Action action1 = actions.get(0);
        Action action2 = actions.get(1);

        assertTrue(action1 instanceof OutPut);
        assertTrue(action2 instanceof OutPut);

        OutPut outPut1 = (OutPut) action1;
        OutPut outPut2 = (OutPut) action2;

        assertTrue(outPut1.getKey().equals("测试变量1"));
        assertTrue(outPut2.getKey().equals("测试变量2"));

        assertTrue(outPut1.getObj().equals(1));
        assertTrue(outPut2.getObj().equals("aaa"));
        assertTrue(outPut1.getUnitMergeStrategy() == MergeStrategy.ALL);
        assertTrue(outPut2.getUnitMergeStrategy() == MergeStrategy.OVERWRITE);
        assertTrue(outPut1.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
        assertTrue(outPut2.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
    }

}
