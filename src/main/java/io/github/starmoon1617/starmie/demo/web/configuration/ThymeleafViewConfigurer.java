/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.configuration;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import io.github.starmoon1617.starmie.demo.common.constant.Constants;

/**
 * Thymeleaf配置,增加.vue,.js的视图处理
 * 
 * @date 2023-10-31
 * @author Nathan Liao
 */
@Configuration
public class ThymeleafViewConfigurer implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ThymeleafViewResolver scriptThymeleafViewResolver(ThymeleafProperties properties, SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding(Constants.UTF_8);
        resolver.setContentType(Constants.APPLICATION_JS_VALUE);
        resolver.setViewNames(new String[] { "*.js" });
        resolver.setCache(properties.isCache());
        return resolver;
    }

    @Bean
    public SpringResourceTemplateResolver scriptTemplateResolver(ThymeleafProperties properties) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix("classpath:/WEB-INF/script/templates/");
        resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
        resolver.setCharacterEncoding(Constants.UTF_8);
        resolver.setCheckExistence(true);
        resolver.setCacheable(properties.isCache());
        resolver.setOrder(0);
        return resolver;
    }

}
