package org.cointda;

import lombok.extern.slf4j.Slf4j;
import org.cointda.config.CoinMarketCapConfig;
import org.cointda.dto.CMCQuotesLatestDto;
import org.cointda.entity.CMCQuotesLatest;
import org.cointda.entity.TradeInfo;
import org.cointda.mapper.TradeInfoMapper;
import org.cointda.service.ICMCQuotesLatestService;
import org.cointda.service.feignc.ICMCMapFeignClient;
import org.cointda.service.feignc.ICMCListingsLatestFeignClient;
import org.cointda.service.feignc.ICMCQuotesLatestFeignClient;
import org.cointda.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    ICMCMapFeignClient ICMCMapFeignClient;
    @Autowired
    ICMCListingsLatestFeignClient ICMCListingsLatestFeignClient;
    @Autowired
    ICMCQuotesLatestFeignClient ICMCQuotesLatestFeignClient;

    @Autowired
    ICMCQuotesLatestService ICMCQuotesLatestService;

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
        List<CMCQuotesLatestDto> listCMCQuotesLatestDto = ICMCQuotesLatestService.getJson("id","1,1982", "USD", aux);
        List<CMCQuotesLatest> listCMCQuotesLatest = new ArrayList<>();
        if(listCMCQuotesLatestDto != null){
            List<CMCQuotesLatest> list = CopyUtil.copyList(listCMCQuotesLatestDto);
            listCMCQuotesLatestDto.stream().forEach(b -> log.info("dto::"+b.toString()));
            list.stream().forEach(b -> log.info("last::"+b.toString()));
            ICMCQuotesLatestService.saveOrUpdateBatch(list);

            ICMCQuotesLatestService.selectList(null).stream().forEach(a -> log.info("sql::"+a.toString()));
            //for(QuotesLatestDto dto : listQuotesLatestDto){
            //    QuotesLatest quotesLatest = new QuotesLatest();
            //    BeanUtils.copyProperties(dto, quotesLatest);
            //    listQuotesLatest.add(quotesLatest);
            //    log.info("listQuotesLatestDto = "+dto.toString());
            //    log.info("quotesLatest = "+quotesLatest.toString());
            //}
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
