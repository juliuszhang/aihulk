package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.exception.RuleEngineException;
import com.aihulk.tech.core.logic.Logic;
import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName DecisionChain
 * @Description <p>
 * 决策链会将所有执行单元组织成一个图，图中的每个顶点就是执行单元（也可以是执行单元组）,每条边就是是否能到达下一个顶点的条件
 * <p/>
 * @Author yibozhang
 * @Date 2019/5/1 12:25
 * @Version 1.0
 */
@Slf4j
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DecisionChain extends BaseResource {

    private LinkedList<Node> nodes;


    public DecisionChain() {
        nodes = Lists.newLinkedList();
    }

    /**
     * 图中的节点对象
     */
    @Getter
    @Setter
    @EqualsAndHashCode
    private class Node {

        //保存一个入度以便查找到第一个执行的节点（也就是入度为0的节点）
        private int inDegree;

        private BasicUnit basicUnit;

        private List<ConditionEdge> conditions;

        public Node(BasicUnit basicUnit) {
            this.basicUnit = basicUnit;
            this.conditions = Lists.newLinkedList();
        }

        public void addCondition(ConditionEdge condition) {
            if (getCondition(condition.getDest().basicUnit) == null) {
                Node node = DecisionChain.this.getNode(condition.getDest().basicUnit);
                node.inDegree++;
                conditions.add(condition);
            }
        }

        public ConditionEdge getCondition(BasicUnit basicUnit) {
            if (basicUnit == null) return null;
            for (ConditionEdge condition : conditions) {
                if (condition.getDest() != null
                        && condition.getDest().equals(basicUnit)) {
                    return condition;
                }
            }
            return null;
        }

        public void removeCondition(BasicUnit basicUnit) {
            if (basicUnit == null) return;
            conditions.remove(getCondition(basicUnit));
        }

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public class ConditionEdge {

        private Node src;

        private Node dest;

        //满足该条件才表示target可达
        //FIXME 多个表达式
        private Logic logic;

        /**
         * 测试target是否连通
         *
         * @return
         */
        public boolean connected() {
            return logic.eval();
        }

        public BasicUnit getSrcBasicUnit() {
            return this.src.basicUnit;
        }

        public void setSrcBasicUnit(BasicUnit basicUnit) {
            this.src = findNode(basicUnit);
        }

        public BasicUnit getDestBasicUnit() {
            return this.dest.basicUnit;
        }

        public void setDestBasicUnit(BasicUnit basicUnit) {
            this.dest = findNode(basicUnit);
        }

        private Node findNode(BasicUnit basicUnit) {
            for (Node node : nodes) {
                if (node.getBasicUnit().equals(basicUnit)) {
                    return node;
                }
            }
            return null;
        }
    }

    /**
     * 广度优先搜索
     */
    private class BFSIterator implements Iterator<BasicUnit> {

        //已访问的规则集
        private List<Node> visited;

        //待访问的规则集
        private Queue<Node> waitToVisit;

        public BFSIterator(Node root) {
            this.visited = Lists.newLinkedList();
            this.waitToVisit = Lists.newLinkedList();
            waitToVisit.offer(root);
        }

        @Override
        public boolean hasNext() {
            log.debug("queue size = {}", waitToVisit.size());
            return waitToVisit.size() > 0;
        }

        @Override
        public BasicUnit next() {
            Node node = waitToVisit.poll();
            if (node == null) return null;
            //遍历节点所有的边
            for (ConditionEdge condition : node.conditions) {
                Node target = condition.getDest();
                if (!visited.contains(target)
                        && !waitToVisit.contains(target) //判断是否访问过以及待访问的节点中是否已经包含了该目标节点
                        && condition.connected()) { //判断两个节点之间是否连通
                    waitToVisit.offer(target);
                    //默认有一个节点连通后就不访问其他节点了
                    break;
                }
            }

            visited.add(node);

            return node.basicUnit;
        }

    }


    public void add(BasicUnit basicUnit, ConditionEdge... conditions) {
        if (basicUnit == null) return;
        Node node = new Node(basicUnit);
        for (ConditionEdge condition : conditions) {
            node.addCondition(condition);
        }
        nodes.add(node);
    }

    public void add(ConditionEdge condition) {
        if (condition == null) return;
        Node node = condition.getSrc();
        if (node != null) {
            node.addCondition(condition);
        } else {
            log.error("unknown basicUnit:{}", condition.getSrc());
        }
    }

    public void add(List<ConditionEdge> conditions) {
        if (conditions == null) return;
        for (ConditionEdge conditionEdge : conditions) {
            this.add(conditionEdge);
        }
    }

    public void remove(BasicUnit basicUnit) {
        if (basicUnit == null) return;
        Node node = getNode(basicUnit);
        if (node != null) {
            nodes.remove(node);
        } else {
            log.error("unknown basicUnit:{}", basicUnit);
        }
    }

    public void remove(ConditionEdge condition) {
        if (condition == null) return;
        Node node = condition.getSrc();
        if (node != null) {
            nodes.remove(node);
        } else {
            log.error("unknown basicUnit:{}", condition.getSrc());
        }
    }

    public Iterator<BasicUnit> iterator() {
        if (!nodes.isEmpty()) {
            //找到入度为0的node作为开始
            for (Node node : nodes) {
                if (node.inDegree == 0) return new BFSIterator(node);
            }
            log.error("检测到决策链中存在环,chainId = " + this.getId());
            throw new RuleEngineException(RuleEngineException.Code.DECISION_CHAIN_HAS_CIRCLE, "决策链存在环");
        } else {
            throw new NullPointerException();
        }
    }

    private Node getNode(BasicUnit basicUnit) {
        if (basicUnit == null) return null;

        for (Node n : nodes) {
            if (n.basicUnit != null && n.basicUnit.equals(basicUnit)) {
                return n;
            }
        }
        return null;
    }

}
