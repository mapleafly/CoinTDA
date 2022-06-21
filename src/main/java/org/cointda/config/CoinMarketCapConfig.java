package org.cointda.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "coin-market-cap")
public class CoinMarketCapConfig {
    private String apiKey;
    private String customHeader;
    private String httpHeader;
    private String coinMarketCapIDMap;
    private String latestListings;
    private String latestQuotes;
    //private String metadata;
    //private String historicalListings;
    //private String historicalQuotes;
    //private String latestMarketPairs;
    //private String latestOHLCV;
    //private String historicalOHLCV;
    //private String pricePerformanceStats;
}
