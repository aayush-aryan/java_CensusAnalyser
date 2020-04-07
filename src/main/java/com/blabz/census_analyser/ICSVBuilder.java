package com.blabz.census_analyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
}
