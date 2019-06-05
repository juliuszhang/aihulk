package com.aihulk.tech.resource.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @ClassName DecisionChain
 * @Description <p>
 * 决策单元会将所有规则集组织成一个图，图中的每个顶点就是规则集，每条边就是是否能到达下一个顶点的条件
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

        private ExecuteUnitGroup executeUnitGroup;

        private List<ConditionEdge> conditions;

        public Node(ExecuteUnitGroup executeUnitGroup) {
            this.executeUnitGroup = executeUnitGroup;
            this.conditions = Lists.newLinkedList();
        }

        public void addCondition(ConditionEdge condition) {
            if (getCondition(condition.getTarget()) == null)
                conditions.add(condition);
        }

        public ConditionEdge getCondition(ExecuteUnitGroup executeUnitGroup) {
            if (executeUnitGroup == null) return null;
            for (ConditionEdge condition : conditions) {
                if (condition.getTarget() != null
                        && condition.getTarget().equals(executeUnitGroup)) {
                    return condition;
                }
            }
            return null;
        }

        public void removeCondition(ExecuteUnitGroup executeUnitGroup) {
            if (executeUnitGroup == null) return;
            conditions.remove(getCondition(executeUnitGroup));
        }

    }

    @Data
    public static class ConditionEdge {

        private ExecuteUnitGroup src;

        private ExecuteUnitGroup target;

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
    private class BFSIterator implements Iterator<ExecuteUnitGroup> {

        //已访问的规则集
        private List<ExecuteUnitGroup> visitedExecuteUnitGroup;

        //待访问的规则集
        private Queue<ExecuteUnitGroup> waitToVisit;

        public BFSIterator(ExecuteUnitGroup root) {
            this.visitedExecuteUnitGroup = Lists.newLinkedList();
            this.waitToVisit = Lists.newLinkedList();
            waitToVisit.offer(root);
        }

        @Override
        public boolean hasNext() {
            log.debug("queue size = {}", waitToVisit.size());
            return waitToVisit.size() > 0;
        }

        @Override
        public ExecuteUnitGroup next() {
            ExecuteUnitGroup executeUnitGroup = waitToVisit.poll();
            if (executeUnitGroup == null) return null;
            //从节点列表里找到对应的节点
            Node node = getNode(executeUnitGroup);
            if (node == null) return null;
            //遍历节点所有的边
            for (ConditionEdge condition : node.conditions) {
                ExecuteUnitGroup target = condition.getTarget();
                if (!visitedExecuteUnitGroup.contains(target)
                        && !waitToVisit.contains(target) //判断是否访问过以及待访问的节点中是否已经包含了该目标节点
                        && condition.connected() //判断两个节点之间是否连通
                        && waitToVisit.isEmpty()) { //判断是否已经存在一个待待访问节点了
                    waitToVisit.offer(target);
                }
            }

            visitedExecuteUnitGroup.add(executeUnitGroup);

            return executeUnitGroup;
        }

    }

    public void add(ExecuteUnitGroup executeUnitGroup, ConditionEdge... conditions) {
        if (executeUnitGroup == null) return;
        Node node = new Node(executeUnitGroup);
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
            log.error("unknown executeUnitGroup:{}", condition.getSrc());
        }
    }

    public void remove(ExecuteUnitGroup executeUnitGroup) {
        if (executeUnitGroup == null) return;
        Node node = getNode(executeUnitGroup);
        if (node != null) {
            nodes.remove(node);
        } else {
            log.error("unknown executeUnitGroup:{}", executeUnitGroup);
        }
    }

    public void remove(ConditionEdge condition) {
        if (condition == null) return;
        Node node = getNode(condition.getSrc());
        if (node != null) {
            nodes.remove(node);
        } else {
            log.error("unknown executeUnitGroup:{}", condition.getSrc());
        }
    }

    public Iterator<ExecuteUnitGroup> iterator() {
        if (!nodes.isEmpty())
            return new BFSIterator(nodes.get(0).executeUnitGroup);
        else
            throw new NullPointerException();
    }

    private Node getNode(ExecuteUnitGroup executeUnitGroup) {
        if (executeUnitGroup == null) return null;

        for (Node n : nodes) {
            if (n.executeUnitGroup != null && n.executeUnitGroup.equals(executeUnitGroup)) {
                return n;
            }
        }
        return null;
    }

}
