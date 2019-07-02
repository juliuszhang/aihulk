package com.aihulk.tech.core.exception;

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

    public enum Code {
        SUCCESS(0),
        FAIL(-1),
        ENGINE_INIT_FAIL(-2),
        SCRIPT_EXEC_FAIL(-3),
        ENGINE_NOT_INIT(-4),
        DECISION_CHAIN_HAS_CIRCLE(-5);

        int code;

        Code(int code) {
            this.code = code;
        }
    }
}
