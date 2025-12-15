/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.starmoon1617.starmie.core.app.util.SessionUtils;
import io.github.starmoon1617.starmie.core.app.web.AbstractBaseController;
import io.github.starmoon1617.starmie.core.base.BaseDto;
import io.github.starmoon1617.starmie.core.base.BaseEntity;
import io.github.starmoon1617.starmie.core.criterion.BaseCriteria;
import io.github.starmoon1617.starmie.core.page.Pagination;
import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.core.util.DateUtils;
import io.github.starmoon1617.starmie.core.util.EntityUtils;
import io.github.starmoon1617.starmie.demo.biz.manager.SystemPresetSearchManager;
import io.github.starmoon1617.starmie.demo.biz.model.SystemPresetSearch;
import io.github.starmoon1617.starmie.demo.biz.model.SystemUser;
import io.github.starmoon1617.starmie.demo.common.constant.Constants;
import io.github.starmoon1617.starmie.demo.common.enums.DataOpType;
import io.github.starmoon1617.starmie.demo.common.enums.PrivilegeType;
import io.github.starmoon1617.starmie.demo.web.annotation.Menu;
import io.github.starmoon1617.starmie.demo.web.annotation.Privilege;
import io.github.starmoon1617.starmie.demo.web.util.ConditionUtils;
import io.github.starmoon1617.starmie.demo.web.vo.PresetConditionsVo;
import io.github.starmoon1617.starmie.demo.web.vo.PresetVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Vue base controller
 * 
 * @date 2023-11-14
 * @author Nathan Liao
 */
public abstract class AbstractVueBaseController<E extends BaseEntity<ID, U>, ID extends Serializable, U extends Serializable>
        extends AbstractBaseController<E, ID, U> {

    @Resource
    private SystemPresetSearchManager systemPresetSearchManager;
    
    @Override
    @Privilege(PrivilegeType.E)
    public void doExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doExport(request, response);
    }
    
    @Override
    @Privilege(PrivilegeType.I)
    public BaseDto<String> doImport(HttpServletRequest request) {
        return super.doImport(request);
    }
    
    @Override
    @Privilege(PrivilegeType.D)
    public BaseDto<E> delete(E e) {
        return super.delete(e);
    }
    
    @Override
    @Privilege(PrivilegeType.U)
    public BaseDto<E> update(E e) {
        return super.update(e);
    }
    
    @Override
    @Privilege(PrivilegeType.C)
    public BaseDto<E> save(E e) {
        return super.save(e);
    }

    @Override
    @Privilege(PrivilegeType.V)
    public BaseDto<Pagination<E>> list(HttpServletRequest request) {
        return super.list(request);
    }

    /**
     * Go to list page
     * 
     * @return
     */
    @RequestMapping(value = "/toList.vue", method = RequestMethod.GET)
    public String toVueList() {
        return super.toList();
    }

    /**
     * 获取菜单编码
     * 
     * @return
     */
    protected String getMenuNo() {
        Menu menu = getClass().getAnnotation(Menu.class);
        if (menu == null) {
            return null;
        }
        return menu.value();
    }

    /**
     * 获取预设条件列表
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPresets", method = RequestMethod.POST)
    public BaseDto<List<PresetVo>> listPresetConditions(HttpServletRequest request) {
        String menuNo = getMenuNo();
        if (!CommonUtils.isNotBlank(menuNo)) {
            return getSuccess(new ArrayList<>());
        }
        SystemUser user = SessionUtils.get(Constants.CURRENT_USER, SystemUser.class);
        List<SystemPresetSearch> spsList = systemPresetSearchManager
                .findConditions(BaseCriteria.getInstance().addEqual("menuNo", menuNo).addEqual("createBy", user.getLoginName()));
        if (CommonUtils.isEmpty(spsList)) {
            return getSuccess(new ArrayList<>());
        }
        List<PresetVo> pvList = new ArrayList<>(spsList.size());
        spsList.forEach(sps -> {
            PresetVo pv = new PresetVo();
            pv.setId(sps.getId());
            pv.setName(sps.getName());
            pv.setPcvs(ConditionUtils.toConditionList(sps.getConditions()));
            pvList.add(pv);
        });
        return getSuccess(pvList);
    }

    /**
     * 操作预设条件
     * 
     * @param sps
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/preset", method = RequestMethod.POST)
    public BaseDto<PresetVo> updatePresetConditions(HttpServletRequest request) {
        PresetVo pv = new PresetVo();
        String conditions = request.getParameter("conditions");
        List<PresetConditionsVo> pcvs = EntityUtils.fromJsonToList(conditions, PresetConditionsVo.class);
        SystemPresetSearch sps = new SystemPresetSearch();
        String idStr = request.getParameter("id");
        sps.setId((CommonUtils.isNotBlank(idStr) ? Integer.valueOf(idStr) : null));
        sps.setName(request.getParameter("name"));
        sps.setConditions(ConditionUtils.toString(pcvs));
        sps.setStatus(1);
        
        pv.setId(sps.getId());
        pv.setPcvs(pcvs);
        pv.setName(sps.getName());
        
        String menuNo = getMenuNo();
        if (!CommonUtils.isNotBlank(menuNo)) {
            return getSuccess(pv);
        }
        Integer opt = Integer.valueOf(request.getParameter("opt"));
        if (DataOpType.DEL.getType().equals(opt)) {
            // 删除
            systemPresetSearchManager.delete(sps);
            return getSuccess(pv);
        }
        SystemUser user = SessionUtils.get(Constants.CURRENT_USER, SystemUser.class);
        sps.setUpdateBy(user.getLoginName());
        sps.setUpdateTime(DateUtils.getCurrentDate());
        if (DataOpType.NEW.getType().equals(opt)) {
            sps.setCreateBy(sps.getUpdateBy());
            sps.setCreateTime(sps.getUpdateTime());
            systemPresetSearchManager.save(sps);
            pv.setId(sps.getId());
        } else {
            systemPresetSearchManager.update(sps);
        }
        return getSuccess(pv);
    }
}
