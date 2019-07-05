package com.aihulk.tech.core.exception;

/**
 * @ClassName EngineInitException
 * @Description EngineInitException
 * @Author yibozhang
 * @Date 2019/5/1 12:46
 * @Version 1.0
 */
public class EngineInitException extends RuleEngineException {

    public EngineInitException(String message) {
        super(Code.ENGINE_INIT_FAIL, message);
    }
}
