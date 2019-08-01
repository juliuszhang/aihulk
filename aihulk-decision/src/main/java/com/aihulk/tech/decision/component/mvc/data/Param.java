
package com.aihulk.tech.decision.component.mvc.data;

abstract class Param {
    private final String fieldName;
    
    private final Object fieldValue;
    
    Param(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public Object getFieldValue() {
        return fieldValue;
    }
}
