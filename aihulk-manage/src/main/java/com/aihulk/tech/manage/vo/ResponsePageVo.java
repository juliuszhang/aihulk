package com.aihulk.tech.manage.vo;

import lombok.Getter;

import java.util.List;

/**
 * @author zhangyibo
 * @title: ResponsePageVo
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2716:35
 */
@Getter
public class ResponsePageVo<R> extends BaseResponseVO<List<R>> {

    private int start;

    private int pageSize;

    private int count;

    public ResponsePageVo<R> buildSuccess(List<R> data, String msg, int start, int pageSize) {
        this.data = data;
        this.code = CommonCode.SUCCESS.code;
        this.msg = msg;
        this.start = start;
        this.pageSize = pageSize;
        if (data != null) this.count = data.size();
        return this;
    }

    public ResponsePageVo<R> buildFail(ResponseCode code, String msg, int start, int pageSize) {
        this.code = code.code;
        this.msg = msg;
        this.start = start;
        this.pageSize = pageSize;
        return this;
    }

}
