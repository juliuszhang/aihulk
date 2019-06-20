package com.aihulk.tech.resource.entity;

import com.aihulk.tech.action.Action;
import com.aihulk.tech.logic.EvalAble;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExecuteUnit
 * @Description 执行单元
 * @Author yibozhang
 * @Date 2019/5/1 12:20
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExecuteUnit extends BaseResource implements EvalAble, BasicUnit {

    private Express express;

    private Action action;

    private List<Fact> facts = Lists.newArrayList();

    private Map<Integer, List<Fact>> factRelation = Maps.newHashMap();

    @Override
    public boolean eval() {
        return express.eval();
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    public void setFactsWithSort(List<Fact> facts, Map<Integer, List<Fact>> factRelation) {
        this.facts = facts;
        this.factRelation = factRelation;
        sortFacts();
    }

    public void sortFacts() {
        DirectedAcyclicGraph<Fact, DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);
        buildDagRecursive(facts, dag);
        facts.clear();
        for (Fact fact : dag) {
            facts.add(fact);
        }
        Collections.reverse(facts);
    }

    public void buildDagRecursive(List<Fact> facts, DirectedAcyclicGraph<Fact, DefaultEdge> dag) {
        if (facts == null || facts.isEmpty()) return;
        for (Fact fact : facts) {
            dag.addVertex(fact);
            List<Fact> refFacts = factRelation.get(fact.getId());
            if (refFacts != null) {
                for (Fact refFact : refFacts) {
                    dag.addVertex(refFact);
                    dag.addEdge(fact, refFact);
                }
                buildDagRecursive(refFacts, dag);
            }
        }
    }

    @Override
    public UnitType getType() {
        return UnitType.EXECUTE_UNIT;
    }

}
