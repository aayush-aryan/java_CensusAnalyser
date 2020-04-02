import com.blabz.census_analyser.StateCensusAnalyser;
import com.blabz.census_analyser.StateCensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    final String WRONG_CSV_FILE_PATH ="./src/main/resources/IndiaStateCensusData.csv";
    final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
    final String CSV_FILE_WITH_WRONG_DELIMITER = "./src/test/resources/XIndiaStateCensusData.csv";
    final String CSV_FILE_WITH_WRONG_HEADER = "./src/test/resources/XIndiaStateCensusData.csv";
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    @Test
    public void givenIndiaCensusCSVFileReturnsCorrectRecords() throws StateCensusAnalyserException {
        try {
            int numOfRecords = stateCensusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        }catch (StateCensusAnalyserException e){
            e.getStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusCSVWithWrongFilePathShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVWithWrongFileTypeShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
     }
    @Test
    public void givenIndiaCensusCSVWithCorrectFileButWrongDelimiterShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(CSV_FILE_WITH_WRONG_DELIMITER);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVWithCorrectFileButWrongHeaderShouldThrowException() {
        try {
            stateCensusAnalyser.loadIndiaCensusData(CSV_FILE_WITH_WRONG_HEADER);
        }catch (StateCensusAnalyserException e){
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }
}

