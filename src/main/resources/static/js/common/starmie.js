/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 */
;
(function(win, factory) {
    if (typeof exports === "object") {
        // CommonJS
        module.exports = exports = factory();
    } else if (typeof define === "function" && define.amd) {
        // AMD
        define([], factory);
    } else {
        // Global (browser)
        win.Starmie = factory();
    }
}(this, function() {
    var starmie = {};
    
    starmie.install = function(Vue, options) {
        /**
         * 应用的根路径
         */
        Vue.prototype.$baseUrl = options.baseUrl;
        /**
         * 星期的表头
         */
        Vue.prototype.$weekHeaders = [
            { text: '日', value: 'sun', width: 34, sortable: false },
            { text: '一', value: 'mon', width: 34, sortable: false },
            { text: '二', value: 'tue', width: 34, sortable: false },
            { text: '三', value: 'wed', width: 34, sortable: false },
            { text: '四', value: 'thu', width: 34, sortable: false },
            { text: '五', value: 'fri', width: 34, sortable: false },
            { text: '六', value: 'sat', width: 34, sortable: false }
        ];
        /**
         * 月份列表
         */
        Vue.prototype.$months = [
            { value: 1, text: '01' }, { value: 2, text: '02' }, { value: 3, text: '03' },
            { value: 4, text: '04' }, { value: 5, text: '05' }, { value: 6, text: '06' },
            { value: 7, text: '07' }, { value: 8, text: '08' }, { value: 9, text: '09' },
            { value: 10, text: '10' }, { value: 11, text: '11' }, { value: 12, text: '12' }
        ];
        /**
         * 根据给定的year,返回年份列表,从2000年开始,到year+50
         */
        Vue.prototype.$getYears = function(year) {
            let maxY = (year < 2000) ? 2050 : year + 50;
            let minY = (year < 2000) ? year : 2000;
            let years = new Array();
            for (let i = minY; i <= maxY; i++) {
                years.push(i);
            }
            return years;
        };
        Vue.prototype.$getMaxDateOfMonth = function(year, month) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
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
         * 格式化日期
         */
        Vue.prototype.$formatDate= function(date) {
            return date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
        };
        /**
         * 计算给定年月的日期列表
         * dates - 需要刷新的日期数组
         * year - 给定年份
         * month - 给定月份 (1 - 12)
         * cDate - 当前日期 'yyyy-MM-dd'
         * sDates - 选中的日期 ['yyyy-MM-dd', 'yyyy-MM-dd']
         */
        Vue.prototype.$calcMonthDates = function(dates, year, month, cDate, sDates) {
            let dayFixed = (new Date(year, (month - 1), 1)).getDay() - 1;
            let islastSat = false;
            let day = {};
            let maxDate = this.$getMaxDateOfMonth(year, month);
            let st = (!sDates || sDates.length <= 0) ? 0 : sDates.length;
            dates.splice(0);
            let index = 0;
            let cd = this.$formatDate(cDate);
            for (let i = 1; i <= maxDate; i++) {
                islastSat = false;
                let d = year + '-' + (month < 10 ? '0' : '') + month + '-' + (i < 10 ? '0' : '' ) + i;
                let v = {value: i};
                if(d == cd) {
                    v.current = 1;
                }
                if (st > 0) {
                    v.selected = ((st == 1)?(d == sDates[0]) : (d >= sDates[0] && d <= sDates[1]));
                }
                let idx = (dayFixed + (i % 7) + 7) % 7;
                day[this.$weekHeaders[idx].value] = v;
                if (idx == 6) {
                    this.$set(dates, index, day);
                    index = index + 1;
                    day = {};
                    islastSat = true;
                }
            }
            if (!islastSat) {
                this.$set(dates, index, day);
                day = {};
            }
        };
    }
    
    return starmie;
}));