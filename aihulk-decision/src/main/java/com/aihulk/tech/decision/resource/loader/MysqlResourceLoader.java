package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.*;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.entity.entity.Chain;
import com.aihulk.tech.entity.entity.Unit;
import com.aihulk.tech.entity.entity.UnitGroup;
import com.aihulk.tech.entity.mapper.ChainMapper;
import com.aihulk.tech.entity.mapper.FactMapper;
import com.aihulk.tech.entity.mapper.UnitGroupMapper;
import com.aihulk.tech.entity.mapper.UnitMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: MysqlResourceLoader
 * @projectName aihulk
 * @description: MysqlResourceLoader
 * @date 2019-06-0414:52
 */
public class MysqlResourceLoader implements ResourceLoader<Resource> {

    private static final MybatisService MYBATIS_SERVICE = MybatisService.getInstance();

    private static final SqlSession SQL_SESSION = MYBATIS_SERVICE.getSqlSession();

    private static final ChainMapper chainMapper = SQL_SESSION.getMapper(ChainMapper.class);

    private static final UnitGroupMapper unitGroupMapper = SQL_SESSION.getMapper(UnitGroupMapper.class);

    private static final UnitMapper unitMapper = SQL_SESSION.getMapper(UnitMapper.class);


    @Override
    public Resource loadResource(Integer bizId, String version) {
        return null;
    }

    @Override
    public Map<String, Resource> loadAllResources(Integer bizId) {
        List<Chain> chains = this.selectChainsByBizId(bizId);
        List<DecisionChain> coreDecisionChains = Lists.newArrayListWithCapacity(chains.size());
        for (Chain chain : chains) {
            DecisionChain coreDecisionChain = new DecisionChain();
            coreDecisionChain.setName(chain.getName());
            coreDecisionChain.setId(chain.getId());
            coreDecisionChain.setNameEn(chain.getNameEn());
            List<UnitGroup> unitGroups = unitGroupMapper.selectByChainId(chain.getId());
            List<ExecuteUnitGroup> coreExecuteUnitGroups = Lists.newArrayListWithCapacity(unitGroups.size());
            for (UnitGroup unitGroup : unitGroups) {
                ExecuteUnitGroup coreExecuteUnitGroup = new ExecuteUnitGroup();
                coreExecuteUnitGroup.setId(unitGroup.getId());
                coreExecuteUnitGroup.setName(unitGroup.getName());
                coreExecuteUnitGroup.setNameEn(unitGroup.getNameEn());
                List<Unit> units = unitMapper.selectByChainId(unitGroup.getId());
                List<ExecuteUnit> coreExecuteUnits = Lists.newArrayListWithCapacity(units.size());
                for (Unit unit : units) {
                    DecisionFlow coreExecuteUnit = new DecisionFlow();
                    coreExecuteUnit.setName(unit.getName());
                    coreExecuteUnit.setNameEn(unit.getNameEn());
                    coreExecuteUnit.setFacts(getFeatures(SQL_SESSION, unit.getId()));
//                    coreExecuteUnit.setLogic(LogicHelper.parse(unit.getEvalStr()));
//                    coreExecuteUnit.setAction();
                    coreExecuteUnits.add(coreExecuteUnit);
                }
            }


            coreDecisionChains.add(coreDecisionChain);
        }

        return null;
    }

    private List<Chain> selectChainsByBizId(Integer bizId) {
        return null;
    }


    private List<Fact> getFeatures(SqlSession sqlSession, Integer ruleId) {
        FactMapper factMapper = sqlSession.getMapper(FactMapper.class);
        List<com.aihulk.tech.entity.entity.Fact> facts = factMapper.selectByUnitId(ruleId);
        List<Fact> coreFacts = Lists.newArrayListWithCapacity(facts.size());
        for (com.aihulk.tech.entity.entity.Fact fact : facts) {
            Fact coreFact = new Fact();
            coreFact.setId(fact.getId());
            coreFact.setName(fact.getName());
            coreFact.setNameEn(fact.getNameEn());
            coreFact.setCode(fact.getCode());
            coreFacts.add(coreFact);
        }

        return coreFacts;
    }
}
