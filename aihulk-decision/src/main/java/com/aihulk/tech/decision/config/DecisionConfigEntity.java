package com.aihulk.tech.decision.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName DecisionConfigEntity
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 20:16
 * @Version 1.0
 */
@Getter
@Setter
public class DecisionConfigEntity {

    private MysqlConfig mysql;

    @Getter
    @Setter
    public static class MysqlConfig {
        private String url;

        private String driver;

        private String username;

        private String password;
    }

}
