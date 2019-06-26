package com.aihulk.tech.manage.exception.handler;

import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.vo.ResponseVo;
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
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理系统业务异常
     *
     * @return
     */
    @ExceptionHandler(value = ManageException.class)
    public ResponseVo<Void> manageExceptionHandle(HttpServletRequest request, ManageException e) {
        return new ResponseVo<Void>().buildFail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数检查异常
     *
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseVo<Void> illegalArgumentExceptionHandle(HttpServletRequest request, IllegalArgumentException e) {
        return new ResponseVo<Void>().buildFail(ResponseVo.ParamCheckCode.PARAM_ILLEGAL, e.getMessage());
    }

    /**
     * 处理未知异常
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseVo<Void> ExceptionHandle(HttpServletRequest request, Exception e) {
        return new ResponseVo<Void>().buildFail(ResponseVo.CommonCode.FAIL, e.getMessage());
    }

}
