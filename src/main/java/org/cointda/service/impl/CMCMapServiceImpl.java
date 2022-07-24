package org.cointda.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.cointda.dto.CMCMapDto;
import org.cointda.dto.Quote;
import org.cointda.dto.Status;
import org.cointda.entity.CMCMap;
import org.cointda.mapper.CMCMapMapper;
import org.cointda.service.ICMCMapService;
import org.cointda.service.feignc.ICMCMapFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class CMCMapServiceImpl extends ServiceImpl<CMCMapMapper, CMCMap> implements ICMCMapService {

    @Autowired
    ICMCMapFeignClient icmcMapFeignClient;

    @Override
    public List<CMCMapDto> getJson(String listing_status, Integer start, Integer limit, String sort, String aux) {
        String jsonMap = icmcMapFeignClient.getHttpJson(listing_status, start, limit, sort, aux);
        return jsonToDto(jsonMap);
    }

    @Override
    public List<CMCMapDto> getJson(Integer start, Integer limit, String sort, String aux) {
        String jsonMap = icmcMapFeignClient.getHttpJson(start, limit, sort, aux);
        return jsonToDto(jsonMap);
    }

    @Override
    public List<CMCMapDto> getJson(Integer start, Integer limit, String sort) {
        String jsonMap = icmcMapFeignClient.getHttpJson(start, limit, sort);
        return jsonToDto(jsonMap);
    }

    @Override
    public List<CMCMapDto> getJson(Integer limit, String sort) {
        String jsonMap = icmcMapFeignClient.getHttpJson(limit, sort);
        return jsonToDto(jsonMap);
    }

    @Override
    public List<CMCMapDto> getJson(Integer limit) {
        String jsonMap = icmcMapFeignClient.getHttpJson(limit);
        return jsonToDto(jsonMap);
    }

    private List<CMCMapDto> jsonToDto(String jsonMap){
        if (jsonMap == null || jsonMap.isEmpty()) {
            return null;
        }
        List<CMCMapDto> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();


        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonMap);
            JsonNode data = rootNode.path("data");
            list = new ObjectMapper().readValue(data.traverse(), new TypeReference<ArrayList<CMCMapDto>>() {});

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //try {
        //    JSONObject cmcMap = new JSONObject(jsonMap);
        //    JSONObject data = (JSONObject)cmcMap.get("data");
        //} catch (JSONException e) {
        //    throw new RuntimeException(e);
        //}




        return list;

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


    @Override
    public int insert(CMCMap entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int update(CMCMap entity) {
        return baseMapper.updateById(entity);
    }

    @Override
    public int deleteByID(Serializable id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int deleteById(CMCMap entity) {
        return baseMapper.updateById(entity);
    }

    @Override
    public CMCMap selectById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<CMCMap> selectBatchIds(Collection<? extends Serializable> idList) {
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    public List<CMCMap> selectList(Wrapper<CMCMap> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
}
