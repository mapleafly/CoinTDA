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
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.CryptocurrencyBean;
import org.mapleaf.cointda.util.DateHelper;
import org.mapleaf.cointda.util.HttpHelper;
import org.mapleaf.cointda.util.YmalFc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
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

  public static void main(String[] args) {
    CoinListingCollector c = new CoinListingCollector();
    List<CoinMarketCapListingBean> list = null;
    try {
      list = c.getCoinMarketListing();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    list.forEach((bean) -> {
      if (bean.getSymbol().toLowerCase().equals("knc") || bean.getSymbol().toLowerCase(Locale.ROOT).equals("kncl")) {
        logger.info(bean.toString());
      }
    });
  }

  public List<CoinMarketCapListingBean> getCoinMarketListing() throws URISyntaxException, IOException {
    List<NameValuePair> paratmers = new ArrayList<>();
    paratmers.add(new BasicNameValuePair("start", "1"));
    paratmers.add(new BasicNameValuePair("limit", "5000"));
    paratmers.add(new BasicNameValuePair("convert", "USD"));
    //paratmers.add(new BasicNameValuePair("convert_id","2781"));
    //paratmers.add(new BasicNameValuePair("sort","market_cap"));
    //paratmers.add(new BasicNameValuePair("sort_dir","asc"));//"asc"or"desc"
    //paratmers.add(new BasicNameValuePair("cryptocurrency_type","all"));//all"or "coins"or "tokens"

    String result = new HttpHelper().makeAPICall(uri, httpHeaders, customHeader, apiKey, paratmers);
    //logger.info(result);
    ObjectMapper mapper = new ObjectMapper();
    List<CoinMarketCapListingBean> list = new ArrayList<>();
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
        if (objNode.has("date_added") && !objNode.get("date_added").isNull()) {
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
          id.setMaxSupply(objNode.get("max_supply").asText());
        }
        if (!objNode.get("circulating_supply").isNull()) {
          id.setCirculatingSupply(objNode.get("circulating_supply").asText());
        }
        if (!objNode.get("total_supply").isNull()) {
          id.setTotalSupply(objNode.get("total_supply").asText());
        }
        JsonNode quote = objNode.path("quote");
        JsonNode usd = quote.path("USD");
        if (!usd.isNull()) {
          if (!usd.get("price").isNull()) {
            id.setPrice(usd.get("price").asText());
          } else {
            id.setPrice("0");
          }
          if (!usd.get("volume_24h").isNull()) {
            id.setVolume_24h(usd.get("volume_24h").asText());
          }
          if (!usd.get("percent_change_1h").isNull()) {
            id.setPercent_change_1h(usd.get("percent_change_1h").asText());
          }
          if (!usd.get("percent_change_24h").isNull()) {
            id.setPercent_change_24h(usd.get("percent_change_24h").asText());
          }
          if (!usd.get("percent_change_7d").isNull()) {
            id.setPercent_change_7d(usd.get("percent_change_7d").asText());
          }
          if (!usd.get("market_cap").isNull()) {
            id.setMarketCap(usd.get("market_cap").asText());
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
    return list;
  }
}
