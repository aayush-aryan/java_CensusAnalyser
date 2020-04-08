package censusadapter;

import censusanalyser.*;
import censusdto.IndiaCSVDTO;
import censusdto.UsCSVDTO;
import com.blabz.census_analyser.CSVBuilderException;
import com.blabz.census_analyser.CSVBuilderFactory;
import com.blabz.census_analyser.CensusDAO;
import com.blabz.census_analyser.ICSVBuilder;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAdapter{
        public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException ;

        <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... getPath) throws CSVBuilderException {
            Map<String, CensusDAO> censusDAOMap = new HashedMap();
            try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))
            ) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvFileIterator = ((ICSVBuilder) csvBuilder).getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> csvStateCensusIterable = () -> csvFileIterator;
                if (censusCSVClass.getName().equals("censusdto.IndiaCSVDTO"))
                    StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                            .map(IndiaCSVDTO.class::cast)
                            .forEach(IndiaCSVDTO -> censusDAOMap.put(IndiaCSVDTO.state, new CensusDAO(IndiaCSVDTO)));
                if (censusCSVClass.getName().equals("censusdto.UsCSVDTO"))
                    StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                            .map(UsCSVDTO.class::cast)
                            .forEach(UsCSVDTO -> censusDAOMap.put(UsCSVDTO.state, new CensusDAO(UsCSVDTO)));
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