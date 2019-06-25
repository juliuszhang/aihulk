package com.aihulk.tech.decision.config;

import org.yaml.snakeyaml.Yaml;

/**
 * @ClassName DecisionConfig
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 20:18
 * @Version 1.0
 */
public class DecisionConfig {

    private static final String DECISION_CONFIG_YAML_PATH = "/decision.yml";

    private static final DecisionConfigEntity config = new Yaml().loadAs(
            DecisionConfig.class.getResourceAsStream(DECISION_CONFIG_YAML_PATH)
            , DecisionConfigEntity.class);

    public static DecisionConfigEntity.MysqlConfig getMysqlConfig() {
        return config.getMysql();
    }

}
