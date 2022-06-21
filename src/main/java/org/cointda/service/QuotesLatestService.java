package org.cointda.service;

import feign.Headers;
import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "QuotesLatestService", url = "${coin-market-cap.quotesLatest}", configuration = FeignClientConfig.class)
public interface QuotesLatestService {

    /**
     *
     * @param id 指定coin id， 多个id用逗号分割， 如："1982,9444"
     * @param convert
     * @return
     */
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    @Headers(value = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    public String getResult(
        @RequestParam("id") String id,
        @RequestParam("convert") String convert,
        @RequestParam("aux") String aux
    );

    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    @Headers(value = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    public String getResult(
        @RequestParam("id") String id,
        @RequestParam("convert") String convert
    );
}
