package com.blabz.census_analyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
        public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStateCensus> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,CSVStateCensus.class);
                return getCount(censusCSVIterator);
            }catch (IOException e){
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStates> stateCSVIterator = (Iterator<CSVStates>) csvBuilder.getCSVFileIterator(reader,CSVStates.class);
                return getCount(stateCSVIterator);
            }catch (IOException e) {
                throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        private <E> int getCount(Iterator<E> iterator){
            Iterable<E> csvIterable = () -> iterator;
            int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEntries;
        }
}


