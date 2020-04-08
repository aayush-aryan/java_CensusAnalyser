package censusadapter;
import com.opencsv.bean.CsvBindByName;
public class IndiaCSVDTO {
    public IndiaCSVDTO(){ }
    @CsvBindByName(column = "State", required = true)
    public String state;
    @CsvBindByName(column = "Population", required = true)
    public Integer population;
    @CsvBindByName(column = "AreaInSqKm", required = true)
    public Double areaInSqKm;
    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Double densityPerSqKm;

    public IndiaCSVDTO(String state, Integer population, Double areaInSqKm, Double densityPerSqKm) {
        this.state = state;
        this.areaInSqKm = areaInSqKm;
        this.population = population;
        this.densityPerSqKm = densityPerSqKm;
    }
}
