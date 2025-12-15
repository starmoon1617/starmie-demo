/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.common.enums;

/**
 * 权限类型
 * 
 * @date 2023-12-19
 * @author Nathan Liao
 */
public enum PrivilegeType {

    /**
     * 1 - 查看
     */
    V(1, "查看"),
    
    /**
     * 2, 导出
     */
    E(2, "导出"),
    
    /**
     * 3 - 创建
     */
    C(3, "创建"),
    
    /**
     * 4 - 导入
     */
    I(4, "导入"),
    
    /**
     * 5 - 更新
     */
    U(5, "更新"),
    
    /**
     * 6 - 删除
     */
    D(6, "删除");
    /**
     * 类型
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
    private PrivilegeType(Integer type, String desc) {
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
