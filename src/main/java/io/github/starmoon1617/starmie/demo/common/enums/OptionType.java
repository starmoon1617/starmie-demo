/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 * 
 */
package io.github.starmoon1617.starmie.demo.common.enums;

/**
 * Option接口定义
 * 
 * @date 2024-03-18
 * @author Nathan Liao
 */
public interface OptionType {

    /**
     * 返回option的value
     * 
     * @return
     */
    Integer getType();

    /**
     * 返回option的text
     * 
     * @return
     */
    String getText();
}
