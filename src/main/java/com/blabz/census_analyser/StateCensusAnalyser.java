package com.blabz.census_analyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser {
        HashMap<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();
        public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
                while (csvFileIterator.hasNext()) {
                    CensusDAO indianCensusDAO = new CensusDAO(csvFileIterator.next());
                    this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
                }
                return this.censusDAOMap.size();
            }catch (IOException e){
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStates> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
                while (csvFileIterator.hasNext()) {
                    CensusDAO censusDAO = new CensusDAO(csvFileIterator.next());
                    this.censusDAOMap.put(censusDAO.state, censusDAO);
                }
                return this.censusDAOMap.size();
            }catch (IOException e) {
                throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        //METHOD TO SORT STATE NAME
        public String getStateWiseSortedCensusData() throws StateCensusAnalyserException {
            if (censusDAOMap == null || censusDAOMap.size() == 0)
                throw new StateCensusAnalyserException("No Census Data", StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().state);
            LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
            Collection<CensusDAO> sortedCensusData = sortedByValue.values();
            String sortedStateCensusJson = new Gson().toJson(sortedCensusData);
            return sortedStateCensusJson;
        }

        //METHOD TO SORT STATE CODE
        public String getStateWiseSortedStateCode() throws StateCensusAnalyserException {
            if (censusDAOMap == null || censusDAOMap.size() == 0)
                throw new StateCensusAnalyserException("No State Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().stateCode);
            LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
            Collection<CensusDAO> sortedCensusData = sortedByValue.values();
            return new Gson().toJson(sortedCensusData);
        }

        //METHOD TO SORT THE LIST IN ALPHABETICAL ORDER
        private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
            Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
            List<Map.Entry<String, CensusDAO>> listOfEntries = new ArrayList<Map.Entry<String, CensusDAO>>(entries);
            Collections.sort(listOfEntries, censusComparator);
            LinkedHashMap<String, CensusDAO> sortedByValue = new LinkedHashMap<String, CensusDAO>(listOfEntries.size());

            // copying entries from List to Map
            for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
                sortedByValue.put(entry.getKey(), entry.getValue());
            }
            return sortedByValue;
        }

}


