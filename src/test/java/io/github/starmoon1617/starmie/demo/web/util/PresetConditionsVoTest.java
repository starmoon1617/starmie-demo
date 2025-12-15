/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.util;

import java.util.ArrayList;
import java.util.List;

import io.github.starmoon1617.starmie.core.util.EntityUtils;
import io.github.starmoon1617.starmie.demo.web.vo.PresetConditionsVo;

/**
 * 
 * 
 * @date 2023-12-29
 * @author Nathan Liao
 */
public class PresetConditionsVoTest {
    
    public static void main(String[] args) {
        
        List<PresetConditionsVo> pcvs = new ArrayList<>();
        
        PresetConditionsVo pcv = new PresetConditionsVo();
        pcv.setAlias("a");
        pcv.setField("user");
        pcv.setOpt(0);
        pcv.setType("S");
        pcv.setValue("abc123");
        
        pcvs.add(pcv);
        
        PresetConditionsVo pcv1 = new PresetConditionsVo();
        pcv1.setField("createDate");
        pcv1.setOpt(0);
        pcv1.setType("D");
        List<String> dates = new ArrayList<>();
        dates.add("2023-12-12");
        dates.add("2023-12-15");
        pcv1.setValues(dates);
        
        pcvs.add(pcv1);
        
        String json = EntityUtils.toNonNJson(pcvs);
        System.out.println(json);
        List<PresetConditionsVo> pcvList = EntityUtils.fromJsonToList(json, PresetConditionsVo.class);
        System.out.println(EntityUtils.toNonNJson(pcvList));
        String msg = ConditionUtils.toString(pcvList);
        System.out.println(msg);
        System.out.println(EntityUtils.toNonNJson(ConditionUtils.toConditionList(msg)));
    }
    
}
