package com.aihulk.tech.manage.aspect;

import com.aihulk.tech.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author zhangyibo
 * @title: WebLogAspect
 * @projectName aihulk
 * @description: controller日志记录切面
 * @date 2019-06-2717:34
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {


    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    @Pointcut("execution(public * com.aihulk.tech.manage.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 前置通知：在连接点之前执行的通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("request_info = {},invoke_info = {}", url + "_" + method, className + "." + methodName + args);
    }

    private static final int MAX_LOG_LENGTH = 500;

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        //防止对象没有实现toString 所以都进行json序列化后打印
        String retJson = JsonUtil.toJsonString(ret);
        if (retJson.length() > MAX_LOG_LENGTH) retJson.substring(0, MAX_LOG_LENGTH);
        log.info("{} return value = {}", className + "." + methodName + args, retJson);
    }

}
