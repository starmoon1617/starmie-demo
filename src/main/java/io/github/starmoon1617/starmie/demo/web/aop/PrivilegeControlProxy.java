/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.starmoon1617.starmie.core.app.util.SessionUtils;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.demo.biz.model.SystemRoleMenu;
import io.github.starmoon1617.starmie.demo.biz.model.SystemUser;
import io.github.starmoon1617.starmie.demo.common.constant.Constants;
import io.github.starmoon1617.starmie.demo.web.annotation.ExtraParam;
import io.github.starmoon1617.starmie.demo.web.annotation.Menu;
import io.github.starmoon1617.starmie.demo.web.annotation.Privilege;

/**
 * 权限控制
 * 
 * @date 2023-12-21
 * @author Nathan Liao
 */
@Aspect
@Component("privilegeControlProxy")
@Order(Ordered.LOWEST_PRECEDENCE)
public class PrivilegeControlProxy {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeControlProxy.class);
    
    @SuppressWarnings("unchecked")
    @Before("@annotation(io.github.starmoon1617.starmie.demo.web.annotation.Privilege) && @annotation(privilege)")
    public void checkPrivilege(JoinPoint joinPoint, Privilege privilege) {
        LOGGER.info("To check Privilege !!!");
        SystemUser user = SessionUtils.get(Constants.CURRENT_USER, SystemUser.class);
        if (user == null) {
            throw new RuntimeException("No Permissions !");
        }
        if ("admin".equals(user.getLoginName())) {
            return ;
        }
        // 获取当前的权限
        Map<String, SystemRoleMenu> srmMap = SessionUtils.get(Constants.CURRENT_PRIVILEGE, Map.class);
        if (CommonUtils.isEmpty(srmMap)) {
            throw new RuntimeException("No Permissions !");
        }
        Menu menu = joinPoint.getTarget().getClass().getAnnotation(Menu.class);
        if (menu == null) {
            throw new RuntimeException("No Permissions !");
        }
        SystemRoleMenu srm = srmMap.get(menu.value());
        if (srm == null) {
            throw new RuntimeException("No Permissions !");
        }
        
        MethodSignature ms = ((MethodSignature)joinPoint.getSignature());
        ExtraParam ep = ms.getMethod().getAnnotation(ExtraParam.class);
        
    }
    
    @SuppressWarnings("unchecked")
    @Before("execution(* *..demo.web.controller.*Controller.toVueList(..)) || execution(* *..demo.web.controller.*Controller.toList(..))")
    public void setPrivilege(JoinPoint joinPoint) {
        LOGGER.info("To list page !!!");
        // 获取当前用户
        SystemUser user = SessionUtils.get(Constants.CURRENT_USER, SystemUser.class);
        if (user == null) {
            throw new RuntimeException("No Permissions !");
        }
        if ("admin".equals(user.getLoginName())) {
            // 管理员
            SystemRoleMenu srm = new SystemRoleMenu();
            srm.setPublicFlag(1);
            srm.setExportFlag(1);
            srm.setCreateFlag(1);
            srm.setImportFlag(1);
            srm.setUpdateFlag(1);
            srm.setDeleteFlag(1);
            SessionUtils.setAttr("privilege", srm);
            return ;
        }
        // 获取当前的权限
        Map<String, SystemRoleMenu> srmMap = SessionUtils.get(Constants.CURRENT_PRIVILEGE, Map.class);
        if (CommonUtils.isEmpty(srmMap)) {
            throw new RuntimeException("No Permissions !");
        }
        Menu menu = joinPoint.getTarget().getClass().getAnnotation(Menu.class);
        if (menu == null) {
            throw new RuntimeException("No Permissions !");
        }
        SystemRoleMenu srm = srmMap.get(menu.value());
        if (srm == null) {
            throw new RuntimeException("No Permissions !");
        }
        SessionUtils.setAttr("privilege", srm);
    }
    
}
