/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.code.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.starmoon1617.starmie.generator.core.api.ShellRunner;

/**
 * 代码生产测试
 * 
 * @date 2023-10-25
 * @author Nathan Liao
 */
public class GeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorTest.class);

    public static void main(String[] args) {
        LOGGER.info("Generate Start!");
        try {
            String config = GeneratorTest.class.getClassLoader().getResource("META-INF/generator/generator.properties").getFile();
            String[] arg = { "-configfile", config, "-overwrite" };
            ShellRunner.main(arg);
        } catch (Exception e) {
            LOGGER.error("Generate Error : ", e);
        }
        LOGGER.info("Generate Success!");
    }

}
