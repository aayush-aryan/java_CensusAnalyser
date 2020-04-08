import com.blabz.census_analyser.CSVBuilderException;
import com.blabz.census_analyser.StateCensusAnalyser;
import com.blabz.census_analyser.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import static censusanalyser.StateCensusAnalyser.COUNTRY.INDIA;
import static censusanalyser.StateCensusAnalyser.COUNTRY.US;

        public class StateCensusAnalyserTest {
            final String INDIA_STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
            final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/StateCodeData.csv";
            final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
            final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
            final String WRONG_CSV_FILE_TYPE = "./src/test/resources/StateCodeData.pdf";
            final String CSV_FILE_WITH_WRONG_DELIMITER = "./src/main/resources/StateCodeData.csv";
            final String CSV_FILE_WITH_WRONG_HEADER = "./src/main/resources/StateCodeData.csv";

            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

            @Test
            public void givenUSCensusCSVFileReturnsCorrectRecords() {
                try {
                    int numOfRecords = stateCensusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE_PATH);
                    Assert.assertEquals(51, numOfRecords);
                } catch (CSVBuilderException e) {
                    e.getStackTrace();
                }
            }

            @Test
            public void givenIndiaStateCensusCSVFileReturnsCorrectRecords() {
                try {
                    int numOfRecords = stateCensusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE_PATH);
                    Assert.assertEquals(29, numOfRecords);
                } catch (CSVBuilderException e) {
                    e.getStackTrace();
                }
            }

            @Test
            public void givenUSCensusDataWhenSortedOnStatePopulationShouldReturnCaliforniaAtFirst() {
                try {
                    stateCensusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE_PATH);
                    String sortedCensusData = stateCensusAnalyser.getStatePopulationWiseSortedUSCensusData();
                    CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
                    Assert.assertEquals("California", stateCensuses[0].state);
                } catch (CSVBuilderException e) {
                    e.printStackTrace();
                }
            }

            @Test
            public void givenUSCensusDataWhenSortedOnStatePopulationShouldReturnWyomingAtLast() {
                try {
                    stateCensusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE_PATH);
                    String sortedCensusData = stateCensusAnalyser.getStatePopulationWiseSortedUSCensusData();
                    CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
                    Assert.assertEquals("Wyoming", stateCensuses[50].state);
                } catch (CSVBuilderException e) {
                    e.printStackTrace();
                }
            }

            @Test
            public void givenUSCensusDataWhenNullShouldThrowException() {
                try {
                    String sortedCensusData = stateCensusAnalyser.getStatePopulationWiseSortedUSCensusData();
                    CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals("No Population State Data", e.getMessage());
                }
            }

            @Test
            public void givenUSCensusCSVWithWrongFilePathShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(US, WRONG_CSV_FILE_PATH);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
                }
            }

            @Test
            public void givenUSCensusCSVWithWrongFileTypeShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(US, WRONG_CSV_FILE_TYPE);
                    ;
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
                }
            }

            @Test
            public void givenUSCensusCSVWithCorrectFileButWrongDelimiterShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(US, CSV_FILE_WITH_WRONG_DELIMITER);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
                }
            }

            @Test
            public void givenUSCensusCSVWithCorrectFileButWrongHeaderShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(US, CSV_FILE_WITH_WRONG_HEADER);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
                }
            }

            @Test
            public void givenIndiaCensusCSVWithWrongFilePathShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_PATH);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
                }
            }

            @Test
            public void givenIndiaCensusCSVWithWrongFileTypeShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(INDIA, WRONG_CSV_FILE_TYPE);
                    ;
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
                }
            }

            @Test
            public void givenIndiaCensusCSVWithCorrectFileButWrongDelimiterShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(INDIA, CSV_FILE_WITH_WRONG_DELIMITER);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
                }
            }

            @Test
            public void givenIndiaCensusCSVWithCorrectFileButWrongHeaderShouldThrowException() {
                try {
                    stateCensusAnalyser.loadCensusData(INDIA, CSV_FILE_WITH_WRONG_HEADER);
                } catch (CSVBuilderException e) {
                    Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
                }
            }
        }