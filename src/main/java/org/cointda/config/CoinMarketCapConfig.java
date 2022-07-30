package org.cointda.config;

import org.cointda.util.PrefsHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Data
@Component
//@ConfigurationProperties(prefix = "coin-market-cap")
public class CoinMarketCapConfig {
    @Bean("CMCApiKey")
    String getApiKey(){
        return PrefsHelper.getPreferencesValue(PrefsHelper.CMC_API_KEY, "");
    }
    //private String apiKey;
    //private String customHeader;
    //private String httpHeader;
    //private String coinMarketCapIDMap;
    //private String latestListings;
    //private String latestQuotes;
    //private String metadata;
    //private String historicalListings;
    //private String historicalQuotes;
    //private String latestMarketPairs;
    //private String latestOHLCV;
    //private String historicalOHLCV;
    //private String pricePerformanceStats;
}
