package com.aihulk.tech.common.constant;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * merge 策略
 */
public enum MergeStrategy {

    OVERWRITE(1) {
        @Override
        public Object merge(Object obj1, Object obj2) {
            return obj2;
        }
    },
    ALL(2) {
        @Override
        public Object merge(Object obj1, Object obj2) {
            if (obj1 instanceof List) {
                ((List) obj1).add(obj2);
                return obj1;
            } else {
                List<Object> list = Lists.newArrayList();
                list.add(obj1);
                list.add(obj2);
                return list;
            }
        }
    },
    NOTOVERWRITE(3) {
        @Override
        public Object merge(Object obj1, Object obj2) {
            return obj1;
        }
    };

    public static MergeStrategy parse(Integer val) {
        for (MergeStrategy mergeStrategy : MergeStrategy.values()) {
            if (mergeStrategy.getVal().equals(val)) {
                return mergeStrategy;
            }
        }
        return null;
    }

    public abstract Object merge(Object obj1, Object obj2);


    @Getter
    private Integer val;

    MergeStrategy(Integer val) {
        this.val = val;
    }
}
