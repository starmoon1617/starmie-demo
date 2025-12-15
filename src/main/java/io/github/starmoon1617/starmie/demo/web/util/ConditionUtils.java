/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.util;

import java.util.ArrayList;
import java.util.List;

import io.github.starmoon1617.starmie.core.constant.InterpunctionConstants;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.demo.web.vo.PresetConditionsVo;

/**
 * 
 * 
 * @date 2023-12-19
 * @author Nathan Liao
 */
public class ConditionUtils {

    /**
     * 用于标识values数组的前缀
     */
    private static final String ARR_PREFIX = "@AY";

    private ConditionUtils() {

    }

    /**
     * 将 PresetConditionsVo 列表转换成String用于保存, 多个使用逗号(|)分割, <BR>
     * 格式: {type}~{opt}~{field}~{value}[~{alias}]
     * 
     * @param pcvs
     * @return
     */
    public static String toString(List<PresetConditionsVo> pcvs) {
        if (CommonUtils.isEmpty(pcvs)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        pcvs.forEach(p -> sb.append(toString(p)).append(InterpunctionConstants.VERTICAL_LINE_STR));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 将 PresetConditionsVo 转换成String用于保存 使用~分割属性, <BR>
     * 格式: {type}~{opt}~{field}~{value}[~{alias}]
     * 
     * @param pcv
     * @return
     */
    public static String toString(PresetConditionsVo pcv) {
        StringBuilder sb = new StringBuilder(pcv.getType());
        sb.append(InterpunctionConstants.TILDE_STR).append(pcv.getOpt());
        sb.append(InterpunctionConstants.TILDE_STR).append(pcv.getField());
        sb.append(InterpunctionConstants.TILDE_STR);
        if (!CommonUtils.isEmpty(pcv.getValues())) {
            pcv.getValues().forEach(v -> sb.append(ARR_PREFIX).append(v));
        } else {
            sb.append(pcv.getValue());
        }
        if (CommonUtils.isNotBlank(pcv.getAlias())) {
            sb.append(InterpunctionConstants.TILDE_STR).append(pcv.getAlias());
        }
        return sb.toString();
    }

    /**
     * 将 String 转成 PresetConditionsVo 列表 <BR>
     * 格式: {type}~{opt}~{field}~{value}[~{alias}]
     * 
     * @param str
     * @return
     */
    public static List<PresetConditionsVo> toConditionList(String str) {
        List<PresetConditionsVo> pcvs = new ArrayList<>();
        if (!CommonUtils.isNotBlank(str)) {
            return pcvs;
        }
        String[] pArr = CommonUtils.split(str, InterpunctionConstants.VERTICAL_LINE_STR);
        for (String p : pArr) {
            if (!CommonUtils.isNotBlank(p)) {
                continue;
            }
            pcvs.add(toCondition(p));
        }
        return pcvs;
    }

    /**
     * 将 String 转成 PresetConditionsVo <BR>
     * 格式: {type}~{opt}~{field}~{value}[~{alias}]
     * 
     * @param str
     * @return
     */
    public static PresetConditionsVo toCondition(String str) {
        String[] cArr = CommonUtils.split(str, InterpunctionConstants.TILDE_STR);
        PresetConditionsVo pcv = new PresetConditionsVo();
        pcv.setType(cArr[0]);
        pcv.setOpt(Integer.valueOf(cArr[1]));
        pcv.setField(cArr[2]);
        String v = cArr[3];
        if (v.startsWith(ARR_PREFIX)) {
            pcv.setValues(CommonUtils.splitToList(v.substring(3), ARR_PREFIX));
        } else {
            pcv.setValue(v);
        }
        if (cArr.length >= 5) {
            pcv.setAlias(cArr[4]);
        }
        return pcv;
    }

}
