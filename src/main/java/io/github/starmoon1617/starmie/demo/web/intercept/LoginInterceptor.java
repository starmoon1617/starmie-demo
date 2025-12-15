/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.intercept;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.starmoon1617.starmie.core.app.util.SessionUtils;
import io.github.starmoon1617.starmie.core.util.EntityUtils;
import io.github.starmoon1617.starmie.demo.biz.model.SystemUser;
import io.github.starmoon1617.starmie.demo.common.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 登录拦截
 * 
 * @date 2023-11-02
 * @author Nathan Liao
 */
@Component("loginInterceptor")
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SystemUser user = SessionUtils.get(request.getSession(), Constants.CURRENT_USER, SystemUser.class);
        if (user == null) {
            // 当前没有登录用户,跳转到登录页面
            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
                // ajax请求
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter pw = response.getWriter();
                Map<String, Object> retVal = new HashMap<>();
                retVal.put("redirectToLogin", true);
                pw.write(EntityUtils.toNonNJson(retVal));
                pw.close();
                response.flushBuffer();
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
            return false;
        }
        return true;
    }
}
