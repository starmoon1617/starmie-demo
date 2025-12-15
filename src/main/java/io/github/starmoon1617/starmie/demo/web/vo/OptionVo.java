/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.vo;

import java.io.Serializable;

/**
 * 用于下拉选项取值
 * 
 * @date 2024-01-08
 * @author Nathan Liao
 */
public class OptionVo implements Serializable {

    /**
     * 值
     */
    private String value;
    
    /**
     * 文本
     */
    private String text;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
    
}
