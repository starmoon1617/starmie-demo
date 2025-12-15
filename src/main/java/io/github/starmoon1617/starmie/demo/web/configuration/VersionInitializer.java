/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 * 
 */
package io.github.starmoon1617.starmie.demo.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

/**
 * 版本号初始化
 * 
 * @date 2024-04-30
 * @author Nathan Liao
 */
@Component
public class VersionInitializer implements ServletContextInitializer {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setAttribute("appVersion", appVersion);
    }

}
