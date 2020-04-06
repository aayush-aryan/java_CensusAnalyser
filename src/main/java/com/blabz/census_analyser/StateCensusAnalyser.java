package com.blabz.census_analyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser{
    public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<CSVStateCensus> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,CSVStateCensus.class);
            return getCount(censusCSVIterator);
        }catch (IOException e){
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<CSVStates> stateCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,CSVStates.class);
            return getCount(stateCSVIterator);
        }catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
}



