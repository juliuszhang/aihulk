package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.exception.RuleEngineException;
import com.aihulk.tech.core.logic.Express;
import com.aihulk.tech.core.service.FactService;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: DecisionTree
 * @projectName aihulk
 * @description: 决策树
 * @date 2019-07-0314:08
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class DecisionTree extends ExecuteUnit<ExecuteUnit.ExecuteUnitResponse> {

    private TreeNode root;

    private Stack<TreeNode> stack;

    @Data
    public static class TreeNode {

        public static final Integer TYPE_CONDITION = 0;

        public static final Integer TYPE_RESULT = 1;

        public static final Integer TYPE_FACT = 2;

        private String name;

        private String nameEn;

        private Integer type;

        private Express express;

        private Action action;

        private List<Fact> facts;

        private List<TreeNode> treeNodes = Lists.newArrayList();
    }

    @Override
    public ExecuteUnitResponse exec() {
        List<TreeNode> resultNodes = this.exec(this.root);
        ExecuteUnitResponse response = new ExecuteUnitResponse();
        //默认决策树要最终返回结果才算触发
        response.setFired(resultNodes.isEmpty());
        response.setActions(resultNodes.stream().map(TreeNode::getAction).collect(Collectors.toList()));
        return response;
    }


    public List<TreeNode> exec(TreeNode root) {
        List<TreeNode> resultNodes = Lists.newArrayList();
        stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            //事实节点 执行抽取事实的动作
            if (TreeNode.TYPE_FACT.equals(treeNode.getType())) {
                FactService factService = new FactService();
                factService.extractFeature(DecisionTree.this.facts);
                List<TreeNode> treeNodes = treeNode.getTreeNodes();
                treeNodes.forEach(stack::push);
            } else if (TreeNode.TYPE_CONDITION.equals(treeNode.getType())) {
                if (treeNode.express.eval()) {
                    List<TreeNode> treeNodes = treeNode.getTreeNodes();
                    for (TreeNode n : treeNodes) {
                        //该节点为结果节点
                        if (TreeNode.TYPE_RESULT.equals(n.type)) {
                            resultNodes.add(n);
                        } else {
                            stack.push(n);
                        }
                    }
                }
            } else {
                log.error("未知的决策树节点类型 type = {},决策树信息为:{}", treeNode.getType(), this.getId() + "-" + this.getName());
                throw new RuleEngineException(RuleEngineException.Code.UNKNOWN_DECISION_TREE_NODE_TYPE, "未知的决策树节点类型 type = " + treeNode.getType());
            }
        }
        return resultNodes;
    }
}
