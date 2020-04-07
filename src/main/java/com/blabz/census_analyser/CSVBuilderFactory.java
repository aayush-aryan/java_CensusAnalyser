package com.blabz.census_analyser;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return (ICSVBuilder) new OpenCSVBuilder();
    }
}
