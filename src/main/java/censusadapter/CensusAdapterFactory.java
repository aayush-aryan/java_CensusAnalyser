package censusadapter;

import com.blabz.census_analyser.CSVBuilderException;

import com.blabz.census_analyser.StateCensusAnalyser;
import com.blabz.census_analyser.StateCensusAnalyserException;

public class CensusAdapterFactory {
    public static CensusAdapter getCensusData(StateCensusAnalyser.COUNTRY country) throws CSVBuilderException {
        switch (country) {
            case INDIA:
                return new IndiaCensusAdapter();
            case US:
                return new USCensusAdapter();
            default:
                throw new CSVBuilderException("incorrect Country", CSVBuilderException.ExceptionType.INCORRECT_COUNTRY_ENTERED);
        }
    }
}
