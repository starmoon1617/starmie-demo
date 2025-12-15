/*
 * Copyright (c) 2025, Starmoon1617 and/or Nathan Liao. All rights reserved.
 * 
 */
package io.github.starmoon1617.starmie.demo.test.nothing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @date 2025-02-09
 * @author Nathan Liao
 */
public class DltTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DltTest.class);
    
    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper();
        
        String apiUrl = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=100&isVerify=1&pageNo=%s";
        
        
        
        
    }

    public static class Result {
        private ResVal value;

        /**
         * @return the value
         */
        public ResVal getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(ResVal value) {
            this.value = value;
        }
    }

    public static class ResVal {
        private List<Dlt> list;
        private int pageNo;
        private int pageSize;
        private int pages;
        private int total;

        /**
         * @return the list
         */
        public List<Dlt> getList() {
            return list;
        }

        /**
         * @param list
         *            the list to set
         */
        public void setList(List<Dlt> list) {
            this.list = list;
        }

        /**
         * @return the pageNo
         */
        public int getPageNo() {
            return pageNo;
        }

        /**
         * @param pageNo
         *            the pageNo to set
         */
        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        /**
         * @return the pageSize
         */
        public int getPageSize() {
            return pageSize;
        }

        /**
         * @param pageSize
         *            the pageSize to set
         */
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * @return the pages
         */
        public int getPages() {
            return pages;
        }

        /**
         * @param pages
         *            the pages to set
         */
        public void setPages(int pages) {
            this.pages = pages;
        }

        /**
         * @return the total
         */
        public int getTotal() {
            return total;
        }

        /**
         * @param total
         *            the total to set
         */
        public void setTotal(int total) {
            this.total = total;
        }

    }

    public static class Dlt {

        private String lotteryDrawTime;
        private String lotteryDrawNum;
        private String lotteryDrawResult;

        /**
         * @return the lotteryDrawTime
         */
        public String getLotteryDrawTime() {
            return lotteryDrawTime;
        }

        /**
         * @param lotteryDrawTime
         *            the lotteryDrawTime to set
         */
        public void setLotteryDrawTime(String lotteryDrawTime) {
            this.lotteryDrawTime = lotteryDrawTime;
        }

        /**
         * @return the lotteryDrawNum
         */
        public String getLotteryDrawNum() {
            return lotteryDrawNum;
        }

        /**
         * @param lotteryDrawNum
         *            the lotteryDrawNum to set
         */
        public void setLotteryDrawNum(String lotteryDrawNum) {
            this.lotteryDrawNum = lotteryDrawNum;
        }

        /**
         * @return the lotteryDrawResult
         */
        public String getLotteryDrawResult() {
            return lotteryDrawResult;
        }

        /**
         * @param lotteryDrawResult
         *            the lotteryDrawResult to set
         */
        public void setLotteryDrawResult(String lotteryDrawResult) {
            this.lotteryDrawResult = lotteryDrawResult;
        }

    }

}
