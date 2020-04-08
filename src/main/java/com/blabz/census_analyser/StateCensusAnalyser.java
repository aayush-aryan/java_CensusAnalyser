package com.blabz.census_analyser;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

public class StateCensusAnalyser{
        HashMap<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();
        public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
                while (csvFileIterator.hasNext()) {
                    CensusDAO indianCensusDAO = new CensusDAO(csvFileIterator.next());
                    this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
                }
                return this.censusDAOMap.size();
            }catch (IOException e){
                throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws StateCensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<CSVStates> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
                while (csvFileIterator.hasNext()) {
                    CensusDAO censusDAO = new CensusDAO(csvFileIterator.next());
                    this.censusDAOMap.put(censusDAO.state, censusDAO);
                }
                return this.censusDAOMap.size();
            }catch (IOException e) {
                throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new StateCensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
        public String getStateWiseSortedSDensity() throws StateCensusAnalyserException {
            if (censusDAOMap == null || censusDAOMap.size() == 0)
                throw new StateCensusAnalyserException("No Population State Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().densityPerSqKm);
            LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
            ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
            Collections.reverse(list);
            String sortedStateCensusJson = new Gson().toJson(list);
            return sortedStateCensusJson;
        }
        private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
            Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
            List<Map.Entry<String, CensusDAO>> listOfEntries = new ArrayList<Map.Entry<String, CensusDAO>>(entries);
            Collections.sort(listOfEntries, censusComparator);
            LinkedHashMap<String, CensusDAO> sortedByValue = new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
            for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
                sortedByValue.put(entry.getKey(), entry.getValue());
            }
            return sortedByValue;
        }

}


