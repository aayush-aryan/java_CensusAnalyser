package com.blabz.census_analyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
        private List stateCSVList = null;
        public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                List censusCSVIList = csvBuilder.getCSVFileList(reader,CSVStateCensus.class);
                return censusCSVIList.size();
            }catch (IOException e){
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                stateCSVList = csvBuilder.getCSVFileList(reader,CSVStates.class);
                return stateCSVList.size();
            }catch (IOException e) {
                throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public String getSortedStateCodeData() throws StateCensusAnalyserException{
            if( stateCSVList == null ||  stateCSVList.size() == 0)
                throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            Comparator<CSVStates> censusComparator = Comparator.comparing(census -> census.stateCode);
            this.sort(stateCSVList,censusComparator);
            String json = new Gson().toJson(stateCSVList);
            return json;
        }
        private void sort(List<CSVStates> censusCSVIList, Comparator<CSVStates> censusComparator){
            for(int i=0; i<censusCSVIList.size()-1; i++){
                for(int j=0; j<censusCSVIList.size()-i-1; j++) {
                    CSVStates census1 = censusCSVIList.get(j);
                    CSVStates census2 = censusCSVIList.get(j+1);
                    if(censusComparator.compare(census1,census2) > 0){
                        censusCSVIList.set(j,census2);
                        censusCSVIList.set(j+1,census1);
                    }
                }
            }
        }
        private <E> int getCount(Iterator<E> iterator){
            Iterable<E> csvIterable = () -> iterator;
            int numOfEntries = (int)StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEntries;
        }

}


