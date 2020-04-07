package com.blabz.census_analyser;

import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws StateCensusAnalyserException;
}
