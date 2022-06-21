package org.cointda.service;

import feign.Headers;
import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CoinMarketCapIdMapService", url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/map", configuration = FeignClientConfig.class)
public interface CoinMarketCapIdMapService {
    //@GetMapping(value = "/v1/cryptocurrency/map", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@Headers({"X-CMC_PRO_API_KEY:8f94c3a3-0a31-43e4-818e-a7e83791b1b3"})
    @GetMapping(headers = {"Accept=application/json", "X-CMC_PRO_API_KEY=8f94c3a3-0a31-43e4-818e-a7e83791b1b3"})
    @Headers(value = {"Accept=application/json", "X-CMC_PRO_API_KEY=8f94c3a3-0a31-43e4-818e-a7e83791b1b3"})
    public String getResult(
        @RequestParam("listing_status") String listing_status,
        @RequestParam("limit") String limit,
        @RequestParam("sort") String sort
    );
}
