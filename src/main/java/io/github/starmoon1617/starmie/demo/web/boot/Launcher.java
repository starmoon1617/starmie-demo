/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * 启动器
 * 
 * @date 2023-10-24
 * @author Nathan Liao
 */
@SpringBootApplication
@PropertySource(encoding = "UTF-8", ignoreResourceNotFound = true, value = { "classpath:META-INF/props/http.properties" })
@ComponentScan({ "io.github.starmoon1617.starmie.demo" })
@MapperScan("io.github.starmoon1617.starmie.demo.biz.mapper")
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
