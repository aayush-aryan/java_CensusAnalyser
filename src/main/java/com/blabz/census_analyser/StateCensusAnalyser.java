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
                Iterator<CSVStateCensus> censusCSVIterator = this.getCSVFileIterator(reader,CSVStateCensus.class);
                return getCount(censusCSVIterator);
            }catch (IOException e){
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                Iterator<CSVStates> stateCSVIterator = this.getCSVFileIterator(reader,CSVStates.class);
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
        private <E> Iterator<E> getCSVFileIterator(Reader reader,Class csvClass) throws StateCensusAnalyserException {
            try{CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<E> csvToBean = csvToBeanBuilder.build();
                return csvToBean.iterator();
            } catch (RuntimeException e) {
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
            }
        }
    }



