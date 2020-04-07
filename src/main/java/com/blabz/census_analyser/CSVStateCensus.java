package com.blabz.census_analyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
        @CsvBindByName(column = "State", required = true)
        public String state;
        @CsvBindByName(column = "Population", required = true)
        public String population;
        @CsvBindByName(column = "AreaInSqKm", required = true)
        public String areaInSqKm;
        @CsvBindByName(column = "DensityPerSqKm", required = true)
        public String densityPerSqKm;
        @Override
        public String toString(){
            return "CSVStateCensus(" +
                    "State'" + state + '\'' +
                    ", population'" + population + '\'' +
                    ", AreaInSqKm'" + areaInSqKm + '\'' +
                    ", DensityInSqKm'" + densityPerSqKm + '\'' +
                    ")";
        }
        //GETTER AND SETTER METHOD TO ENCAPSULATE AND PROVIDE WAY TO USER TO USE PRIVATE DATA
        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        public String getPopulation() {
            return population;
        }

        public void setAreaInSqKm(String areaInSqKm) {
            this.areaInSqKm = areaInSqKm;
        }

        public String getAreaInSqKm() {
            return areaInSqKm;
        }

        public void setDensityPerSqKm(String densityPerSqKm) {
            this.densityPerSqKm = densityPerSqKm;
        }

        public String getDensityPerSqKm() {
            return densityPerSqKm;
        }
}


