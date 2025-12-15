/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.github.starmoon1617.starmie.demo.web.jackson.PresetConditionsDeserializer;
import io.github.starmoon1617.starmie.demo.web.jackson.PresetConditionsSerializer;

/**
 * 预设条件Vo
 * 
 * @date 2023-12-19
 * @author Nathan Liao
 */
@JsonSerialize(using = PresetConditionsSerializer.class)
@JsonDeserialize(using = PresetConditionsDeserializer.class)
public class PresetConditionsVo implements Serializable {

    private static final long serialVersionUID = -9222367472634951152L;

    /**
     * 别名
     */
    private String alias;

    /**
     * 字段
     */
    private String field;

    /**
     * 设置的值,单个
     */
    private String value;

    /**
     * 设置的值, 2个或多个
     */
    private List<String> values;

    /**
     * 类型
     */
    private String type;

    /**
     * 操作符
     */
    private Integer opt;

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     *            the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

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
     * @return the opt
     */
    public Integer getOpt() {
        return opt;
    }

    /**
     * @param opt
     *            the opt to set
     */
    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * @param values
     *            the values to set
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

}
