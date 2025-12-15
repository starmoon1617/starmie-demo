/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义端口和context path
 * 
 * @date 2023-10-24
 * @author Nathan Liao
 */
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${http.port:8080}")
    private int port;

    @Value("${http.context:/starmie-demo}")
    private String context;
    
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(port);
        factory.setContextPath(context);
        factory.setRegisterDefaultServlet(false);
    }

}
