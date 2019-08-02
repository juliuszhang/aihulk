package com.aihulk.tech.decision;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Starter
 * @Description: Starter
 * @date 2019/8/1
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.aihulk.tech.entity.mapper")
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
