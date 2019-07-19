package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.entity.entity.UnitGroup;
import com.aihulk.tech.entity.mapper.UnitGroupMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: UnitGroupResourceLoader
 * @projectName aihulk
 * @description: UnitGroupResourceLoader
 * @date 2019-07-0114:42
 */
public class UnitGroupResourceLoader implements ResourceLoader<Map<Integer, ExecuteUnitGroup>> {

    private UnitResourceLoader unitResourceLoader = new UnitResourceLoader();

    @Override
    public Map<Integer, ExecuteUnitGroup> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        UnitGroupMapper mapper = sqlSession.getMapper(UnitGroupMapper.class);
        UnitGroup queryParam = new UnitGroup();
        queryParam.setBizId(bizId);
        QueryWrapper wrapper = new QueryWrapper(queryParam);

        List<UnitGroup> unitGroups = mapper.selectList(wrapper);
        Map<Integer, ExecuteUnit> executeUnitMap = unitResourceLoader.loadResource(bizId, version);
        Map<Integer, List<ExecuteUnit>> relations = getUnitUnitGroupRelations(executeUnitMap);

        List<ExecuteUnitGroup> executeUnitGroups = unitGroups.stream().map(unitGroup -> this.map(unitGroup, relations)).collect(Collectors.toList());
        return executeUnitGroups.stream().collect(Collectors.toMap(ExecuteUnitGroup::getId, Function.identity()));
    }

    @Override
    public Map<String, Map<Integer, ExecuteUnitGroup>> loadAllResources(Integer bizId) {
        return null;
    }

    private Map<Integer, List<ExecuteUnit>> getUnitUnitGroupRelations(Map<Integer, ExecuteUnit> executeUnitMap) {
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        UnitGroupMapper mapper = sqlSession.getMapper(UnitGroupMapper.class);
        List<Map<String, Object>> relations = mapper.selectUnitUnitGroupRelation();
        Map<Integer, List<ExecuteUnit>> relationsMap = Maps.newHashMap();
        for (Map<String, Object> relation : relations) {
            Integer unitGroupId = (Integer) relation.get("unitGroupId");
            Integer unitId = (Integer) relation.get("unitId");
            relationsMap.putIfAbsent(unitGroupId, new ArrayList<>());
            relationsMap.get(unitGroupId).add(executeUnitMap.get(unitId));
        }
        return relationsMap;
    }

    private ExecuteUnitGroup map(UnitGroup unitGroup, Map<Integer, List<ExecuteUnit>> relations) {
        ExecuteUnitGroup executeUnitGroup = new ExecuteUnitGroup();
        executeUnitGroup.setId(unitGroup.getId());
        executeUnitGroup.setName(unitGroup.getName());
        executeUnitGroup.setNameEn(unitGroup.getNameEn());
        executeUnitGroup.setExecuteUnits(relations.get(unitGroup.getId()));
        return executeUnitGroup;
    }
}
