package com.aihulk.tech.entity.component;

import com.aihulk.tech.common.util.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.google.common.base.Strings;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author zhangyibo
 * @title: SysFieldAutoSetHandler
 * @projectName aihulk
 * @description: 自动设置创建时间更新时间和操作人等系统字段
 * @date 2019-06-2810:37
 */
public class SysFieldAutoSetHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", DateUtil.getCurDateTime(), metaObject);
        this.setOperator(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", DateUtil.getCurDateTime(), metaObject);
        this.setOperator(metaObject);
    }

    private void setOperator(MetaObject metaObject) {
        if (!Strings.isNullOrEmpty(OperatorHolder.getOperator())) {
            this.setFieldValByName("operator", "Jerry", metaObject);//版本号3.0.6以及之前的版本
        }
    }
}
