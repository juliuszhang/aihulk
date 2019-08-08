package com.aihulk.tech.common.constant;

import com.aihulk.tech.common.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: DataType
 * @projectName aihulk
 * @description: 数据类型
 * @date 2019-07-0411:50
 */
@AllArgsConstructor
public enum DataType {

    NUMBER("number") {
        @Override
        public Object cast(String value) {
            return new BigDecimal(value);
        }
    },
    STRING("string") {
        @Override
        public Object cast(String value) {
            return value;
        }
    },
    OBJECT("object") {
        @Override
        public Object cast(String value) {
            //FIXME
            return JsonUtil.parseObject(value, Map.class);
        }
    },
    ARRAY_STRING("array_string") {
        @Override
        public Object cast(String value) {
            return JsonUtil.parseObject(value, new TypeReference<List<String>>() {
            });
        }
    },
    ARRAY_NUMBER("array_number") {
        @Override
        public Object cast(String value) {
            return JsonUtil.parseObject(value, new TypeReference<List<BigDecimal>>() {
            });
        }
    },
    ARRAY_OBJECT("array_object") {
        @Override
        public Object cast(String value) {
            return JsonUtil.parseObject(value, new TypeReference<List<Map>>() {
            });
        }
    };

    @Getter
    private String name;

    public static DataType parse(String type) {
        if (type == null) return null;
        for (DataType dataType : DataType.values()) {
            if (dataType.name.equalsIgnoreCase(type)) {
                return dataType;
            }
        }
        return null;
    }

    public abstract Object cast(String value);
}
