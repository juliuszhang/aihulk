package com.aihulk.tech.exception;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:29
 * @Description:
 */
public class EngineNotInitException extends RuleEngineException {

    public EngineNotInitException(String message) {
        super(Code.ENGINE_NOT_INIT, message);
    }
}
