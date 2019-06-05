package com.aihulk.tech.logic;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * @ClassName Operation
 * @Description 定义表达式的操作
 * @Author yibozhang
 * @Date 2019/5/1 12:22
 * @Version 1.0
 */
public enum Operation {
    AND("and") {
        @Override
        public boolean eval(Object src, Object target) {
            if (src instanceof Boolean && target instanceof Boolean) {
                return (boolean) src && (boolean) target;
            }
            return false;
        }
    },
    OR("or") {
        @Override
        public boolean eval(Object src, Object target) {
            if (src instanceof Boolean && target instanceof Boolean) {
                return (boolean) src || (boolean) target;
            }
            return false;
        }
    },
    IS_NULL("is_null") {
        @Override
        public boolean eval(Object src, Object target) {
            return src == null;
        }
    },
    IS_EMPTY("is_empty") {
        @Override
        public boolean eval(Object src, Object target) {
            if (src == null) return true;
            if (src instanceof String) return Strings.isNullOrEmpty(src.toString());
            if (src instanceof Collection) return ((Collection) src).isEmpty();
            if (src instanceof Map) return ((Map) src).isEmpty();
            return false;
        }
    },
    GT("gt") {
        @Override
        public boolean eval(Object src, Object target) {
            if (src instanceof Number && target instanceof Number) {
                BigDecimal srcDecimal = new BigDecimal(src.toString());
                BigDecimal tarDecimal = new BigDecimal(target.toString());
                return srcDecimal.compareTo(tarDecimal) == 1;
            }
            return false;
        }
    },
    LT("lt") {
        @Override
        public boolean eval(Object src, Object target) {
            if (src instanceof Number && target instanceof Number) {
                BigDecimal srcDecimal = new BigDecimal(src.toString());
                BigDecimal tarDecimal = new BigDecimal(target.toString());
                return srcDecimal.compareTo(tarDecimal) == -1;
            }
            return false;
        }
    },
    EQ("eq") {
        @Override
        public boolean eval(Object src, Object target) {
            return src.equals(target);
        }
    },
    IS_TRUE("true") {
        @Override
        public boolean eval(Object src, Object target) {
            return src instanceof Boolean && (Boolean) src;
        }
    },
    IS_FALSE("false") {
        @Override
        public boolean eval(Object src, Object target) {
            return !(src instanceof Boolean) || !(Boolean) src;
        }
    };

    private String name;

    Operation(String name) {
        this.name = name;
    }

    public abstract boolean eval(Object src, Object target);
}
