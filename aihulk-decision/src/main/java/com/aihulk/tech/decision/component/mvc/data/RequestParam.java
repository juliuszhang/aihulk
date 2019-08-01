package com.aihulk.tech.decision.component.mvc.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestParam {
    
    private List<Param> params;
    
    public void add(Param param) {
        if (this.params == null || this.params.isEmpty()) {
            params = new LinkedList<>();
        }
        this.params.add(param);
    }
    
    private Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if (this.params != null && !this.params.isEmpty()) {
            for (Param param : this.params) {
                String fieldName = param.getFieldName();
                Object fieldValue = param.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + "," + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }
    
    public String getString(String fieldName) {
        return CastUtil.castString(getFieldMap().get(fieldName));
    }
    
    public boolean getBoolean(String fieldName) {
        return CastUtil.castBoolean(getFieldMap().get(fieldName));
    }
    
    public double getDouble(String fieldName) {
        return CastUtil.castDouble(getFieldMap().get(fieldName));
    }
    
    public int getInt(String fieldName) {
        return CastUtil.castInt(getFieldMap().get(fieldName));
    }
    
    public long getLong(String fieldName) {
        return CastUtil.castLong(getFieldMap().get(fieldName));
    }
    
}
