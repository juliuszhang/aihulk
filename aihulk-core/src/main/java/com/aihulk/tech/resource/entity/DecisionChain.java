package com.aihulk.tech.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @ClassName DecisionChain
 * @Description <p>
 * 决策链会将所有规则集组织成一个图，图中的每个顶点就是规则集，每条边就是是否能到达下一个顶点的条件
 * <p/>
 * @Author yibozhang
 * @Date 2019/5/1 12:25
 * @Version 1.0
 */
@Slf4j
@Data
public class DecisionChain extends BaseResource {

    private LinkedList<Node> nodes;


    public DecisionChain() {
        nodes = Lists.newLinkedList();
    }

    /**
     * 图中的节点对象
     */
    @Data
    private static class Node {

        private BasicUnit basicUnit;

        private List<ConditionEdge> conditions;

        public Node(BasicUnit basicUnit) {
            this.basicUnit = basicUnit;
            this.conditions = Lists.newLinkedList();
        }

        public void addCondition(ConditionEdge condition) {
            if (getCondition(condition.getDest()) == null)
                conditions.add(condition);
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

    @Data
    public static class ConditionEdge {

        private BasicUnit src;

        private BasicUnit dest;

        //满足该条件才表示target可达
        private Express express;

        /**
         * 测试target是否连通
         *
         * @return
         */
        public boolean connected() {
            return express.eval();
        }
    }

    /**
     * 广度优先搜索
     */
    private class BFSIterator implements Iterator<BasicUnit> {

        //已访问的规则集
        private List<BasicUnit> visited;

        //待访问的规则集
        private Queue<BasicUnit> waitToVisit;

        public BFSIterator(BasicUnit root) {
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
            BasicUnit basicUnit = waitToVisit.poll();
            if (basicUnit == null) return null;
            //从节点列表里找到对应的节点
            Node node = getNode(basicUnit);
            if (node == null) return null;
            //遍历节点所有的边
            for (ConditionEdge condition : node.conditions) {
                BasicUnit target = condition.getDest();
                if (!visited.contains(target)
                        && !waitToVisit.contains(target) //判断是否访问过以及待访问的节点中是否已经包含了该目标节点
                        && condition.connected() //判断两个节点之间是否连通
                        && waitToVisit.isEmpty()) { //判断是否已经存在一个待待访问节点了
                    waitToVisit.offer(target);
                }
            }

            visited.add(basicUnit);

            return basicUnit;
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
        Node node = getNode(condition.getSrc());
        if (node != null) {
            node.addCondition(condition);
        } else {
            log.error("unknown basicUnit:{}", condition.getSrc());
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
        Node node = getNode(condition.getSrc());
        if (node != null) {
            nodes.remove(node);
        } else {
            log.error("unknown basicUnit:{}", condition.getSrc());
        }
    }

    public Iterator<BasicUnit> iterator() {
        if (!nodes.isEmpty())
            return new BFSIterator(nodes.get(0).basicUnit);
        else
            throw new NullPointerException();
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
