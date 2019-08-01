package com.aihulk.tech.decision.component.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Router
 * @Description: 标注在类上标识这是一个处理http请求的类
 * @date 2019/8/1
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
    
}
