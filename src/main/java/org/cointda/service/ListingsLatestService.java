package org.cointda.service;

import feign.Headers;
import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ListingsLatestService", url = "${coin-market-cap.latestListings}", configuration = FeignClientConfig.class)
public interface ListingsLatestService {
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    @Headers(value = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    public String getResult(
        @RequestParam("start") String start,
        @RequestParam("limit") String limit,
        @RequestParam("convert") String convert
    );
}
