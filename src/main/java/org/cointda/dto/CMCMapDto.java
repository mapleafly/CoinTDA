package org.cointda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CMCMapDto {
    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    @JsonProperty(value = "is_active")
    private Integer isActive;
    private Integer rank;
    @JsonProperty(value = "first_historical_data")
    private String firstHistoricalData;
    @JsonProperty(value = "last_historical_data")
    private String lastHistoricalData;
    private Platform platform;
}
