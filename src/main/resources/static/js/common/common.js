/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 */
;
/**
 * Common function for data request
 */
(function(win, factory) {
    if (typeof exports === "object") {
        // CommonJS
        module.exports = exports = factory();
    } else if (typeof define === "function" && define.amd) {
        // AMD
        define([], factory);
    } else {
        // Global (browser)
        win.Common = factory();
    }
}(this, function() {
    var common = {};
    
    /**
     * 调用方法
     */
    common.call = function(func, vars) {
        if(func && typeof func === 'function') {
            func(vars);
        }
    };
    
    /**
     * POST方法调用
     */
    common.post = function(url, params, callBack, eCallBack) {
        axios.post(url, params)
        .then(function(response) {
            common.call(callBack, response);
        })
        .catch(function(error) { // 请求失败处理
            common.call(eCallBack, error);
        });
    };

    /**
     * 请求查询数据
     */
    common.searchDatas = function(url, searchForm, pagination, callBack, eCallBack) {
        let params = new URLSearchParams();
        if (searchForm) {
            for (let p in searchForm) {
                if (searchForm[p]) {
                    params.append(p, searchForm[p]);
                }
            }
        }
        if (pagination) {
            params.append("_FL0", pagination.itemsPerPage);
            params.append("_FL1", pagination.itemsPerPage * (pagination.page - 1));
        }
        common.post(url, params, callBack, eCallBack);
    };
    /**
     * 请求 保存/更新/删除 数据
     */
    common.updateData = function(url, item, callBack, eCallBack) {
        let params = new URLSearchParams();
        if (item) {
            for (let p in item) {
                if (item[p]) {
                    params.append(p, item[p]);
                }
            }
        }
        common.post(url, params, callBack, eCallBack);
    };
    /**
     * 请求导出数据(excel)
     */
    common.exportDatas = function(url, searchForm, exportInfo, heads, eCallBack) {
        let formData = new FormData();
        if (searchForm) {
            for (let p in searchForm) {
                if (searchForm[p]) {
                    formData.append(p, searchForm[p]);
                }
            }
        }
        let name = (exportInfo && exportInfo.fileName) ? exportInfo.fileName : "data_export";
        formData.append("fileName", name);
        let dhs = new Array();
        if (heads) {
            let hasExclude = (exportInfo) && (exportInfo.excludeInfos) && exportInfo.excludeInfos.length > 0;
            for (let i = 0; i < heads.length; i++) {
                let head = {};
                if(hasExclude && exportInfo.excludeInfos.includes(heads[i].value)) {
                    continue;
                }
                head.title = heads[i].text;
                head.field = heads[i].value;
                head.width = heads[i].width;
                dhs.push(head);
            }
            if (exportInfo && exportInfo.extraInfos) {
                for(let i = 0; i < exportInfo.extraInfos.length; i++) {
                    dhs.push(exportInfo.extraInfos[i]);
                }
            }
            formData.append("heads", JSON.stringify(dhs));
        }
        axios({
            method: 'post',
            url: url,
            data: formData,
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            responseType: 'blob'
        }).then(function(response) {
            let blob = response.data;
            document.getElementById("downLoadFileHref").href = URL.createObjectURL(blob);
            document.getElementById("downLoadFileHref").download = name;
            document.getElementById("downLoadFileHref").click();
            setTimeout(function() { // 延时释放
                URL.revokeObjectURL(blob); // 用URL.revokeObjectURL()来释放这个object URL
            }, 5000);
            return true;
        }).catch(function(error) {
            common.call(eCallBack, error);
        });
    };
    /**
     * 请求导入数据(excel)
     */
    common.importDatas = function(url, file, heads, callBack, eCallBack) {
        let formData = new FormData();
        formData.append("uploadFile", file);
        formData.append("heads", heads);
        common.post(url, formData, callBack, eCallBack);
    };
    /**
     * 请求菜单数据
     */
    common.loadMenus = function(baseUrl, callBack, eCallBack) {
        common.post(baseUrl + 'navigation_module/navigations', null, callBack, eCallBack);
    };
    /**
     * 请求当前页面的预设查询条件
     */
    common.updatePreset = function(baseUrl, searchForm, preset, index, type) {
        let formData = new URLSearchParams();
        if (preset) {
            for (let p in preset) {
                if (preset[p]) {
                    formData.append(p, preset[p]);
                }
            }
        }
        formData.append("opt", type);
        common.post(baseUrl + '/preset', formData, function(resp) {
            if(!resp.code && resp.data) {
                if (type == 3) {
                    if (searchForm.conds && searchForm.conds.length > 0) {
                        searchForm.conds.splice(index, 1);
                    }
                } else if (type == 1) {
                    searchForm.conds.push(resp.data.data);
                }
            }
        }, function (e){
            console.info(e);
        });
    };
    /**
     * 处理预设
     */
    common.loadPresets = function(baseUrl, searchForm) {
        common.post(baseUrl + '/listPresets', null, function(resp) {
            if(!resp.code && resp.data) {
                if(resp.data.data.length > 0) {
                    searchForm.items = resp.data.data;
                }
            }
        }, function (e){
            console.info(e);
        });
    };
    /**
     * 将当前的查询条件,设置到searchPreset.item
     */
    common.setSearchToPreset = function(searchForm, presetItem) {
        presetItem = Object.assign({}, { id:'', name:'', pcvs:[], flag:false });
        if(searchForm && searchForm.items) {
            for(let p in searchForm.items) {
                let c = searchForm.items[p];
                if (c.type == 'D') {
                    if (c.value && c.value.length > 0) {
                        let pv = Object.assign({}, {field:'', opt:10,type:'D',value:[]});
                        pv.field = p;
                        pv.opt = c.opt;
                        pv.type = c.type;
                        pv.value.push(c.value[0]);
                        if (c.value.length > 1) {
                            pv.value.push(c.value[1]);
                        }
                        presetItem.pcvs.push(pv);
                    }
                    continue;
                }
                if (c.value || c.value === 0) {
                    let pv = Object.assign({}, {field:'', opt:0,type:'',value:''});
                        pv.field = p;
                        pv.opt = c.opt;
                        pv.type = c.type;
                        pv.value = c.value;
                        presetItem.pcvs.push(pv);
                }
            }
        }
    };
    /**
     * 从text种获取对应的值, texts为[{text:'', value:''}..]
     */
    common.getTexts = function(v, texts) {
        if(!texts || texts.length <= 0) {
            return v;
        }
        for(let t in texts) {
            if (v == t.value) {
                return t.text;
            }
        }
        return v;
    };
    /**
     * 格式化 预设
     */
    common.formatPreset = function(conditions, labels, texts) {
        if(!conditions || conditions.length <= 0) {
            return '';
        }
        let data = "";
        conditions.forEach(e => {
            let text = texts[e.field];
            data = data + labels[e.field];
            if (e.type == 'D') {
                if(e.value.length > 0) {
                    data = data + '>="' + common.getTexts(e.value[0], text)  + '"';
                    if(e.value.length > 1) {
                        data = '并且<="' + common.getTexts(e.value[1], text) + '"';
                    }
                    data = data + '; ';
                }
            } else {
                if(e.opt === 0) {
                    data = data + '=';
                } else if(e.opt === 2) {
                    data = data + '>';
                } else if(e.opt === 3) {
                    data = data + '>=';
                } else if(e.opt === 4) {
                    data = data + '<';
                } else if(e.opt === 5) {
                    data = data + '<=';
                }
                data = data + '"' + common.getTexts(e.value, text) + '"; ';
            }
        });
        return data;
    };
    /**
     * 获取某年,月的最大天数
     */
    common.getMaxDate = function(year, month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
            return 29;
        }
        return 28;
    };
    /**
     * 初始化日期面板
     */
    common.initDays = function(weeks, panelDate, currentDate, selectedDays) {
        let nDays = new Array();
        let pDate = new Date(panelDate.year, (panelDate.month - 1), 1);
        let dayFixed = pDate.getDay() - 1;
        let day = {};
        let islastSat = false;
        let currentYear = currentDate.getFullYear();
        let currentMonth = (currentDate.getMonth() + 1);
        let currentDay = currentDate.getDate();
        
        let selectType = 0;
        let selectDate1 = '';
        let selectDate2 = '';
        if (selectedDays && selectedDays.length > 0) {
            selectType = selectedDays.length;
            selectDate1 = selectedDays[0];
            if (selectedDays.length > 1 ) {
                selectDate2 = selectedDays[1];
            }
        }
        let maxDate = common.getMaxDate(panelDate.year, panelDate.month);
        for(let i = 1; i <= maxDate; i++) {
            islastSat = false;
            let v = {value:i};
            if (panelDate.year == currentYear && panelDate.month == currentMonth && i == currentDay) {
               v.current = 1;
            }
            // 判断是否在选中区域
            if (selectType == 1) {
                let d = panelDate.year + '-' + ( panelDate.month < 10 ? '0' : '' ) + panelDate.month + '-' + ( i < 10 ? '0' : '' ) + i;
                v.selected = (d === selectDate1);
            } else if (selectType == 2) {
                let d = panelDate.year + '-' + ( panelDate.month < 10 ? '0' : '' ) + panelDate.month + '-' + ( i < 10 ? '0' : '' ) + i;
                if (d >= selectDate1 && d <= selectDate2) {
                    v.selected = 1;
                }
            }
            let idx = (dayFixed + ( i % 7) + 7 ) % 7;
            day[weeks[idx].value] = v;
            if (idx == 6) {
                nDays.push(day);
                day = {};
                islastSat = true;
            }
        }
        if (!islastSat) {
            nDays.push(day);
        }
        return nDays;
    };
    common.log = function(date) {
        console.info(date);
        console.info(date.inputYear);
        console.info(date.inputYear);
    };
    /**
     * 加载下拉列表数据
     */
    common.loadOptions = function(baseUrl, type, appendNone, callBack, eCallBack) {
        let url = baseUrl + '/data/list_multi_options';
        let params = new URLSearchParams();
        params.append('appendNone', appendNone);
        if (type instanceof Array) {
            params.append('types', type.join(','));
        } else {
            params.append('type', type);
            url = baseUrl + '/data/list_options';
        }
        common.post(url, params, callBack, eCallBack);
    };
    /**
     * 通过JSON的请求加载下拉列表数据
     */
    common.loadJsonOptions = function(baseUrl, types, appendNoneTypes, callBack, eCallBack) {
        let params = new Array();
        if (types) {
            if(types instanceof Array) {
                types.forEach(t => params.push({type:t,appendNone:false}));
            } else {
                params.push({type:types,appendNone:false});
            }
        }
        if (appendNoneTypes) {
            if(appendNoneTypes instanceof Array) {
                appendNoneTypes.forEach(t => params.push({type:t,appendNone:true}));
            } else {
                params.push({type:appendNoneTypes,appendNone:true});
            }
        }
        if (params.length <= 0) {
            return ;
        }
        common.post(baseUrl + '/data/list_options_json', params, callBack, eCallBack);
    }

    return common;
}));