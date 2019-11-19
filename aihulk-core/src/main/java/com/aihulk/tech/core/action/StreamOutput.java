package com.aihulk.tech.core.action;

import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;

/**
 * @author zhangyibo
 * @title: StreamOutput
 * @projectName aihulk
 * @description: 通过流的形式向外输出，默认输出到控制台
 * @date 2019-07-0514:34
 */
@Getter
@Setter
public class StreamOutput extends Action {

    private Object outPutValue;

    private OutputStream os = System.out;

}
