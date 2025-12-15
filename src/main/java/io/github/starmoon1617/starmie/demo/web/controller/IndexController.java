/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.github.starmoon1617.starmie.core.app.util.SessionUtils;
import io.github.starmoon1617.starmie.core.criterion.BaseCriteria;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.demo.biz.manager.EmployeeManager;
import io.github.starmoon1617.starmie.demo.biz.manager.SystemRoleMenuManager;
import io.github.starmoon1617.starmie.demo.biz.model.Employee;
import io.github.starmoon1617.starmie.demo.biz.model.SystemRoleMenu;
import io.github.starmoon1617.starmie.demo.biz.model.SystemUser;
import io.github.starmoon1617.starmie.demo.common.constant.Constants;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 登录Controller
 * 
 * @date 2023-11-02
 * @author Nathan Liao
 */
@Controller
public class IndexController {
    @Resource
    private EmployeeManager employeeManager;
    @Resource
    private SystemRoleMenuManager systemRoleMenuManager;

    /**
     * Go to index page
     * 
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toIndex(HttpServletRequest request) {
        SystemUser user = new SystemUser();
        user.setLoginName("test");
        user.setType(1);
        SessionUtils.set(request.getSession(), Constants.CURRENT_USER, user);
        // 获取用户的角色,并放入session
        BaseCriteria criteria = BaseCriteria.getInstance();
        List<Employee> employees = employeeManager.find(criteria.addEqual("user", user.getLoginName()));
        if (!CommonUtils.isEmpty(employees)) {
            user.setEmployee(employees.get(0));
            List<SystemRoleMenu> srmList = systemRoleMenuManager.find(criteria.clear().addEqual("roleNo", user.getEmployee().getRole()));
            if (!CommonUtils.isEmpty(srmList)) {
                SessionUtils.set(Constants.CURRENT_PRIVILEGE, srmList.stream().collect(Collectors.toMap(SystemRoleMenu::getMenuNo, Function.identity())));
            }
        }
        SessionUtils.setAttr("contextPath", request.getContextPath());
        return "index";
    }
    
    @RequestMapping("/script/**")
    public String toScript(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        String script = requestPath.substring(requestPath.indexOf("/script/") + 8);
        return script;
    }
}
