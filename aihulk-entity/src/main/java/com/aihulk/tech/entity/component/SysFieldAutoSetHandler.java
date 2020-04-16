package com.aihulk.tech.entity.component;

import com.aihulk.tech.common.util.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.google.common.base.Strings;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author zhangyibo
 * @date 2019-06-2810:37
 */
public class SysFieldAutoSetHandler implements MetaObjectHandler {


    private static final String SYS_FIELD_CREATE_TIME = "createTime";
    private static final String SYS_FIELD_UPDATE_TIME = "updateTime";
    private static final String SYS_FIELD_OPERATOR = "operator";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(SYS_FIELD_CREATE_TIME, DateUtil.getCurDateTime(), metaObject);
        this.setFieldValByName(SYS_FIELD_UPDATE_TIME, DateUtil.getCurDateTime(), metaObject);
        this.setOperator(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(SYS_FIELD_UPDATE_TIME, DateUtil.getCurDateTime(), metaObject);
        this.setOperator(metaObject);
    }

    private void setOperator(MetaObject metaObject) {
        if (!Strings.isNullOrEmpty(OperatorHolder.getOperator())) {
            this.setFieldValByName(SYS_FIELD_OPERATOR, OperatorHolder.getOperator(), metaObject);
        }
    }
}
