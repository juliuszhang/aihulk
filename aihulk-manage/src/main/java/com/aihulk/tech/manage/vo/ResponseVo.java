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
public class ResponseVo<R> {

    private R data;

    private int code;

    private String msg;

    public ResponseVo<R> buildSuccess(R data, String msg) {
        this.data = data;
        this.code = CommonCode.SUCCESS.code;
        this.msg = msg;
        return this;
    }

    public ResponseVo<R> buildFail(ResponseCode code, String msg) {
        this.code = code.code;
        this.msg = msg;
        return this;
    }

    /**
     * buildFail时传入的code只能是该类型 限死类型以免代码中出现魔术字符
     */
    public static abstract class ResponseCode {
        protected int code;

        public ResponseCode(int code) {
            this.code = code;
        }
    }

    /**
     * 通用错误码
     */
    public static class CommonCode extends ResponseCode {

        public CommonCode(int code) {
            super(code);
        }

        public static final CommonCode SUCCESS = new CommonCode(0);
        public static final CommonCode FAIL = new CommonCode(-1);
    }

    /**
     * 参数检查错误
     */
    public static class ParamCheckCode extends ResponseCode {
        public ParamCheckCode(int code) {
            super(code);
        }

        public static final ParamCheckCode PARAM_NULL = new ParamCheckCode(101);

        public static final ParamCheckCode PARAM_ILLEGAL = new ParamCheckCode(102);

    }

}
