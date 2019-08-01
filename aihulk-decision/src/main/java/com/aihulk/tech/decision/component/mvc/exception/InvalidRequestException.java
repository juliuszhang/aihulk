
package com.aihulk.tech.decision.component.mvc.exception;

public class InvalidRequestException extends RuntimeException {
    
    public InvalidRequestException() {
        super(ExceptionMessage.INVALID_REQUEST_ERROR.getMessage());
    }
}
