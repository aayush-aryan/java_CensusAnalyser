package com.blabz.census_analyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
        @CsvBindByName(column = "SrNo", required = true)
        public String srNo;
        @CsvBindByName(column = "StateName", required = true)
        public String stateName;
        @CsvBindByName(column = "TIN", required = true)
        public String tin;
        @CsvBindByName(column = "StateCode", required = true)
        public String stateCode;
        @Override
        public String toString(){
            return "CSVStateCensus(" +
                    "SrNo'" + srNo + '\'' +
                    ", StateName'" + stateName + '\'' +
                    ", TIN'" + tin + '\'' +
                    ", StateCode'" + stateCode + '\'' +
                    ")";
        }
        public String getSrNo() {
            return srNo;
        }

        public void setSrNo(String srNo) {
            this.srNo = srNo;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getTIN() {
            return tin;
        }

        public void setTIN(String TIN) {
            this.tin = TIN;
        }

        public String getStateCode() {
            return stateCode;
        }
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
        }