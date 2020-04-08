package com.blabz.census_analyser;

import censusadapter.CensusAdapter;
import censusadapter.CensusAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StateCensusAnalyser {
        Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();
        public enum COUNTRY {INDIA,US};
        public enum SortingMode {AREA,STATE,STATECODE,DENSITY,POPULATION};
        private COUNTRY country;
        public StateCensusAnalyser() {    }
        public StateCensusAnalyser(COUNTRY country) {
            this.country = country;
        }
        public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException {
            CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
            censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
            return censusDAOMap.size();
        }
        public String getSortedCensusData(SortingMode mode,COUNTRY countryName) throws CSVBuilderException {
            if (censusDAOMap == null || censusDAOMap.size() == 0)
                throw new CSVBuilderException("No Census Data",CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
            ArrayList censusDTO = censusDAOMap.values().stream()
                    .sorted(CensusDAO.getSortComparator(mode))
                    .map(CensusDAO  -> CensusDAO.getCensusDTO(countryName))
                    .collect(Collectors.toCollection(ArrayList::new));
            return new Gson().toJson(censusDTO);
        }
        public String getMostPopulousStateInUsAndIndia(StateCensusAnalyser ...object) throws CSVBuilderException {
            CensusDAO[] stateCensuses1 = new Gson().fromJson(object[0].getSortedCensusData(SortingMode.POPULATION,object[0].country), CensusDAO[].class);
            CensusDAO[] stateCensuses2 = new Gson().fromJson(object[1].getSortedCensusData(SortingMode.POPULATION,object[1].country), CensusDAO[].class);
            if(stateCensuses1[0].population>stateCensuses2[0].population)
                return stateCensuses1[0].state;
            else
                return stateCensuses2[0].state;
        }
}

