package com.aihulk.tech.core.constant;

/**
 * @author zhangyibo
 * @title: DataType
 * @projectName aihulk
 * @description: 数据类型
 * @date 2019-07-0411:50
 */
public enum DataType {

    NUMBER("number"),
    STRING("string"),
    OBJECT("object"),
    ARRAY_STRING("array_string"),
    ARRAY_NUMBER("array_number"),
    ARRAY_OBJECT("array_object");

    private String name;

    DataType(String name) {
        this.name = name;
    }

    public static DataType parse(String type) {
        for (DataType dataType : DataType.values()) {
            if (dataType.name.equalsIgnoreCase(type)) {
                return dataType;
            }
        }
        return null;
    }
}
