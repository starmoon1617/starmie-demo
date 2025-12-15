/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.common.enums;

/**
 * 数据操作类型
 * 
 * @date 2023-12-11
 * @author Nathan Liao
 */
public enum DataOpType {

    /**
     * 1 - 新建
     */
    NEW(1, "新建"),
    
    /**
     * 2 - 修改
     */
    UPT(2, "修改"),
    
    /**
     * 3 - 删除
     */
    DEL(3, "删除")
    
    ;
    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 描述
     */
    private String desc;

    /**
     * @param type
     * @param desc
     */
    private DataOpType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

}
