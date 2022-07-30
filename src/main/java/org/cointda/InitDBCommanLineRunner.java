package org.cointda;

import lombok.extern.slf4j.Slf4j;
import org.cointda.service.ICMCMapService;
import org.cointda.service.ICMCQuotesLatestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 1)
public class InitDBCommanLineRunner implements CommandLineRunner {


    @Autowired
    ICMCQuotesLatestService icmcQuotesLatestService;
    @Autowired
    ICMCMapService icmcMapService;

    @Override
    public void run(String... args) throws Exception {
        log.info("加载顺序1");

        ///////// quote test //////////
/*

        String aux = "num_market_pairs,cmc_rank,date_added,platform,max_supply,circulating_supply,total_supply,is_active";
        List<CMCQuotesLatestDto> listCMCQuotesLatestDto = icmcQuotesLatestService.getJson("id", "1,1027,825,3408,1839,2010,4687,52,5426,74,4943", "USD", aux);
        if (listCMCQuotesLatestDto != null) {
            List<CMCQuotesLatest> list = CopyUtil.copyListCMCQuotes(listCMCQuotesLatestDto);
            //listCMCQuotesLatestDto.stream().forEach(b -> log.info("dto::" + b.toString()));
            //list.stream().forEach(b -> log.info("last::" + b.toString()));
            icmcQuotesLatestService.saveOrUpdateBatch(list);

            icmcQuotesLatestService.selectList(null).stream().forEach(a -> log.info("CMCQuotesLatest::sql::" + a.toString()));
        }
*/
        ////// CMCMap test ////////

     /*   List<CMCMapDto> cmcMapDtoList = icmcMapService.getJson(50, "cmc_rank");
        List<CMCMap> cmcMaps = CopyUtil.copyListCMCMap(cmcMapDtoList);
        icmcMapService.saveOrUpdateBatch(cmcMaps);
        icmcMapService.selectList(null).stream().forEach(a -> log.info("CMCMap::sql::"+a.toString()));
*/

    }
}
