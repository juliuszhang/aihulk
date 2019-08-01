package com.aihulk.tech.decision.component.mvc.exception;

public class ActionExecuteException extends RuntimeException {
    
    public ActionExecuteException(Exception e) {
        super(ExceptionMessage.ACTION_EXECUTE_ERROR.getMessage(), e);
    }
}
