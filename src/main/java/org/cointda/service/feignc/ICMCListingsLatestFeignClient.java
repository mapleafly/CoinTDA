package org.cointda.service.feignc;

import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 使用FeignClient，远程获取json数据
 */
@FeignClient(name = "ICMCListingsLatestFeignClient", url = "${coin-market-cap.latestListings}", configuration = FeignClientConfig.class)
public interface ICMCListingsLatestFeignClient {
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
        @RequestParam("start") String start,
        @RequestParam("limit") String limit,
        @RequestParam("convert") String convert
    );
}
