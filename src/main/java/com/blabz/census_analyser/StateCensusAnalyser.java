package com.blabz.census_analyser;

import censusadapter.CensusAdapter;
import censusadapter.CensusAdapterFactory;

import java.util.HashMap;
import java.util.Map;

public class StateCensusAnalyser {
    Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    public enum COUNTRY {INDIA,US};
    public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOMap.size();
    }
        }

