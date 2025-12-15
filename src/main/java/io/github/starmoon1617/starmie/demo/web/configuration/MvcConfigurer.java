/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.starmoon1617.starmie.core.app.convert.DateConverter;
import io.github.starmoon1617.starmie.core.constant.InterpunctionConstants;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.demo.web.intercept.LoginInterceptor;
import jakarta.annotation.Resource;

/**
 * MVC 配置
 * 
 * @date 2023-10-24
 * @author Nathan Liao
 */
@Component
public class MvcConfigurer implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;
    
    @Value("${spring.http.interceptor.exclude}")
    private String exclude;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        ir.excludePathPatterns(CommonUtils.split(exclude, InterpunctionConstants.COMMA_STR));
    }
}
