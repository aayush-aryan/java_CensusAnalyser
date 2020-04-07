package com.blabz.census_analyser;

public class StateCensusAnalyserException extends Exception{
    public enum ExceptionType{
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE;
    }
    public ExceptionType type;
    public StateCensusAnalyserException(String message,ExceptionType type){
        super(message);
        this.type = type;
    }
    public StateCensusAnalyserException(String message,ExceptionType type,Throwable cause){
        super(message,cause);
        this.type = type;
    }
    public StateCensusAnalyserException(String message,String name){
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}
