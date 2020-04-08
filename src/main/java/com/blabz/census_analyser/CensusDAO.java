package com.blabz.census_analyser;

public class CensusDAO {
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Integer population;
    public String stateCode;
    public String srNo;
    public String tin;

    public CensusDAO(CSVStateCensus stateCensusCSV) {
        state = stateCensusCSV.getState();
        areaInSqKm = stateCensusCSV.getAreaInSqKm();
        densityPerSqKm = stateCensusCSV.getDensityPerSqKm();
        population = stateCensusCSV.getPopulation();
    }

    public CensusDAO(CSVStates stateCodeCSV) {
        srNo = stateCodeCSV.srNo;
        state = stateCodeCSV.stateName;
        stateCode = stateCodeCSV.stateCode;
        tin = stateCodeCSV.tin;
    }

    public CensusDAO(CSVUSCensus usCSVData) {
        stateCode = usCSVData.getStateId();
        areaInSqKm = usCSVData.getArea();
        state = usCSVData.getState();
        densityPerSqKm = usCSVData.getPopulationDensity();

    }

    public static void getSortComparator(StateCensusAnalyser.SortingMode mode) {

    }
}