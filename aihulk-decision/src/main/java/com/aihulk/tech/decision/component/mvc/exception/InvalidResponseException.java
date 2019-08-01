
package com.aihulk.tech.decision.component.mvc.exception;


public class InvalidResponseException extends RuntimeException {
    public InvalidResponseException() {
        super(ExceptionMessage.INVALID_RESPONSE_ERROR.getMessage());
    }
}
