package org.cointda.service.feignc;

import feign.Headers;
import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用FeignClient，远程获取json数据
 */
@FeignClient(name = "ICoinMarketCapIdMapFeignClient", url = "${coin-market-cap.coinMarketCapIDMap}", configuration = FeignClientConfig.class)
public interface ICoinMarketCapIdMapFeignClient {
    //@GetMapping(value = "/v1/cryptocurrency/map", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@Headers({"X-CMC_PRO_API_KEY:8f94c3a3-0a31-43e4-818e-a7e83791b1b3"})
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    @Headers(value = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=${coin-market-cap.apiKey}"})
    public String getHttpJson(
        @RequestParam("listing_status") String listing_status,
        @RequestParam("limit") String limit,
        @RequestParam("sort") String sort
    );
}
