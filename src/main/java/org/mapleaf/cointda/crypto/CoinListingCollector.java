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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.CryptocurrencyBean;
import org.mapleaf.cointda.util.DateHelper;
import org.mapleaf.cointda.util.YmalFc;

/**
 *
 * @author xuelf
 */
public class CoinListingCollector {

    private static final Logger logger = LogManager.getLogger(CoinListingCollector.class.getName());

    private static String apiKey;
    private static String uri;
    private static String customHeader;
    private static String httpHeaders;

    public CoinListingCollector() {
        YmalFc<CryptocurrencyBean> ymalFc = new YmalFc<>(CryptocurrencyBean.class);
        CryptocurrencyBean contact = ymalFc.build();
        apiKey = contact.getApiKey();
        uri = contact.getLatestlistings();
        customHeader = contact.getCustomHeader();
        httpHeaders = contact.getHttpHeader();
    }

    public List<CoinMarketCapListingBean> getCoinMarketListing() {
        List<NameValuePair> paratmers = new ArrayList<>();
        paratmers.add(new BasicNameValuePair("start", "1"));
        paratmers.add(new BasicNameValuePair("limit", "5000"));
        paratmers.add(new BasicNameValuePair("convert", "USD"));
        //paratmers.add(new BasicNameValuePair("convert_id","2781"));
        //paratmers.add(new BasicNameValuePair("sort","market_cap"));
        //paratmers.add(new BasicNameValuePair("sort_dir","asc"));//"asc"or"desc"
        //paratmers.add(new BasicNameValuePair("cryptocurrency_type","all"));//all"or "coins"or "tokens"

        String result = makeAPICall(uri, paratmers);
        //logger.info(result);
        ObjectMapper mapper = new ObjectMapper();
        List<CoinMarketCapListingBean> list = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(result);
            JsonNode data = rootNode.path("data");
            // 遍历 data 内的 array  
            if (data.isArray()) {
                for (JsonNode objNode : data) {
                    CoinMarketCapListingBean id = new CoinMarketCapListingBean();
                    id.setId(objNode.get("id").asInt());
                    if (!objNode.get("name").isNull()) {
                        id.setName(objNode.get("name").asText());
                    }
                    if (!objNode.get("symbol").isNull()) {
                        id.setSymbol(objNode.get("symbol").asText());
                    }
                    if (!objNode.get("slug").isNull()) {
                        id.setSlug(objNode.get("slug").asText());
                    }
                    if (!objNode.get("cmc_rank").isNull()) {
                        id.setCmc_rank(objNode.get("cmc_rank").asInt());
                    }
                    if (!objNode.get("date_added").isNull()) {
                        String date = DateHelper.utcToLocal(
                                objNode.get("date_added").asText());
                        //logger.info(date);
                        id.setDate_added(date);
                    } else {
                        id.setDate_added(DateHelper.toString(LocalDate.now()));
                    }
                    JsonNode platform = objNode.path("platform");
                    if (!platform.isNull()) {
                        id.setPlatform_id(platform.get("id").asInt());
                        id.setToken_address(platform.get("token_address").asText());
                    }
                    if (!objNode.get("num_market_pairs").isNull()) {
                        id.setNumMarketPairs(objNode.get("num_market_pairs").asInt());
                    }
                    if (!objNode.get("max_supply").isNull()) {
                        id.setMaxSupply(new BigDecimal(objNode.get("max_supply").asText()));
                    }
                    if (!objNode.get("circulating_supply").isNull()) {
                        id.setCirculatingSupply(new BigDecimal(objNode.get("circulating_supply").asText()));
                    }
                    if (!objNode.get("total_supply").isNull()) {
                        id.setTotalSupply(new BigDecimal(objNode.get("total_supply").asText()));
                    }
                    JsonNode quote = objNode.path("quote");
                    JsonNode usd = quote.path("USD");
                    if (!usd.isNull()) {
                        if (!usd.get("price").isNull()) {
                            id.setPrice(new BigDecimal(usd.get("price").asText()));
                        } else {
                            id.setPrice(new BigDecimal("0"));
                        }
                        if (!usd.get("volume_24h").isNull()) {
                            id.setVolume_24h(new BigDecimal(usd.get("volume_24h").asText()));
                        }
                        if (!usd.get("percent_change_1h").isNull()) {
                            id.setPercent_change_1h(new BigDecimal(usd.get("percent_change_1h").asText()));
                        }
                        if (!usd.get("percent_change_24h").isNull()) {
                            id.setPercent_change_24h(new BigDecimal(usd.get("percent_change_24h").asText()));
                        }
                        if (!usd.get("percent_change_7d").isNull()) {
                            id.setPercent_change_7d(new BigDecimal(usd.get("percent_change_7d").asText()));
                        }
                        if (!usd.get("market_cap").isNull()) {
                            id.setMarketCap(new BigDecimal(usd.get("market_cap").asText()));
                        }
                        if (!usd.get("last_updated").isNull()) {
                            String date = DateHelper.utcToLocal(
                                    usd.get("last_updated").asText());
                            id.setLastUpdated(date);
                        } else {
                            id.setLastUpdated(DateHelper.toString(LocalDate.now()));
                        }

                    }

                    list.add(id);
                }
            }
        } catch (JsonProcessingException ex) {
            logger.error(ex.toString());
        }
        return list;
    }

    public String makeAPICall(String uri, List<NameValuePair> parameters) {
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
        CoinListingCollector c = new CoinListingCollector();
        List<CoinMarketCapListingBean> list = c.getCoinMarketListing();
        for (CoinMarketCapListingBean bean : list) {
            logger.info(bean.getSymbol());
            logger.info(bean.getCmc_rank());
            if (bean.getVolume_24h() == null || bean.getVolume_24h().scale() < 9) {
                logger.info(bean.getVolume_24h());
            } else {
                logger.info(bean.getVolume_24h().setScale(8, RoundingMode.HALF_UP));
            }
            if (bean.getMarketCap() == null || bean.getMarketCap().scale() < 9) {
                logger.info(bean.getMarketCap());
            } else {
                logger.info(bean.getMarketCap().setScale(8, RoundingMode.HALF_UP));
            }
            if (bean.getMaxSupply() == null || bean.getMaxSupply().scale() < 9) {
                logger.info(bean.getMaxSupply());
            } else {
                logger.info(bean.getMaxSupply().setScale(8, RoundingMode.HALF_UP));
            }
            if (bean.getPrice() == null || bean.getPrice().scale() < 9) {
                logger.info(bean.getPrice());
            } else {
                logger.info(bean.getPrice().setScale(8, RoundingMode.HALF_UP));
            }
            logger.info("----------");
            //BigDecimal Percent_change_24h = bean.getPercent_change_24h();
            BigDecimal getPrice = bean.getPrice();
            if (getPrice == null) {
                continue;
            }
            if (getPrice.toString().length() >= 23) {
                logger.info("getPrice`s length >= 23");
            }

            BigDecimal getTotalSupply = bean.getTotalSupply();
            if (getTotalSupply == null) {
                continue;
            }
            if (getTotalSupply.toString().length() >= 23) {
                logger.info("getTotalSupply`s length >= 23");
            }

            BigDecimal getPercent_change_7d = bean.getPercent_change_7d();
            if (getPercent_change_7d == null) {
                continue;
            }
            if (getPercent_change_7d.toString().length() >= 23) {
                logger.info("getPercent_change_7d`s length >= 23");
            }
            BigDecimal getCirculatingSupply = bean.getCirculatingSupply();
            if (getCirculatingSupply == null) {
                continue;
            }
            if (getCirculatingSupply.toString().length() >= 23) {
                logger.info("getCirculatingSupply`s length >= 23");
            }

            BigDecimal getMaxSupply = bean.getMaxSupply();
            if (getMaxSupply == null) {
                continue;
            }
            if (getMaxSupply.toString().length() >= 23) {
                logger.info("getMaxSupply`s length >= 23");
            }

            BigDecimal Volume_24h = bean.getVolume_24h();
            if (Volume_24h == null) {
                continue;
            }
            if (Volume_24h.toString().length() >= 23) {
                logger.info("Volume_24h`s length >= 23");
            }
            String v = Volume_24h.toString();
            int i = v.indexOf(".");
            if (i != -1 && v.substring(0, v.indexOf(".")).length() > 15) {
                logger.info("整数部分大于15位");
            }

            BigDecimal MarketCap = bean.getMarketCap();
            if (MarketCap == null) {
                continue;
            }
            if (MarketCap.toString().length() >= 23) {
                logger.info("MarketCap`s length >= 23");
            }

            if(bean.getCmc_rank().intValue() == 1133){
                logger.info(bean);
            }

            if(bean.getCmc_rank().intValue() == 1134){
                logger.info(bean);
                //break;
            }

        }
        //logger.info(list);
    }
}
