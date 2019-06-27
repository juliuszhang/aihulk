package com.aihulk.tech.manage.vo;

import lombok.Getter;

/**
 * @author zhangyibo
 * @title: ResponseVo
 * @projectName aihulk
 * @description: ResponseVo
 * @date 2019-06-2616:54
 */
@Getter
public class ResponseVo<R> extends BaseResponseVo<R> {

    public ResponseVo<R> buildSuccess(R data, String msg) {
        this.data = data;
        this.code = CommonCode.SUCCESS.code;
        this.msg = msg;
        return this;
    }

    public ResponseVo<R> buildSuccess(String msg) {
        this.buildSuccess(null, msg);
        return this;
    }

    public ResponseVo<R> buildFail(ResponseCode code, String msg) {
        this.code = code.code;
        this.msg = msg;
        return this;
    }

}
