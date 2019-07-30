package com.aihulk.tech.manage.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: ExecFactVo
 * @projectName aihulk
 * @description: ExecFactVo
 * @date 2019-07-29 16:06
 */
@Data
public class ExecFactVo {

    private Integer factId;

    private String script;

    private String scriptType;

    private Map<String, Object> data;


}
