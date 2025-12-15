/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.common.enums;

/**
 * 合同类型
 * 
 * @date 2023-12-11
 * @author Nathan Liao
 */
public enum ContractType implements OptionType {

    
    /**
     * 1 - 采购
     */
    PROCUREMENT(1, "PO", "采购"),
    /**
     * 2 - 销售
     */
    SALE(2, "SO", "销售"),
    /**
     * 3 - 自用
     */
    SELF_USE(3, "SC", "自用"),
    /**
     * 4 - 产耗
     */
    CONSUMPTION(4, "MC", "产耗"),
    /**
     * 5 - 产出
     */
    OUT_PUT(5, "MP", "产出"),
    /**
     * 6 - 研发
     */
    DEVELOPMENT(6, "RP", "研发")
    ;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 单据编码前缀
     */
    private String prefix;

    /**
     * 描述
     */
    private String text;

    /**
     * @param type
     * @param prefix
     * @param text
     */
    private ContractType(Integer type, String prefix, String text) {
        this.type = type;
        this.prefix = prefix;
        this.text = text;
    }

    /**
     * @return the type
     */
    @Override
    public Integer getType() {
        return type;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return the text
     */
    @Override
    public String getText() {
        return text;
    }

}
