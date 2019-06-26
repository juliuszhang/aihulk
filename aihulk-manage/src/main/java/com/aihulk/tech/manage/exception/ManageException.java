package com.aihulk.tech.manage.exception;

import com.aihulk.tech.manage.vo.ResponseVo;
import lombok.Getter;

/**
 * @author zhangyibo
 * @title: ManageException
 * @projectName aihulk
 * @description: 全局异常对象
 * @date 2019-06-2617:53
 */
@Getter
public class ManageException extends RuntimeException {

    private ResponseVo.ResponseCode code;

    public ManageException(ResponseVo.ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode;
    }
}
