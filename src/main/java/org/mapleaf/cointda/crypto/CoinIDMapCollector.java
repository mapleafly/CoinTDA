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
package org.mapleaf.cointda.crypto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapIdBean;
import org.mapleaf.cointda.bean.CryptocurrencyBean;
import org.mapleaf.cointda.util.DateHelper;
import org.mapleaf.cointda.util.HttpHelper;
import org.mapleaf.cointda.util.YmalFc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static void main(String[] args) {
        CoinIDMapCollector c = new CoinIDMapCollector();
        List<CoinMarketCapIdBean> list = null;
        try {
            list = c.getCoinMarketCapIds();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        if (list != null && !list.isEmpty()) {
            for (CoinMarketCapIdBean bean : list) {
                if (bean.getSymbol().equalsIgnoreCase("knc") || bean.getSymbol().toLowerCase(Locale.ROOT).equals("kncl")) {
                    logger.info(bean.toString());
                }
            }
        }
        //logger.info(list);
    }

    public List<CoinMarketCapIdBean> getCoinMarketCapIds() throws URISyntaxException, IOException {
        List<NameValuePair> paratmers = new ArrayList<>();
        //paratmers.add(new BasicNameValuePair("symbol","BTC,USDT,BNB,MDA"));
        paratmers.add(new BasicNameValuePair("listing_status", "active"));
        //paratmers.add(new BasicNameValuePair("start","BTC,USDT,BNB,MDA"));
        paratmers.add(new BasicNameValuePair("limit", "5000"));
        paratmers.add(new BasicNameValuePair("sort", "cmc_rank"));
        //paratmers.add(new BasicNameValuePair("aux","platform,first_historical_data,last_historical_data,is_active"));
        String result = new HttpHelper().makeAPICall(uri, httpHeaders, customHeader, apiKey, paratmers);
        //logger.info(result);
        ObjectMapper mapper = new ObjectMapper();
        List<CoinMarketCapIdBean> list = new ArrayList<>();

        //map = mapper.readValue(result, CoinMarketCapIdMap.class);
        JsonNode rootNode = mapper.readTree(result);
        //CoinMarketCapIdMap map = mapper.treeToValue(rootNode, CoinMarketCapIdMap.class);
        JsonNode data = rootNode.path("data");
        // 遍历 data 内的 array
        if (data.isArray()) {
            for (JsonNode objNode : data) {
                //logger.info(objNode);
                CoinMarketCapIdBean id = new CoinMarketCapIdBean();
                id.setId(objNode.get("id").asInt());
                id.setName(objNode.get("name").asText());
                id.setSymbol(objNode.get("symbol").asText());
                id.setSlug(objNode.get("slug").asText());
                //id.setIs_active(objNode.get("is_active").asInt());
                id.setRank(objNode.get("rank").asInt());
                if (objNode.has("first_historical_data") && !objNode.get("first_historical_data").isNull()) {
                    String date = DateHelper.utcToLocal(
                        objNode.get("first_historical_data").asText());
                    id.setFirst_historical_data(date);
                } else {
                    id.setFirst_historical_data(null);
                }
                if (objNode.has("last_historical_data") && !objNode.get("last_historical_data").isNull()) {
                    String date = DateHelper.utcToLocal(
                        objNode.get("last_historical_data").asText());
                    id.setLast_historical_data(date);
                } else {
                    id.setLast_historical_data(null);
                }

                JsonNode p = objNode.path("platform");
                if (!p.isNull()) {
                    id.setPlatform_id(p.get("id").asInt());
                    id.setToken_address(p.get("token_address").asText());
                }
                list.add(id);
            }
        }
        return list;
    }
}
