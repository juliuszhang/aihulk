package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.entity.mapper.ActionMapper;
import com.aihulk.tech.entity.mapper.VariableMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

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
public class ActionResourceLoader implements ResourceLoader<List<Action>> {

    private ActionMapper actionMapper = MybatisService.getMapper(ActionMapper.class);

    private VariableMapper variableMapper = MybatisService.getMapper(VariableMapper.class);

    @Override
    public List<Action> loadResource(Integer bizId, String version) {
        List<com.aihulk.tech.entity.entity.Action> dbActions = actionMapper.selectList(new QueryWrapper<com.aihulk.tech.entity.entity.Action>()
                .lambda().eq(com.aihulk.tech.entity.entity.Action::getBusinessId, bizId));

        List<Map<String, Object>> variables = variableMapper.selectAll();
        //以actionId作为key
        Map<Integer, Map<String, Object>> variableMap = variables.stream().collect(Collectors.toMap(map -> (Integer) map.get("actionId"), Function.identity()));

        List<Action> results = Lists.newArrayListWithExpectedSize(dbActions.size());
        for (com.aihulk.tech.entity.entity.Action dbAction : dbActions) {
            //如果是输出变量
            if (com.aihulk.tech.entity.entity.Action.ACTION_TYPE_OUTPUT.equals(dbAction.getType())) {
                Map<String, Object> variable = variableMap.get(dbAction.getId());
                String nameEn = (String) variable.get("nameEn");
                Object value = variable.get("value");
                Integer mergeStrategy = (Integer) variable.get("mergeStrategy");
                Action action = new OutPut(nameEn, value, mergeStrategy);
                action.setId(dbAction.getId());
                results.add(action);
            }
        }
        return results;
    }

    @Override
    public Map<String, List<Action>> loadAllResources(Integer bizId) {
        return null;
    }
}
