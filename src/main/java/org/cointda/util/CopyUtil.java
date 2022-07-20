package org.cointda.util;

import org.cointda.dto.Platform;
import org.cointda.dto.quote.Quote;
import org.cointda.dto.quote.QuotesLatestDto;
import org.cointda.entity.QuotesLatest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CopyUtil {

    public static <T> T copy(Object source, Class<T> c) {
        if (source == null) {
            return null;
        }
     return copy(source, c, (String) null);
    }

    public static <T> T copy(Object source, Class<T> c, @Nullable String... ignoreProperties){
        if (source == null) {
            return null;
        }
        try {
            T instance = c.getDeclaredConstructor().newInstance();
            if(ignoreProperties == null){
                BeanUtils.copyProperties(source, instance);
            }else{
                BeanUtils.copyProperties(source, instance, ignoreProperties);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E, T> List<T> copyList(List<E> sources, Class<T> c) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (E source : sources) {
            list.add(copy(source, c));
        }
        return list;
    }

    public static QuotesLatest copy(QuotesLatestDto dto){
        if(dto == null){
            return null;
        }
        QuotesLatest quotesLatest = new QuotesLatest();
        quotesLatest.setId(dto.getId());
        quotesLatest.setName(dto.getName());
        quotesLatest.setSymbol(dto.getSymbol());
        quotesLatest.setSlug(dto.getSlug());
        quotesLatest.setLastUpdated(dto.getLast_updated());
        quotesLatest.setNumMarketPairs(dto.getNum_market_pairs());
        quotesLatest.setDateAdded(dto.getDate_added());
        quotesLatest.setMaxSupply(dto.getMax_supply());
        quotesLatest.setCirculatingSupply(dto.getCirculating_supply());
        quotesLatest.setTotalSupply(dto.getTotal_supply());
        quotesLatest.setIsActive(dto.getIs_active());
        quotesLatest.setCmcRank(dto.getCmc_rank());

        Platform platform = dto.getPlatform();
        if(platform != null){
            quotesLatest.setPlatformId(platform.getId());
            quotesLatest.setTokenAddress(platform.getToken_address());
        }

        Quote quote = dto.getQuote();
        if(quote != null){
            quotesLatest.setPrice(String.valueOf(quote.getPrice()));
            quotesLatest.setVolume_24h(String.valueOf(quote.getVolume_24h()));
            quotesLatest.setVolume_change_24h(String.valueOf(quote.getVolume_change_24h()));
            quotesLatest.setPercent_change_1h(String.valueOf(quote.getPercent_change_1h()));
            quotesLatest.setPercent_change_24h(String.valueOf(quote.getPercent_change_24h()));
            quotesLatest.setPercent_change_7d(String.valueOf(quote.getPercent_change_7d()));
            quotesLatest.setPercent_change_30d(String.valueOf(quote.getPercent_change_30d()));
            quotesLatest.setPercent_change_60d(String.valueOf(quote.getPercent_change_60d()));
            quotesLatest.setPercent_change_90d(String.valueOf(quote.getPercent_change_90d()));
            quotesLatest.setMarketCap(String.valueOf(quote.getMarket_cap()));
            quotesLatest.setMarketCapDominance(String.valueOf(quote.getMarket_cap_dominance()));
        }

        return quotesLatest;
    }

    public static List<QuotesLatest> copyList(List<QuotesLatestDto> dtoList){
        if(dtoList == null || dtoList.isEmpty()){
            return null;
        }
        List<QuotesLatest> list = new ArrayList<>();
        for(QuotesLatestDto dto : dtoList){
            if(dto != null) {
                list.add(copy(dto));
            }
        }
        return list;
    }
}
