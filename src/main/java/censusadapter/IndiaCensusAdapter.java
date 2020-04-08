package censusadapter;
import com.blabz.census_analyser.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
       @Override
        public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
            Map<String, CensusDAO> censusStateMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
            if (csvFilePath.length == 1)
                return  censusStateMap;
            return this.loadStateCodeData(censusStateMap, csvFilePath[1]);
        }

        //READING AND PRINTING DATA FROM STATE CODE CSV FILE
        public static Map<String, CensusDAO> loadStateCodeData(Map<String, CensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
            try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
            ) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStates> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
                Iterable<CSVStates> csvStateCodeIterable = () -> csvFileIterator;
                StreamSupport.stream(csvStateCodeIterable.spliterator(), false)
                        .map(CSVStates.class::cast)
                        .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(), new CensusDAO(csvStateCode)));
                return censusDAOMap;
            } catch (IOException e) {
                throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
            } catch (RuntimeException e) {
                throw new CSVBuilderException("Number of data fields does not match number of headers.",
                        CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
            }
        }
}
