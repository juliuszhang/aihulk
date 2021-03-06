package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.entity.entity.*;
import com.aihulk.tech.entity.mapper.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangyibo
 * @title: AbstractTest
 * @projectName aihulk
 * @description: TODO
 * @date 2019-08-08 17:25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AbstractTest {


    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private VariableMapper variableMapper;

    @Autowired
    private ActionVariableRelationMapper actionVariableRelationMapper;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private FactMapper factMapper;

    @Autowired
    private UnitFactRelationMapper unitFactRelationMapper;

    @Autowired
    private LogicMapper logicMapper;

    @Autowired
    private FactRelationMapper factRelationMapper;

    @Autowired
    private UnitGroupMapper unitGroupMapper;

    @Autowired
    private UnitUnitGroupRelationMapper unitUnitGroupRelationMapper;

    @Autowired
    private FlowRuleMapper flowRuleMapper;

    @Autowired
    private ChainMapper chainMapper;

    @Autowired
    private ChainUnitRelationMapper chainUnitRelationMapper;

    //chain
    Chain chain = new Chain();

    //chain unit relation
    ChainUnitRelation chainUnitRelation = new ChainUnitRelation();
    ChainUnitRelation chainUnitRelation2 = new ChainUnitRelation();

    //flow rule
    FlowRule flowRule = new FlowRule();

    //logic of flow rule
    Logic logicOfFlowRule = new Logic();

    //action
    com.aihulk.tech.entity.entity.Action action1 = new com.aihulk.tech.entity.entity.Action();
    com.aihulk.tech.entity.entity.Action action2 = new com.aihulk.tech.entity.entity.Action();

    //variable
    Variable variable1 = new Variable();
    Variable variable2 = new Variable();

    //unit
    Unit unit = new Unit();
    Unit unit2 = new Unit();

    //unit group
    UnitGroup unitGroup = new UnitGroup();

    //unit unit group relation
    UnitUnitGroupRelation unitUnitGroupRelation = new UnitUnitGroupRelation();

    //unit fact relation
    UnitFactRelation unitFactRelation1 = new UnitFactRelation();
    UnitFactRelation unitFactRelation2 = new UnitFactRelation();

    //fact
    Fact fact1 = new Fact();
    Fact fact2 = new Fact();

    //fact relation
    FactRelation factRelation1 = new FactRelation();

    //logic
    Logic logic = new Logic();
    Logic logic2 = new Logic();

    protected Integer bizId = 1;

    @Before
    public void setUp() throws Exception {
        insertFact();
        insertFactRelation();
        insertUnitGroup();
        insertUnit();
        insertUnitUnitGroupRelation();
        insertLogic();
        insertUnitFactRelation();
        insertAction();
        insertVarible();
        insertActionVariableRelation();
        insertChain();
        insertChainUnitRelation();
        insertFlowRule();
        insertLogicOfFlowRule();
    }

    private void insertChain() {
        chain.setName("test chain");
        chain.setNameEn("test chain");
        chain.setBusinessId(bizId);
        chainMapper.insert(chain);
    }

    private void insertChainUnitRelation() {
        chainUnitRelation.setChainId(chain.getId());
        chainUnitRelation.setUnitId(unitGroup.getId());
        chainUnitRelation.setType(ChainUnitRelation.TYPE_EXECUTE_UNIT_GROUP);
        chainUnitRelationMapper.insert(chainUnitRelation);

        chainUnitRelation2.setChainId(chain.getId());
        chainUnitRelation2.setUnitId(unit2.getId());
        chainUnitRelation2.setType(ChainUnitRelation.TYPE_EXECUTE_UNIT);
        chainUnitRelationMapper.insert(chainUnitRelation2);
    }

    private void insertFlowRule() {
        flowRule.setChainId(chain.getId());
        flowRule.setBizId(bizId);
        flowRule.setSrcId(unitGroup.getId());
        flowRule.setDestId(unit2.getId());
        flowRule.setDestType(FlowRule.UNIT_TYPE_EXECUTE_UNIT);
        flowRule.setSrcType(FlowRule.UNIT_TYPE_EXECUTE_GROUP);
        flowRule.setName("test flow rule");
        flowRule.setNameEn("test flow rule");
        flowRuleMapper.insert(flowRule);
    }

    private void insertUnitUnitGroupRelation() {
        unitUnitGroupRelation.setUnitGroupId(unitGroup.getId());
        unitUnitGroupRelation.setUnitId(unit.getId());
        unitUnitGroupRelationMapper.insert(unitUnitGroupRelation);
    }

    private void insertUnitGroup() {
        unitGroup.setName("test unit group");
        unitGroup.setNameEn("test unit group");
        unitGroup.setBusinessId(bizId);
        unitGroupMapper.insert(unitGroup);
    }

    private void insertFactRelation() {
        factRelation1.setFactId(fact2.getId());
        factRelation1.setRefFactId(fact1.getId());
        factRelationMapper.insert(factRelation1);
    }

    private void insertLogic() {
        logic.setName("test logic");
        logic.setNameEn("test logic");
        logic.setBusinessId(bizId);
        logic.setRelationId(unit.getId());
        logic.setStructure(Logic.STRUCTURE_DECISION_FLOW);
        logic.setLogicExp("{\"src\":2,\"op\":\"GT\",\"dest\":1}");
        logicMapper.insert(logic);

        logic2.setName("test logic 2");
        logic2.setNameEn("test logic 2");
        logic2.setBusinessId(bizId);
        logic2.setRelationId(unit2.getId());
        logic2.setStructure(Logic.STRUCTURE_DECISION_FLOW);
        logic2.setLogicExp("{\"src\":3,\"op\":\"EQ\",\"dest\":3}");
        logicMapper.insert(logic2);
    }

    private void insertLogicOfFlowRule() {
        logicOfFlowRule.setName("logic of flow rule");
        logicOfFlowRule.setNameEn("logic of flow rule");
        logicOfFlowRule.setBusinessId(bizId);
        logicOfFlowRule.setRelationId(flowRule.getId());
        logicOfFlowRule.setStructure(Logic.STRUCTURE_FLOW_RULE);
        logicOfFlowRule.setLogicExp("{\"src\":1,\"op\":\"LT\",\"dest\":3}");
        logicMapper.insert(logicOfFlowRule);
    }

    private void insertUnit() {
        unit.setName("test unit");
        unit.setNameEn("test unit");
        unit.setType(Unit.TYPE_DECISION_BLOCK);
        unit.setBusinessId(bizId);
        unitMapper.insert(unit);

        unit2.setName("first test unit");
        unit2.setNameEn("first test unit");
        unit2.setType(Unit.TYPE_DECISION_BLOCK);
        unit2.setBusinessId(bizId);
        unitMapper.insert(unit2);
    }

    private void insertFact() {

        fact2.setNameEn("test fact 2");
        fact2.setName("test fact 2");
        fact2.setBusinessId(bizId);
        fact2.setScriptType(Fact.SCRIPT_TYPE_JS);
        fact2.setResultType(Fact.RESULT_TYPE_NUMBER);
        factMapper.insert(fact2);

        fact1.setName("test fact 1");
        fact1.setNameEn("test fact 1");
        fact1.setBusinessId(bizId);
        fact1.setResultType(Fact.RESULT_TYPE_NUMBER);
        fact1.setScriptType(Fact.SCRIPT_TYPE_BASIC);
        String script = "function func1(data){" +
                "return data.apply.age;" +
                "}" +
                "func1(data);";
        fact1.setFormatScript(script);
        factMapper.insert(fact1);


        //让依赖的事实先入库 来验证拓扑排序是有效的
        String script2 = "function func2(data){" +
                "$ref_fact_" + fact1.getId() +
                "}" +
                "func2(data);";
        fact2.setFormatScript(script2);
        factMapper.updateById(fact2);


    }

    private void insertUnitFactRelation() {
        unitFactRelation1.setFactId(fact1.getId());
        unitFactRelation1.setUnitId(unit.getId());

        unitFactRelation2.setFactId(fact2.getId());
        unitFactRelation2.setUnitId(unit.getId());
        unitFactRelationMapper.insert(unitFactRelation1);
        unitFactRelationMapper.insert(unitFactRelation2);
    }

    private void insertAction() {

        action1.setName("输出测试变量");
        action1.setNameEn("output test variable");
        action1.setBusinessId(bizId);
        action1.setUnitId(unit.getId());
        action1.setType(com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT);
        actionMapper.insert(action1);

        action2.setName("输出测试变量2");
        action2.setNameEn("output test variable2");
        action2.setBusinessId(bizId);
        action2.setUnitId(unit.getId());
        action2.setType(com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT);
        actionMapper.insert(action2);
    }

    public void insertVarible() {

        variable1.setName("测试变量1");
        variable1.setNameEn("test variable 1");
        variable1.setMergeStrategy(Variable.MERGE_STRATEGY_CHAIN_FIRST | Variable.MERGE_STRATEGY_UNIT_ALL);
        variable1.setBusinessId(bizId);
        variable1.setType(Variable.TYPE_NUMBER);
        variableMapper.insert(variable1);

        variable2.setName("测试变量2");
        variable2.setNameEn("test variable 2");
        variable2.setMergeStrategy(Variable.MERGE_STRATEGY_CHAIN_FIRST | Variable.MERGE_STRATEGY_UNIT_LAST);
        variable2.setBusinessId(1);
        variable2.setType(Variable.TYPE_STRING);
        variableMapper.insert(variable2);
    }

    private void insertActionVariableRelation() {

        ActionVariableRelation actionVariableRelation1 = new ActionVariableRelation();
        actionVariableRelation1.setActionId(action1.getId());
        actionVariableRelation1.setVariableId(variable1.getId());
        actionVariableRelation1.setValue("1");

        ActionVariableRelation actionVariableRelation2 = new ActionVariableRelation();
        actionVariableRelation2.setActionId(action2.getId());
        actionVariableRelation2.setVariableId(variable2.getId());
        actionVariableRelation2.setValue("aaa");

        actionVariableRelationMapper.insert(actionVariableRelation1);
        actionVariableRelationMapper.insert(actionVariableRelation2);

    }

}
