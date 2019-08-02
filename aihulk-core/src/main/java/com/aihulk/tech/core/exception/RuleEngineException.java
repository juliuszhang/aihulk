package com.aihulk.tech.core.exception;

import lombok.Getter;

/**
 * @ClassName RuleEngineException
 * @Description 最基础的决策引擎异常
 * @Author yibozhang
 * @Date 2019/2/22 20:58
 * @Version 1.0
 */
public class RuleEngineException extends RuntimeException {

    private Code code;

    public RuleEngineException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public static class Code {

        @Getter
        private int code;

        public static final Code SUCCESS = new Code(0);
        public static final Code FAIL = new Code(-1);
        public static final Code ENGINE_INIT_FAIL = new Code(-2);
        public static final Code SCRIPT_EXEC_FAIL = new Code(-3);
        public static final Code ENGINE_NOT_INIT = new Code(-4);
        public static final Code DECISION_CHAIN_HAS_CIRCLE = new Code(-5);
        //不存在的决策树节点类型
        public static final Code UNKNOWN_DECISION_TREE_NODE_TYPE = new Code(-6);

        Code(int code) {
            this.code = code;
        }
    }
}
