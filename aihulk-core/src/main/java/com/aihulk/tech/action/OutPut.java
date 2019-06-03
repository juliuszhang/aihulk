package com.aihulk.tech.action;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutPut implements Action {

    private String key;

    private Object obj;

    private MergeStrategy mergeStrategy;


    public enum MergeStrategy {

        OVERWRITE {
            @Override
            public Object merge(Object obj1, Object obj2) {
                return obj2;
            }
        },
        ALL {
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
        NOTOVERWRITE {
            @Override
            public Object merge(Object obj1, Object obj2) {
                return obj1;
            }
        };

        public abstract Object merge(Object obj1, Object obj2);

    }
}
