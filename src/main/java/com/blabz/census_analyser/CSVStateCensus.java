package com.blabz.census_analyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
    @CsvBindByName(column = "State", required = true)
    public String state;
    @CsvBindByName(column = "Population", required = true)
    public String population;
    @CsvBindByName(column = "AreaInSqKm", required = true)
    public String areaInSqKm;
    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public String densityPerSqKm;

    @Override
    public String toString() {
        return "CSVStateCensus(" +
                "State'" + state + '\'' +
                ", population'" + population + '\'' +
                ", AreaInSqKm'" + areaInSqKm + '\'' +
                ", DensityInSqKm'" + densityPerSqKm + '\'' +
                ")";
    }
}
