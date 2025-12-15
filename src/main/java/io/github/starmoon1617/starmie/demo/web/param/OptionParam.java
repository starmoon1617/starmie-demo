/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.param;

import java.io.Serializable;

/**
 * 选项请求参数
 * 
 * @date 2024-02-20
 * @author Nathan Liao
 */
public class OptionParam implements Serializable {

    private static final long serialVersionUID = -272786193467899773L;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否添加空选项
     */
    private boolean appendNone;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the appendNone
     */
    public boolean isAppendNone() {
        return appendNone;
    }

    /**
     * @param appendNone
     *            the appendNone to set
     */
    public void setAppendNone(boolean appendNone) {
        this.appendNone = appendNone;
    }

}
