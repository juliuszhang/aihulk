package com.aihulk.tech.decision.config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

/**
 * @ClassName DecisionConfigEntity
 * @Description DecisionConfigEntity
 * @Author yibozhang
 * @Date 2019/5/1 20:16
 * @Version 1.0
 */
@Getter
@Setter
public class DecisionConfigEntity {

    private MysqlConfig mysql;

    private ServerConfig server;

    @Getter
    @Setter
    public static class MysqlConfig {
        private String url;

        private String driver;

        private String username;

        private String password;
    }

    @Getter
    @Setter
    public static class ServerConfig {
        private int port;

        private String basePackage;
    }

}
