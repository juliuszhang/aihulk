package com.aihulk.tech.core.resource.loader;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.MergeStrategy;
import com.aihulk.tech.common.util.DateUtil;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.logic.ExpressHelper;
import com.aihulk.tech.core.logic.Operation;
import com.aihulk.tech.core.logic.SimpleExpress;
import com.aihulk.tech.core.resource.entity.*;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:40
 * @Description:
 */
public class LocalTestResourceLoader implements ResourceLoader {
    @Override
    public Resource loadResource(Integer bizId, String version) {
        Resource resource = new Resource();
        //decision unit
        DecisionChain chain = new DecisionChain();
        chain.setId(1);
        chain.setName("测试决策单元");
        chain.setDesc("测试决策单元desc");
        chain.setCreateTime(DateUtil.getCurDateTime());
        chain.setUpdateTime(DateUtil.getCurDateTime());
        //executeUnit
        DecisionBlock executeUnit1 = new DecisionBlock();
        executeUnit1.setId(1);
        executeUnit1.setName("测试规则集");
        executeUnit1.setDesc("测试规则集desc");
        executeUnit1.setCreateTime(DateUtil.getCurDateTime());
        executeUnit1.setUpdateTime(DateUtil.getCurDateTime());
        SimpleExpress executeUnitSimpleExpress = new SimpleExpress();
        executeUnitSimpleExpress.setSrc(3);
        executeUnitSimpleExpress.setTarget(2);
        executeUnitSimpleExpress.setOp(Operation.GT);
        executeUnit1.setExpress(executeUnitSimpleExpress);


        //ruleSets2
        ExecuteUnitGroup executeUnitGroup2 = new ExecuteUnitGroup();
        executeUnitGroup2.setId(2);
        executeUnitGroup2.setName("测试规则集2");
        executeUnitGroup2.setDesc("测试规则集2desc");
        executeUnitGroup2.setCreateTime(DateUtil.getCurDateTime());
        executeUnitGroup2.setUpdateTime(DateUtil.getCurDateTime());

        //rules
        DecisionBlock executeUnit = new DecisionBlock();
        executeUnit.setId(1);
        executeUnit.setName("测试规则");
        executeUnit.setDesc("测试规则desc");
        executeUnit.setCreateTime(DateUtil.getCurDateTime());
        executeUnit.setUpdateTime(DateUtil.getCurDateTime());
        //feature
        Fact ageFact = new Fact();
        ageFact.setId(1);
        ageFact.setName("获取年龄");
        ageFact.setCode("function $fact_001(data){ return $ref_fact_002} \n $fact_001(data);");
        Fact refFact = new Fact();
        refFact.setId(2);
        refFact.setName("引用特征");
        refFact.setCode("function $fact_002(data){ return data.apply.age} \n $fact_002(data);");
        Map<Integer, List<Fact>> relation = Maps.newHashMap();
        relation.put(1, Collections.singletonList(refFact));
        executeUnit.setFactsWithSort(Arrays.asList(ageFact, refFact), relation);
        //simpleExpress
        SimpleExpress simpleExpress = new SimpleExpress();
        SimpleExpress subSimpleExpress1 = new SimpleExpress();
        subSimpleExpress1.setSrc("$feature_001");
        subSimpleExpress1.setTarget(18);
        subSimpleExpress1.setOp(Operation.GT);
        SimpleExpress subSimpleExpress2 = new SimpleExpress();
        subSimpleExpress2.setSrc("");
        subSimpleExpress2.setOp(Operation.IS_EMPTY);
        simpleExpress.setSrc(subSimpleExpress1);
        simpleExpress.setTarget(subSimpleExpress2);
        simpleExpress.setOp(Operation.AND);
        executeUnit.setExpress(simpleExpress);
        OutPut action = new OutPut("a", "23891", DataType.NUMBER, MergeStrategy.NOTOVERWRITE, MergeStrategy.ALL);
        executeUnit.setActions(Collections.singletonList(action));

        //决策表
        DecisionTable decisionTable = new DecisionTable();
        decisionTable.setRows(Arrays.asList(new DecisionTable.Row(1, 1), new DecisionTable.Row(2, 2)));
        decisionTable.setCols(Arrays.asList(new DecisionTable.Col(1, 1, "年龄", DecisionTable.Col.TYPE_CONDITION),
                new DecisionTable.Col(2, 2, "变量a", DecisionTable.Col.TYPE_RESULT)));
        Map<String, DecisionTable.Cell> cellMap = Maps.newHashMap();
        DecisionTable.Cell cell1 = new DecisionTable.Cell();
        cell1.setRow(1);
        cell1.setCol(1);
        cell1.setRowSpan(1);
        cell1.setExpress(ExpressHelper.parse("{\"src\":1,\"op\":\"GT\",\"dest\":2}"));
        cellMap.put("1,1", cell1);

        DecisionTable.Cell cell2 = new DecisionTable.Cell();
        cell2.setRow(1);
        cell2.setCol(2);
        cell2.setRowSpan(1);
        cell2.setValue(new OutPut("var_1", "30", DataType.NUMBER, MergeStrategy.NOTOVERWRITE, MergeStrategy.ALL));
        cellMap.put("1,2", cell2);

        DecisionTable.Cell cell3 = new DecisionTable.Cell();
        cell3.setRow(2);
        cell3.setCol(1);
        cell3.setRowSpan(1);
        cell3.setExpress(ExpressHelper.parse("{\"src\":2,\"op\":\"GT\",\"dest\":1}"));
        cellMap.put("2,1", cell3);

        DecisionTable.Cell cell4 = new DecisionTable.Cell();
        cell4.setRow(2);
        cell4.setCol(2);
        cell4.setRowSpan(1);
        cell4.setValue(new OutPut("var_2", "50", DataType.NUMBER, MergeStrategy.NOTOVERWRITE, MergeStrategy.ALL));
        cellMap.put("2,2", cell4);

        decisionTable.setCellMap(cellMap);


        executeUnitGroup2.setExecuteUnits(Arrays.asList(executeUnit, decisionTable));

        chain.add(executeUnit1);
        chain.add(executeUnitGroup2);


        DecisionChain.ConditionEdge conditionEdge = chain.new ConditionEdge();
        conditionEdge.setSrcBasicUnit(executeUnit1);
        conditionEdge.setDestBasicUnit(executeUnitGroup2);
        SimpleExpress flowSimpleExpress = new SimpleExpress();
        flowSimpleExpress.setSrc(true);
        flowSimpleExpress.setOp(Operation.IS_TRUE);
        conditionEdge.setExpress(flowSimpleExpress);
        chain.add(conditionEdge);
        resource.setDecisionChains(Collections.singletonList(chain));
        return resource;
    }

    @Override
    public Map<String, Resource> loadAllResources(Integer bizId) {
        return null;
    }
}
