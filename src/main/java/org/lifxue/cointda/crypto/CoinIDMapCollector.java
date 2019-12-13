/*
 * Copyright 2019 xuelf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lifxue.cointda.crypto;

/**
 * This example uses the Apache HTTPComponents library.
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.bean.CoinMarketCapIdBean;
import org.lifxue.cointda.bean.CryptocurrencyBean;
import org.lifxue.cointda.util.DateHelper;
import org.lifxue.cointda.util.YmalFc;

public class CoinIDMapCollector {

    private static final Logger logger = LogManager.getLogger(CoinIDMapCollector.class.getName());

    private static String apiKey;
    private static String uri;
    private static String customHeader;
    private static String httpHeaders;

    public CoinIDMapCollector() {
        YmalFc<CryptocurrencyBean> ymalFc = new YmalFc<>(CryptocurrencyBean.class);
        CryptocurrencyBean contact = ymalFc.build();
        apiKey = contact.getApiKey();
        uri = contact.getCoinMarketCapIDMap();
        customHeader = contact.getCustomHeader();
        httpHeaders = contact.getHttpHeader();
    }

    public List<CoinMarketCapIdBean> getCoinMarketCapIds() {
        List<NameValuePair> paratmers = new ArrayList<>();
        //paratmers.add(new BasicNameValuePair("symbol","BTC,USDT,BNB,MDA"));
        //paratmers.add(new BasicNameValuePair("listing_status","inactive "));
        //paratmers.add(new BasicNameValuePair("start","BTC,USDT,BNB,MDA"));
        paratmers.add(new BasicNameValuePair("limit","50"));
        //paratmers.add(new BasicNameValuePair("sort","id"));
        //paratmers.add(new BasicNameValuePair("aux","platform,first_historical_data,last_historical_data,is_active"));
        String result = makeAPICall(uri, paratmers);
        //logger.info(result);
        ObjectMapper mapper = new ObjectMapper();
        List<CoinMarketCapIdBean> list = new ArrayList<>();
        try {
            //map = mapper.readValue(result, CoinMarketCapIdMap.class);
            JsonNode rootNode = mapper.readTree(result);
            //CoinMarketCapIdMap map = mapper.treeToValue(rootNode, CoinMarketCapIdMap.class);
            JsonNode data = rootNode.path("data");
            // 遍历 data 内的 array  
            if (data.isArray()) {
                for (JsonNode objNode : data) {
                    CoinMarketCapIdBean id = new CoinMarketCapIdBean();
                    id.setcId(objNode.get("id").asInt());
                    id.setName(objNode.get("name").asText());
                    id.setSymbol(objNode.get("symbol").asText());
                    id.setSlug(objNode.get("slug").asText());
                    id.setIs_active(objNode.get("is_active").asInt());
                    id.setRank(objNode.get("rank").asInt());
                    if (!objNode.get("first_historical_data").isNull()) {
                        String date = DateHelper.utcToLocal(
                                objNode.get("first_historical_data").asText());
                        id.setFirst_historical_data(date);
                    }else{
                        id.setFirst_historical_data(DateHelper.toString(LocalDate.now()));
                    }
                    if (!objNode.get("last_historical_data").isNull()) {
                        String date = DateHelper.utcToLocal(
                                objNode.get("last_historical_data").asText());
                        id.setLast_historical_data(date);
                    }else{
                        id.setLast_historical_data(DateHelper.toString(LocalDate.now()));
                    }

                    JsonNode p = objNode.path("platform");
                    if (!p.isNull()) {
                        id.setpId(p.get("id").asInt());
                        id.setToken_address(p.get("token_address").asText());
                    }
                    list.add(id);
                }
            }
        } catch (JsonProcessingException ex) {
            logger.error(ex.toString());
        }
        return list;
    }

    private String makeAPICall(String uri, List<NameValuePair> parameters) {
        String response_content = "";
        try {

            URIBuilder query = new URIBuilder(uri);
            query.addParameters(parameters);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(query.build());

            request.setHeader(HttpHeaders.ACCEPT, httpHeaders);
            request.addHeader(customHeader, apiKey);

            try (CloseableHttpResponse response = client.execute(request)) {
                //System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                response_content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            }

        } catch (URISyntaxException | IOException ex) {
            logger.error(ex.toString());
        }
        return response_content;
    }

    public static void main(String[] args) {
        CoinIDMapCollector c = new CoinIDMapCollector();
        List<CoinMarketCapIdBean> list = c.getCoinMarketCapIds();
        logger.info(list.size());
    }
}
