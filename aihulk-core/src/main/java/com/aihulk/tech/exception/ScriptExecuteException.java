package com.aihulk.tech.exception;

/**
 * @ClassName ScriptExecuteException
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/2/22 20:59
 * @Version 1.0
 */
public class ScriptExecuteException extends RuleEngineException {
    public ScriptExecuteException(String message) {
        super(Code.SCRIPT_EXEC_FAIL, message);
    }
}
