package censusadapter;

import com.blabz.census_analyser.CSVBuilderException;
import com.blabz.census_analyser.CSVUSCensus;

public class USCensusAdapter extends CensusAdapter {
        @Override
        public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
            return super.loadCensusData(CSVUSCensus.class, csvFilePath[0]);
        }

}
