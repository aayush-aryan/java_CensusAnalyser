package com.blabz.census_analyser;

public class CSVBuilderException extends Throwable {
    public enum ExceptionType{
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE;
        public static final ExceptionType NO_CENSUS_DATA = ;
    }
    public ExceptionType type;
    public CSVBuilderException(String message, ExceptionType type){
        super(message);
        this.type = type;
    }
}
