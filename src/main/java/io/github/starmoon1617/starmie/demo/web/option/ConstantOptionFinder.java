/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 * 
 */
package io.github.starmoon1617.starmie.demo.web.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.starmoon1617.starmie.demo.common.enums.ContractType;
import io.github.starmoon1617.starmie.demo.common.enums.OptionType;
import io.github.starmoon1617.starmie.demo.web.vo.OptionVo;
import jakarta.annotation.PostConstruct;

/**
 * 常量Option的类型查找器
 * 
 * @date 2024-03-18
 * @author Nathan Liao
 */
@Service("constantOptionFinder")
public class ConstantOptionFinder implements OptionFinder {

    private Map<String, List<OptionVo>> optionMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        optionMap.put("ContractType", toOptions(ContractType.class));
    }

    protected <T extends Enum<T> & OptionType> List<OptionVo> toOptions(Class<T> eClass) {
        List<OptionVo> ovs = new ArrayList<>();
        if (eClass == null) {
            return ovs;
        }
        T[] ts = eClass.getEnumConstants();
        if (ts == null || ts.length <= 0) {
            return ovs;
        }
        for (T t : ts) {
            OptionVo ov = new OptionVo();
            ov.setText(t.getText());
            ov.setValue(t.getType().toString());
            ovs.add(ov);
        }
        return ovs;
    }

    @Override
    public List<String> types() {
        List<String> list = new ArrayList<>();
        list.add("ContractType");
        return list;
    }

    @Override
    public List<OptionVo> listOptions(String type, Map<String, Object> params) {
        return optionMap.get(type);
    }

}
