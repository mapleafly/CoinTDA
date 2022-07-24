package org.cointda.util;

import org.cointda.dto.CMCMapDto;
import org.cointda.dto.CMCQuotesLatestDto;
import org.cointda.dto.Platform;
import org.cointda.dto.Quote;
import org.cointda.entity.CMCMap;
import org.cointda.entity.CMCQuotesLatest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
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
    public static CMCMap copy(CMCMapDto dto){
        if(dto == null){
            return null;
        }

        CMCMap cmcMap = new CMCMap();
        cmcMap.setId(dto.getId());
        cmcMap.setName(dto.getName());
        cmcMap.setSymbol(dto.getSymbol());
        cmcMap.setSlug(dto.getSlug());
        cmcMap.setIsActive(dto.getIsActive());
        cmcMap.setRank(dto.getRank());

        String firstDateTime = dto.getFirstHistoricalData().replace("Z", " UTC");
        String lastDateTime = dto.getLastHistoricalData().replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            cmcMap.setFirstHistoricalData(defaultFormat.format(format.parse(firstDateTime)));
            cmcMap.setLastHistoricalData(defaultFormat.format(format.parse(lastDateTime)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Platform platform = dto.getPlatform();
        if(platform != null){
            cmcMap.setPlatformId(platform.getId());
            cmcMap.setTokenAddress(platform.getToken_address());
        }

        return cmcMap;
    }
    public static List<CMCMap> copyListCMCMap(List<CMCMapDto> dtoList){
        if(dtoList == null || dtoList.isEmpty()){
            return null;
        }
        List<CMCMap> list = new ArrayList<>();
        for(CMCMapDto dto : dtoList){
            if(dto != null) {
                list.add(copy(dto));
            }
        }
        return list;
    }

    public static CMCQuotesLatest copy(CMCQuotesLatestDto dto){
        if(dto == null){
            return null;
        }
        CMCQuotesLatest CMCQuotesLatest = new CMCQuotesLatest();
        CMCQuotesLatest.setId(dto.getId());
        CMCQuotesLatest.setName(dto.getName());
        CMCQuotesLatest.setSymbol(dto.getSymbol());
        CMCQuotesLatest.setSlug(dto.getSlug());

        String lastUpdated = dto.getLast_updated().replace("Z", " UTC");
        String dataAdded = dto.getDate_added().replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            CMCQuotesLatest.setLastUpdated(defaultFormat.format(format.parse(lastUpdated)));
            CMCQuotesLatest.setDateAdded(defaultFormat.format(format.parse(dataAdded)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //CMCQuotesLatest.setLastUpdated(dto.getLast_updated());
        CMCQuotesLatest.setNumMarketPairs(dto.getNum_market_pairs());
        //CMCQuotesLatest.setDateAdded(dto.getDate_added());
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

    public static List<CMCQuotesLatest> copyListCMCQuotes(List<CMCQuotesLatestDto> dtoList){
        if(dtoList == null || dtoList.isEmpty()){
            return null;
        }
        List<CMCQuotesLatest> list = new ArrayList<>();
        for(CMCQuotesLatestDto dto : dtoList){
            if(dto != null) {
                list.add(copy(dto));
            }
        }
        return list;
    }
}
