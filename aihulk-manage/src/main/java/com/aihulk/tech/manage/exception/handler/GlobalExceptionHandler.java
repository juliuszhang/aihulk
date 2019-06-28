package com.aihulk.tech.manage.exception.handler;

import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangyibo
 * @title: GlobalExceptionHandler
 * @projectName aihulk
 * @description: 全局异常处理器
 * @date 2019-06-2617:55
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理系统业务异常
     *
     * @return
     */
    @ExceptionHandler(value = ManageException.class)
    public ResponseVo<Void> manageExceptionHandle(HttpServletRequest request, ManageException e) {
        log.error("catch exception by manage exception handle method,exception detail:", e);
        return new ResponseVo<Void>().buildFail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数检查异常
     *
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseVo<Void> illegalArgumentExceptionHandle(HttpServletRequest request, IllegalArgumentException e) {
        log.error("catch exception by illegal argument exception handle method,exception detail:", e);
        return new ResponseVo<Void>().buildFail(ResponseVo.ParamCheckCode.PARAM_ILLEGAL, e.getMessage());
    }

    /**
     * 处理未知异常
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseVo<Void> ExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("catch exception by exception handle method,exception detail:", e);
        return new ResponseVo<Void>().buildFail(ResponseVo.CommonCode.FAIL, e.getMessage());
    }

}
