/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 自定义组件控制器
 * 
 * @date 2024-01-09
 * @author Nathan Liao
 */
@Controller
@RequestMapping("/components")
public class StarmieComponentController {
    
    /**
     * Go to list page
     * 
     * @return
     */
    @RequestMapping(value = "/{name}.vue", method = RequestMethod.GET)
    public String toComponent(@PathVariable("name") String name) {
        return "components/" + name;
    }
}
