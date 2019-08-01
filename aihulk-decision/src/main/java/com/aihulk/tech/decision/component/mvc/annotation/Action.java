package com.aihulk.tech.decision.component.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Action
 * @Description: 标注在方法上以映射url和方法的关系
 * @date 2019/8/1
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    String value();

    RequestMethod[] method();
}
