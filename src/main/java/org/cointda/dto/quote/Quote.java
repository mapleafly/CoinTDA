package org.cointda.dto.quote;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Quote {
    private String quote;
    private double price;
    private double volume_24h;
    private double volume_change_24h;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private double percent_change_30d;
    private double percent_change_60d;
    private double percent_change_90d;
    private double market_cap;
    private double market_cap_dominance;
    private long fully_diluted_market_cap;
    private double tvl;
    private Date last_updated;
}
