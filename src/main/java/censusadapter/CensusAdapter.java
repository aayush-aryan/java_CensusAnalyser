package censusadapter;

import com.blabz.census_analyser.*;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException;

    <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... getPath) throws CSVBuilderException {
        Map<String, CensusDAO> censusDAOMap = new HashedMap();
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvStateCensusIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("censusanalyser.CSVStateCensus"))
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(CSVStateCensus.class::cast)
                        .forEach(CSVStateCensus -> censusDAOMap.put(CSVStateCensus.getState(), new CensusDAO(CSVStateCensus)));
            if (censusCSVClass.getName().equals("censusanalyser.CSVUSCensus"))
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(CSVUSCensus.class::cast)
                        .forEach(UsCSVData -> censusDAOMap.put(UsCSVData.getState(), new CensusDAO((CSVStateCensus) UsCSVData)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);

        }
    }
}