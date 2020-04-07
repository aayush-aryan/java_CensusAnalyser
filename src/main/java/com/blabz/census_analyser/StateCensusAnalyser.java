package com.blabz.census_analyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
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
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List stateCSVList = csvBuilder.getCSVFileList(reader,CSVStates.class);
            return stateCSVList.size();
        }catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }

}


