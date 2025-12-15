/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 预设条件VO
 * 
 * @date 2023-12-19
 * @author Nathan Liao
 */
public class PresetVo implements Serializable {

    private static final long serialVersionUID = 2074480616538722899L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 默认标识
     */
    private Boolean flag;

    /**
     * 条件列表
     */
    private List<PresetConditionsVo> pcvs;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the flag
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     * @param flag
     *            the flag to set
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the pcvs
     */
    public List<PresetConditionsVo> getPcvs() {
        return pcvs;
    }

    /**
     * @param pcvs
     *            the pcvs to set
     */
    public void setPcvs(List<PresetConditionsVo> pcvs) {
        this.pcvs = pcvs;
    }

}
