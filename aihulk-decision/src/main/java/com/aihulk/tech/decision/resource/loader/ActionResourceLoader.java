package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.ConsoleOutput;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.entity.entity.Variable;
import com.aihulk.tech.entity.mapper.ActionMapper;
import com.aihulk.tech.entity.mapper.VariableMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: ActionResourceLoader
 * @projectName aihulk
 * @description: ActionResourceLoader
 * @date 2019-07-0514:43
 */
@Component
public class ActionResourceLoader implements ResourceLoader<Map<Integer, List<Action>>> {

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private VariableMapper variableMapper;

    @Override
    public Map<Integer, List<Action>> loadResource(Integer bizId, String version) {
        List<com.aihulk.tech.entity.entity.Action> dbActions = actionMapper.selectList(new QueryWrapper<com.aihulk.tech.entity.entity.Action>()
                .lambda().eq(com.aihulk.tech.entity.entity.Action::getBusinessId, bizId));

        List<Map<String, Object>> variables = variableMapper.selectAll();
        //以actionId作为key
        Map<Integer, Map<String, Object>> variableMap = variables.stream().collect(Collectors.toMap(map -> (Integer) map.get("actionId"), Function.identity()));

        Map<Integer, List<Action>> resultMap = Maps.newHashMap();
        for (com.aihulk.tech.entity.entity.Action dbAction : dbActions) {
            resultMap.putIfAbsent(dbAction.getUnitId(), new ArrayList<>());
            //如果是输出变量
            if (com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT.equals(dbAction.getType())) {
                Map<String, Object> varMap = variableMap.get(dbAction.getId());
                Integer mergeStrategy = (Integer) varMap.get("mergeStrategy");
                String nameEn = (String) varMap.get("nameEn");
                String value = (String) varMap.get("value");
                DataType type = DataType.parse((String) varMap.get("type"));
                Action action = new OutPut(nameEn, value, type,
                        Variable.getUnitStrategy(mergeStrategy),
                        Variable.getChainStrategy(mergeStrategy));
                action.setId(dbAction.getId());
                resultMap.get(dbAction.getUnitId()).add(action);
            } else if (com.aihulk.tech.entity.entity.Action.ACTION_TYPE_CONSOLE_OUTPUT.equals(dbAction.getType())) {
                //TODO
                Action action = new ConsoleOutput();
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Map<Integer, List<Action>>> loadAllResources(Integer bizId) {
        return null;
    }
}
