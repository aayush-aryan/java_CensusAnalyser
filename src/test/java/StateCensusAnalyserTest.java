import com.blabz.census_analyser.CensusDAO;
import com.blabz.census_analyser.StateCensusAnalyser;
import com.blabz.census_analyser.StateCensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest{
    final String INDIA_STATE_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/StateCodeData.csv";
    final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    final String WRONG_CSV_FILE_TYPE = "./src/test/resources/StateCodeData.pdf";
    final String CSV_FILE_WITH_WRONG_DELIMITER = "./src/main/resources/StateCodeData.csv";
    final String CSV_FILE_WITH_WRONG_HEADER = "./src/main/resources/StateCodeData.csv";

    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    @Test
    public void givenIndianStateCensusDataWhenSortedOnPopulationShouldReturnMostPopulatedAtFirst() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(INDIA_STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusDataWhenSortedOnPopulationShouldReturnLessPopulatedAtLast() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(INDIA_STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusDataWhenNullShouldThrowException() {
        try {
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedSPopulation();
            CensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("No Population State Data", e.getMessage());
        }
    }
    @Test
    public void givenIndiaStateCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = stateCensusAnalyser.loadIndiaCensusData(INDIA_STATE_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        }catch (StateCensusAnalyserException e){
            e.getStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = stateCensusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        }catch (StateCensusAnalyserException e){
            e.getStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusCSVWithWrongFilePathShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVWithWrongFileTypeShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_TYPE);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVWithCorrectFileButWrongDelimiterShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaStateCodeData(CSV_FILE_WITH_WRONG_DELIMITER);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVWithCorrectFileButWrongHeaderShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaStateCodeData(CSV_FILE_WITH_WRONG_HEADER);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }
}