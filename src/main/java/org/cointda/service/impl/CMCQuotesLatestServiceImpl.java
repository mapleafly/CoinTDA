package org.cointda.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.cointda.dto.CMCQuotesLatestDto;
import org.cointda.dto.Platform;
import org.cointda.dto.Quote;
import org.cointda.dto.Status;
import org.cointda.entity.CMCQuotesLatest;
import org.cointda.mapper.CMCQuotesLatestMapper;
import org.cointda.service.ICMCQuotesLatestService;
import org.cointda.service.feignc.ICMCQuotesLatestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class CMCQuotesLatestServiceImpl extends ServiceImpl<CMCQuotesLatestMapper, CMCQuotesLatest> implements ICMCQuotesLatestService {

    @Autowired
    ICMCQuotesLatestFeignClient ICMCQuotesLatestFeignClient;

    @Resource
    private CMCQuotesLatestMapper CMCQuotesLatestMapper;

    /**
     * @param key     查询参数 - by ID, symbol, or slug
     * @param values  查询值
     * @param convert quote种类
     * @param aux     结果中需要的辅助数据
     */
    @Override
    public List<CMCQuotesLatestDto> getJson(String key, String values, String convert, String aux) {
        List<CMCQuotesLatestDto> listCMCQuotesLatestDto = new ArrayList<>();
        String strJson = getHttpJson(key, values, convert, aux);
        if (strJson == null || strJson.isEmpty()) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(strJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Status status = getStatus(rootNode);
        if (status == null || status.getErrorCode() != 0) {
            return null;
        }

        JsonNode data = rootNode.path("data");
        for (String id : values.split(",")) {
            JsonNode coin = data.path(id);
            CMCQuotesLatestDto CMCQuotesLatestDto = new CMCQuotesLatestDto();
            CMCQuotesLatestDto.setId(coin.get("id").asInt());
            if (coin.hasNonNull("name")) {
                CMCQuotesLatestDto.setName(coin.get("name").asText());
            }
            if (coin.hasNonNull("symbol")) {
                CMCQuotesLatestDto.setSymbol(coin.get("symbol").asText());
            }
            if (coin.hasNonNull("slug")) {
                CMCQuotesLatestDto.setSlug(coin.get("slug").asText());
            }
            if (coin.hasNonNull("num_market_pairs")) {
                CMCQuotesLatestDto.setNum_market_pairs(coin.get("num_market_pairs").asInt());
            }
            if (coin.hasNonNull("date_added")) {
                //String date = DateHelper.utcToLocal(coin.get("date_added").asText());
                CMCQuotesLatestDto.setDate_added(coin.get("date_added").asText());
            }
            if (coin.hasNonNull("max_supply")) {
                CMCQuotesLatestDto.setMax_supply(coin.get("max_supply").asText());
            }
            if (coin.hasNonNull("circulating_supply")) {
                CMCQuotesLatestDto.setCirculating_supply(coin.get("circulating_supply").asText());
            }
            if (coin.hasNonNull("total_supply")) {
                CMCQuotesLatestDto.setTotal_supply(coin.get("total_supply").asText());
            }
            if (coin.hasNonNull("is_active")) {
                CMCQuotesLatestDto.setIs_active(coin.get("is_active").asInt());
            }
            if (coin.hasNonNull("is_fiat")) {
                CMCQuotesLatestDto.setIs_fiat(coin.get("is_fiat").asInt());
            }
            if (coin.hasNonNull("cmc_rank")) {
                CMCQuotesLatestDto.setCmc_rank(coin.get("cmc_rank").asInt());
            }
            if (coin.hasNonNull("last_updated")) {
                log.info("11111111111111111111");
                log.info(coin.get("last_updated").asText());
                //String date = DateHelper.utcToLocal(coin.get("last_updated").asText());
                CMCQuotesLatestDto.setLast_updated(coin.get("last_updated").asText());
            }
            if (coin.hasNonNull("self_reported_circulating_supply")) {
                CMCQuotesLatestDto.setSelf_reported_circulating_supply(coin.get("self_reported_circulating_supply").asText());
            }
            if (coin.hasNonNull("self_reported_market_cap")) {
                CMCQuotesLatestDto.setSelf_reported_market_cap(coin.get("self_reported_market_cap").asText());
            }

            if (coin.hasNonNull("platform")) {
                Platform p = mapper.convertValue(coin.path("platform"), Platform.class);
                CMCQuotesLatestDto.setPlatform(p);
            }

            Quote quoteDto = getQuote(coin, convert);
            if(quoteDto != null){
                CMCQuotesLatestDto.setQuote(quoteDto);
            }

            List<String> listTags = getTags(coin);
            if (listTags != null) {
                CMCQuotesLatestDto.setTags(listTags);
            }

            listCMCQuotesLatestDto.add(CMCQuotesLatestDto);
        }

        return listCMCQuotesLatestDto;
    }

    private String getHttpJson(String key, String values, String convert, String aux) {
        String strJson;
        switch (key) {
            case "id":
                strJson = ICMCQuotesLatestFeignClient.getHttpJsonById(values, convert,
                    aux);
                break;
            case "symbol":
                strJson = ICMCQuotesLatestFeignClient.getHttpJsonBySymbol(values, convert,
                    aux);
                break;
            case "slug":
                strJson = ICMCQuotesLatestFeignClient.getHttpJsonBySlug(values, convert,
                    aux);
                break;
            default:
                strJson = null;
        }
        return strJson;
    }


    private Status getStatus(JsonNode rootNode) {
        Status status = null;
        if(rootNode.hasNonNull("status")){
            JsonNode jsonStatus = rootNode.path("status");
            try {
                ObjectMapper mapper = new ObjectMapper();
                status = mapper.readValue(jsonStatus.toString(), Status.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return status;
    }

    private Quote getQuote(JsonNode coin, String convert){
        Quote quote = null;
        if(coin.hasNonNull("quote")){
            JsonNode jsonQuote = coin.path("quote");
            if(jsonQuote.hasNonNull(convert)){
                JsonNode quoteConvert = jsonQuote.path(convert);
                ObjectMapper mapper = new ObjectMapper();
                quote = mapper.convertValue(quoteConvert, Quote.class);
                if (quote != null) {
                    quote.setQuote(convert);
                }
            }
        }
        return quote;
    }

    private List<String> getTags(JsonNode coin){
        List<String> listTags = null;
        if (coin.hasNonNull("tags")) {
            JsonNode tags = coin.path("tags");
            try {
                listTags =
                    new ObjectMapper().readValue(tags.traverse(), new TypeReference<ArrayList<String>>() {});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listTags;
    }

    /**
     *  插入数据
     * @param entity
     * @return int n 表示插入几条数据，如果n=0 表示插入失败
     */
    @Override
    public int insert(CMCQuotesLatest entity) {
        return CMCQuotesLatestMapper.insert(entity);
    }

    @Override
    public int update(CMCQuotesLatest entity) {
        return CMCQuotesLatestMapper.updateById(entity);
    }

    @Override
    public int deleteByID(Serializable id) {
        return CMCQuotesLatestMapper.deleteById(id);
    }

    @Override
    public int deleteById(CMCQuotesLatest entity) {
        return CMCQuotesLatestMapper.deleteById(entity);
    }

    @Override
    public CMCQuotesLatest selectById(Serializable id) {
        return CMCQuotesLatestMapper.selectById(id);
    }

    @Override
    public List<CMCQuotesLatest> selectBatchIds(Collection<? extends Serializable> idList) {
        return CMCQuotesLatestMapper.selectBatchIds(idList);
    }

    @Override
    public List<CMCQuotesLatest> selectList(Wrapper<CMCQuotesLatest> queryWrapper) {
        return CMCQuotesLatestMapper.selectList(queryWrapper);
    }
}
