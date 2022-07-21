package org.cointda.util;

import org.cointda.dto.Platform;
import org.cointda.dto.quote.Quote;
import org.cointda.dto.quote.QuotesLatestDto;
import org.cointda.entity.CMCQuotesLatest;
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

    public static CMCQuotesLatest copy(QuotesLatestDto dto){
        if(dto == null){
            return null;
        }
        CMCQuotesLatest CMCQuotesLatest = new CMCQuotesLatest();
        CMCQuotesLatest.setId(dto.getId());
        CMCQuotesLatest.setName(dto.getName());
        CMCQuotesLatest.setSymbol(dto.getSymbol());
        CMCQuotesLatest.setSlug(dto.getSlug());
        CMCQuotesLatest.setLastUpdated(dto.getLast_updated());
        CMCQuotesLatest.setNumMarketPairs(dto.getNum_market_pairs());
        CMCQuotesLatest.setDateAdded(dto.getDate_added());
        CMCQuotesLatest.setMaxSupply(dto.getMax_supply());
        CMCQuotesLatest.setCirculatingSupply(dto.getCirculating_supply());
        CMCQuotesLatest.setTotalSupply(dto.getTotal_supply());
        CMCQuotesLatest.setIsActive(dto.getIs_active());
        CMCQuotesLatest.setCmcRank(dto.getCmc_rank());

        Platform platform = dto.getPlatform();
        if(platform != null){
            CMCQuotesLatest.setPlatformId(platform.getId());
            CMCQuotesLatest.setTokenAddress(platform.getToken_address());
        }

        Quote quote = dto.getQuote();
        if(quote != null){
            CMCQuotesLatest.setPrice(String.valueOf(quote.getPrice()));
            CMCQuotesLatest.setVolume24h(String.valueOf(quote.getVolume_24h()));
            CMCQuotesLatest.setVolumeChange24h(String.valueOf(quote.getVolume_change_24h()));
            CMCQuotesLatest.setPercentChange1h(String.valueOf(quote.getPercent_change_1h()));
            CMCQuotesLatest.setPercentChange24h(String.valueOf(quote.getPercent_change_24h()));
            CMCQuotesLatest.setPercentChange7d(String.valueOf(quote.getPercent_change_7d()));
            CMCQuotesLatest.setPercentChange30d(String.valueOf(quote.getPercent_change_30d()));
            CMCQuotesLatest.setPercentChange60d(String.valueOf(quote.getPercent_change_60d()));
            CMCQuotesLatest.setPercentChange90d(String.valueOf(quote.getPercent_change_90d()));
            CMCQuotesLatest.setMarketCap(String.valueOf(quote.getMarket_cap()));
            CMCQuotesLatest.setMarketCapDominance(String.valueOf(quote.getMarket_cap_dominance()));
        }

        return CMCQuotesLatest;
    }

    public static List<CMCQuotesLatest> copyList(List<QuotesLatestDto> dtoList){
        if(dtoList == null || dtoList.isEmpty()){
            return null;
        }
        List<CMCQuotesLatest> list = new ArrayList<>();
        for(QuotesLatestDto dto : dtoList){
            if(dto != null) {
                list.add(copy(dto));
            }
        }
        return list;
    }
}
