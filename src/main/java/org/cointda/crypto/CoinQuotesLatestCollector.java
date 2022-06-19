package org.cointda.crypto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.cointda.bean.CoinQuotesLatestBean;
import org.cointda.bean.CryptocurrencyBean;
import org.cointda.util.DateHelper;
import org.cointda.util.HttpHelper;
import org.cointda.util.YmalFc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Copyright 2020 lif.
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

/**
 * @author lif
 */
@Slf4j
public class CoinQuotesLatestCollector {
    private static String apiKey;
    private static String uri;
    private static String customHeader;
    private static String httpHeaders;

    public CoinQuotesLatestCollector() {
        YmalFc<CryptocurrencyBean> ymalFc = new YmalFc<>(CryptocurrencyBean.class);
        CryptocurrencyBean contact = ymalFc.build();
        apiKey = contact.getApiKey();
        uri = contact.getLatestQuotes();
        customHeader = contact.getCustomHeader();
        httpHeaders = contact.getHttpHeader();
    }

    public static void main(String[] args) {
        CoinQuotesLatestCollector c = new CoinQuotesLatestCollector();
        List<CoinQuotesLatestBean> list = null;
        try {
            list = c.getQuotesLatest("id", "1982,9444");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // c.getQuotesLatest("symbol", "MDA,RDN");
        list.forEach((bean) -> {
            if (bean.getSymbol().toLowerCase().equals("knc") || bean.getSymbol().toLowerCase(Locale.ROOT).equals("kncl")) {
                log.info(bean.toString());
            }
        });

    }

    public List<CoinQuotesLatestBean> getQuotesLatest(String k, String v) throws IOException, URISyntaxException {
        List<NameValuePair> paratmers = new ArrayList<>();
        paratmers.add(new BasicNameValuePair(k, v));
        paratmers.add(new BasicNameValuePair("convert", "USD"));

        String result = new HttpHelper().makeAPICall(uri, httpHeaders, customHeader, apiKey, paratmers);
        // log.info(result);
        ObjectMapper mapper = new ObjectMapper();
        List<CoinQuotesLatestBean> list = new ArrayList<>();

        JsonNode rootNode = mapper.readTree(result);
        JsonNode status = rootNode.path("status");
        if (status.get("error_code").isNull() || !status.get("error_code").asText().equals("0")) {
            return list;
        }
        JsonNode data = rootNode.path("data");
        for (String id : v.split(",")) {
            JsonNode coin = data.path(id);
            // log.info(coin);
            CoinQuotesLatestBean bean = new CoinQuotesLatestBean();
            bean.setId(coin.get("id").asInt());
            if (!coin.get("name").isNull()) {
                bean.setName(coin.get("name").asText());
            }
            if (!coin.get("symbol").isNull()) {
                bean.setSymbol(coin.get("symbol").asText());
            }
            if (!coin.get("slug").isNull()) {
                bean.setSlug(coin.get("slug").asText());
            }
            if (!coin.get("num_market_pairs").isNull()) {
                bean.setNum_market_pairs(coin.get("num_market_pairs").asInt());
            }
            if (coin.has("date_added") && !coin.get("date_added").isNull()) {
                String date = DateHelper.utcToLocal(coin.get("date_added").asText());
                bean.setDate_added(date);
            } else {
                bean.setDate_added(DateHelper.toString(LocalDate.now()));
            }
            if (!coin.get("max_supply").isNull()) {
                bean.setMax_supply(coin.get("max_supply").asText());
            }
            if (!coin.get("circulating_supply").isNull()) {
                bean.setCirculating_supply(coin.get("circulating_supply").asText());
            }
            if (!coin.get("total_supply").isNull()) {
                bean.setTotal_supply(coin.get("total_supply").asText());
            }
            if (!coin.get("is_active").isNull()) {
                bean.setIs_active(coin.get("is_active").asInt());
            }

            JsonNode platform = coin.path("platform");
            if (!platform.isNull()) {
                bean.setPlatform_id(platform.get("id").asInt());
                bean.setToken_address(platform.get("token_address").asText());
            }

            if (!coin.get("cmc_rank").isNull()) {
                bean.setCmc_rank(coin.get("cmc_rank").asInt());
            }
            if (!coin.get("is_fiat").isNull()) {
                bean.setIs_fiat(coin.get("is_fiat").asInt());
            }

            JsonNode quote = coin.path("quote");
            JsonNode usd = quote.path("USD");
            if (!usd.isNull()) {
                if (!usd.get("price").isNull()) {
                    bean.setPrice(usd.get("price").asText());
                } else {
                    bean.setPrice("0");
                }
                if (!usd.get("volume_24h").isNull()) {
                    bean.setVolume_24h(usd.get("volume_24h").asText());
                }
                if (!usd.get("percent_change_1h").isNull()) {
                    bean.setPercent_change_1h(usd.get("percent_change_1h").asText());
                }
                if (!usd.get("percent_change_24h").isNull()) {
                    bean.setPercent_change_24h(usd.get("percent_change_24h").asText());
                }
                if (!usd.get("percent_change_7d").isNull()) {
                    bean.setPercent_change_7d(usd.get("percent_change_7d").asText());
                }
                if (!usd.get("market_cap").isNull()) {
                    bean.setMarket_cap(usd.get("market_cap").asText());
                }
                if (!usd.get("last_updated").isNull()) {
                    String date = DateHelper.utcToLocal(usd.get("last_updated").asText());
                    bean.setLastUpdated(date);
                } else {
                    bean.setLastUpdated(DateHelper.toString(LocalDate.now()));
                }
            }
            list.add(bean);
        }

        return list;
    }
}
