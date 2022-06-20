package org.cointda;

import lombok.extern.slf4j.Slf4j;
import org.cointda.entity.TradeInfo;
import org.cointda.mapper.TradeInfoMapper;
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

    @Override
    public void run(String... args) throws Exception {
        log.info("加载顺序1");
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setBaseId(1);
        tradeInfo.setBaseNum("1");
        tradeInfo.setTradeDate("aa");
        tradeInfo.setBaseSymbol("sd");
        tradeInfo.setPrice("123");
        tradeInfo.setQuoteId(1);
        tradeInfo.setQuoteNum("1");
        tradeInfo.setQuoteSymbol("aa");
        tradeInfo.setSaleOrBuy("1");
        tradeInfoMapper.insert(tradeInfo);
        List<TradeInfo> list = tradeInfoMapper.selectList(null);
        if(list != null && !list.isEmpty()){
            log.info("list.size === "+list.size());
            list.stream().forEach(t -> log.info("::"+t.toString()));
        }


    }
}
