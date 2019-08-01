
package com.aihulk.tech.decision.component.mvc.exception;


public class InitializeException extends RuntimeException {
    
    public InitializeException(Exception e) {
        super(ExceptionMessage.INITIALIZE_ERROR.getMessage(), e);
    }
}
