package com.blabz.census_analyser;

public class CensusDAO {
    public String state;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String population;
    public String stateCode;
    public String srNo;
    public String tin;

    public CensusDAO(CSVStateCensus stateCensusCSV) {
        state = stateCensusCSV.getState();
        areaInSqKm = stateCensusCSV.getAreaInSqKm();
        densityPerSqKm = stateCensusCSV.getDensityPerSqKm();
        population = stateCensusCSV.getPopulation();
    }
    public CensusDAO(CSVStates statesCodeCSV) {
        srNo = statesCodeCSV.srNo;
        state = statesCodeCSV.stateName;
        stateCode = statesCodeCSV.stateCode;
        tin = statesCodeCSV.tin;
    }
}
