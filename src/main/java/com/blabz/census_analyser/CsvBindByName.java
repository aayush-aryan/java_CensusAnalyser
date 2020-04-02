package com.blabz.census_analyser;

public @interface CsvBindByName {
    String column();

    boolean required();
}
