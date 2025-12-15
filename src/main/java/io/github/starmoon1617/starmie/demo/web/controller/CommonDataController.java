/*
 * Copyright (c) 2024, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.starmoon1617.starmie.core.app.base.BaseController;
import io.github.starmoon1617.starmie.core.base.BaseDto;
import io.github.starmoon1617.starmie.core.constant.InterpunctionConstants;
import io.github.starmoon1617.starmie.core.criterion.BaseCriteria;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.core.util.EntityUtils;
import io.github.starmoon1617.starmie.demo.biz.manager.UserManager;
import io.github.starmoon1617.starmie.demo.biz.model.User;
import io.github.starmoon1617.starmie.demo.web.param.OptionParam;
import io.github.starmoon1617.starmie.demo.web.vo.OptionVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 通用的选项控制器
 * 
 * @date 2024-02-04
 * @author Nathan Liao
 */
@Controller
@RequestMapping("/data")
public class CommonDataController extends BaseController {

    @Resource
    private UserManager userManager;
    
    @ResponseBody
    @RequestMapping(value = "/list_options_json", method = RequestMethod.POST)
    public BaseDto<Map<String, List<OptionVo>>> listMultiOptions(@RequestBody List<OptionParam> params) {
        Map<String, List<OptionVo>> pvsMap = new HashMap<>();
        if (CommonUtils.isEmpty(params)) {
            return getSuccess(pvsMap);
        }
        for (OptionParam op : params) {
            if ("status".equals(op.getType())) {
                pvsMap.put("status", appendNone(getStatusList(), op.isAppendNone()));
            } else if ("user".equals(op.getType())) {
                pvsMap.put("user", appendNone(getUserList(), op.isAppendNone()));
            }
        }
        return getSuccess(pvsMap);
    } 

    @ResponseBody
    @RequestMapping(value = "/list_multi_options", method = RequestMethod.POST)
    public BaseDto<Map<String, List<OptionVo>>> listMultiOptions(HttpServletRequest request) {
        Map<String, List<OptionVo>> pvsMap = new HashMap<>();
        boolean appendNone = Boolean.valueOf(request.getParameter("appendNone"));
        String type = request.getParameter("types");
        Set<String> types = CommonUtils.splitToSet(type, InterpunctionConstants.COMMA_STR);
        for (String t : types) {
            if ("status".equals(t)) {
                pvsMap.put("status", appendNone(getStatusList(), appendNone));
            } else if ("user".equals(t)) {
                pvsMap.put("user", appendNone(getUserList(), appendNone));
            }
        }
        return getSuccess(pvsMap);
    }

    /**
     * 获取option列表
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list_options", method = RequestMethod.POST)
    public BaseDto<List<OptionVo>> listOptions(HttpServletRequest request) {
        final List<OptionVo> pvs = new ArrayList<>();
        String type = request.getParameter("type");
        boolean appendNone = Boolean.valueOf(request.getParameter("appendNone"));
        if ("status".equals(type)) {
            pvs.addAll(getStatusList());
        } else if ("user".equals(type)) {
            pvs.addAll(getUserList());
        }
        return getSuccess(appendNone(pvs, appendNone));
    }

    private List<OptionVo> appendNone(final List<OptionVo> pvs, boolean appendNone) {
        if (appendNone && !CommonUtils.isEmpty(pvs)) {
            pvs.add(0, EntityUtils.fromJson("{\"text\":\"请选择\",\"value\":\"\"}", OptionVo.class));
        }
        return pvs;
    }

    private List<OptionVo> getStatusList() {
        return EntityUtils.fromJsonToList("[{\"text\":\"有效\",\"value\":\"1\"},{\"text\":\"无效\",\"value\":\"0\"}]", OptionVo.class);
    }

    private List<OptionVo> getUserList() {
        final List<OptionVo> pvs = new ArrayList<>();
        List<User> users = userManager.find(BaseCriteria.getInstance());
        if (!CommonUtils.isEmpty(users)) {
            users.forEach(u -> {
                OptionVo p = new OptionVo();
                p.setText(u.getName());
                p.setValue(u.getLoginName());
                pvs.add(p);
            });
        }
        return pvs;
    }
}
