package org.cointda.service.feignc;

import org.cointda.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用FeignClient，远程获取json数据
 */
@FeignClient(name = "ICMCMapFeignClient", url = "${coin-market-cap.coinMarketCapIDMap}", configuration = FeignClientConfig.class)
public interface ICMCMapFeignClient {
    /**
     *
     * @param listing_status 状态； "active" ， "inactive" , "untracked"
     * @param limit  1...5000
     * @param sort  排序："id" , "cmc_rank"
     * @return
     */
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
        @RequestParam("listing_status") String listing_status,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit,
        @RequestParam("sort") String sort,
        @RequestParam("aux") String aux
    );

    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit,
        @RequestParam("sort") String sort,
        @RequestParam("aux") String aux
    );

    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit,
        @RequestParam("sort") String sort
    );

    //@GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=#{T(org.cointda.util.PrefsHelper).getCmcApiKey()}"})
    //@Headers(value = {"Accept=${coin-market-cap.httpHeader}", "${coin-market-cap.customHeader}=#{T(org.cointda.util.PrefsHelper).getCmcApiKey()}"})
    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
        @RequestParam("limit") Integer limit,
        @RequestParam("sort") String sort
    );

    @GetMapping(headers = {"Accept=${coin-market-cap.httpHeader}"})
    public String getHttpJson(
         @RequestParam("limit") Integer limit
    );
}
