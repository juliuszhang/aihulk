package com.aihulk.tech.manage.vo;

import lombok.Getter;

/**
 * @author zhangyibo
 * @title: BaseResponseVo
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2716:34
 */
@Getter
public abstract class BaseResponseVo<R> {

    protected int code;

    protected String msg;

    protected R data;

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

    /**
     * 业务上的错误
     */
    public static class ManageBusinessErrorCode extends ResponseCode {

        public ManageBusinessErrorCode(int code) {
            super(code);
        }

        //用户不存在
        public static final ManageBusinessErrorCode USER_NOT_EXIST = new ManageBusinessErrorCode(201);

        //用户密码错误
        public static final ManageBusinessErrorCode USER_PASSWORD_WRONG = new ManageBusinessErrorCode(202);

        //邮箱暂未认证
        public static final ManageBusinessErrorCode USER_EMAIL_UNCHECKED = new ManageBusinessErrorCode(203);
    }

}
