/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 * 
 */
package io.github.starmoon1617.starmie.demo.web.option;

import java.util.List;
import java.util.Map;

import io.github.starmoon1617.starmie.demo.web.vo.OptionVo;

/**
 * Option 查找接口
 * 
 * @date 2024-03-18
 * @author Nathan Liao
 */
public interface OptionFinder {

    /**
     * 返回可以查找的option类型
     * 
     * @return
     */
    List<String> types();
    
    /**
     * 根据类型和参数查找option列表
     * @param type
     * @param params
     * @return
     */
    List<OptionVo> listOptions(String type, Map<String, Object> params);
    
}
