package org.cointda;

import lombok.extern.slf4j.Slf4j;
import org.cointda.config.CoinMarketCapConfig;
import org.cointda.dto.quote.QuotesLatestDto;
import org.cointda.entity.TradeInfo;
import org.cointda.mapper.TradeInfoMapper;
import org.cointda.service.IQuotesLatestService;
import org.cointda.service.feignc.ICoinMarketCapIdMapFeignClient;
import org.cointda.service.feignc.IListingsLatestFeignClient;
import org.cointda.service.feignc.IQuotesLatestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
@Order(value = 1)
public class InitDBCommanLineRunner implements CommandLineRunner {

    @Resource
    private TradeInfoMapper tradeInfoMapper;

    @Autowired
    private CoinMarketCapConfig coinMarketCapConfig;

    @Autowired
    ICoinMarketCapIdMapFeignClient iCoinMarketCapIdMapFeignClient;
    @Autowired
    IListingsLatestFeignClient iListingsLatestFeignClient;
    @Autowired
    IQuotesLatestFeignClient iQuotesLatestFeignClient;

    @Autowired
    IQuotesLatestService iQuotesLatestService;

    @Override
    public void run(String... args) throws Exception {
        log.info("加载顺序1");
        //log.info("CoinMarketCapConfig.apiKey = "+ coinMarketCapConfig.getApiKey());

        //String str = coinMarketCapIdMapService.getResult("active", "5000", "cmc_rank");
        //log.info("json.str2 ==" + str);

        //log.info("listingLatest = "+ listingsLatestService.getResult("1", "5000", "USD"));

        //num_market_pairs,cmc_rank,date_added,tags,platform,max_supply,circulating_supply,total_supply,market_cap_by_total_supply,volume_24h_reported,volume_7d,volume_7d_reported,volume_30d,volume_30d_reported,is_active,is_fiat
        //log.info("quotesLatestService = "+ iQuotesLatestFeignClient.getHttpJson("1982", "USD", "num_market_pairs,cmc_rank,date_added,platform,max_supply,circulating_supply,total_supply,is_active"));
        String aux = "num_market_pairs,cmc_rank,date_added,platform,max_supply,circulating_supply,total_supply,is_active";
        List<QuotesLatestDto> listQuotesLatestDto = iQuotesLatestService.getJson("id","1,1982", "USD", aux);
        if(listQuotesLatestDto != null){
            for(QuotesLatestDto dto : listQuotesLatestDto){
                log.info("quotesLatestService = "+dto.toString());
            }
        }
        //JSONObject jsonObject = coinMarketCapIdMapService.getResult("active", "5000", "cmc_rank");
        //log.info("jsonObject.toString() ==" + jsonObject.toString());
        //JSONObject jsonData = jsonObject.getJSONObject("data");
        //log.info("id = "+ jsonData.getString("id"));

        //TradeInfo tradeInfo = new TradeInfo();
        //tradeInfo.setBaseId(1);
        //tradeInfo.setBaseNum("1");
        //tradeInfo.setTradeDate("aa");
        //tradeInfo.setBaseSymbol("sd");
        //tradeInfo.setPrice("123");
        //tradeInfo.setQuoteId(1);
        //tradeInfo.setQuoteNum("1");
        //tradeInfo.setQuoteSymbol("aa");
        //tradeInfo.setSaleOrBuy("1");
        //tradeInfoMapper.insert(tradeInfo);
        List<TradeInfo> list = tradeInfoMapper.selectList(null);
        if(list != null && !list.isEmpty()){
            log.info("list.size === "+list.size());
            list.stream().forEach(t -> log.info("::"+t.toString()));
        }


    }
}
