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
package org.cointda.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author xuelf
 */
public class CryptocurrencyBean {

    @JsonProperty
    private String apiKey;
    @JsonProperty
    private String customHeader;
    private String httpHeader;
    @JsonProperty
    private String coinMarketCapIDMap;
    @JsonProperty
    private String metadata;
    @JsonProperty
    private String latestlistings;
    @JsonProperty
    private String historicalListings;
    @JsonProperty
    private String latestQuotes;
    @JsonProperty
    private String historicalQuotes;
    @JsonProperty
    private String latestMarketPairs;
    @JsonProperty
    private String latestOHLCV;
    @JsonProperty
    private String historicalOHLCV;
    @JsonProperty
    private String pricePerformanceStats;

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the coinMarketCapIDMap
     */
    public String getCoinMarketCapIDMap() {
        return coinMarketCapIDMap;
    }

    /**
     * @param coinMarketCapIDMap the coinMarketCapIDMap to set
     */
    public void setCoinMarketCapIDMap(String coinMarketCapIDMap) {
        this.coinMarketCapIDMap = coinMarketCapIDMap;
    }

    /**
     * @return the metadata
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the latestlistings
     */
    public String getLatestlistings() {
        return latestlistings;
    }

    /**
     * @param latestlistings the latestlistings to set
     */
    public void setLatestlistings(String latestlistings) {
        this.latestlistings = latestlistings;
    }

    /**
     * @return the historicalListings
     */
    public String getHistoricalListings() {
        return historicalListings;
    }

    /**
     * @param historicalListings the historicalListings to set
     */
    public void setHistoricalListings(String historicalListings) {
        this.historicalListings = historicalListings;
    }

    /**
     * @return the latestQuotes
     */
    public String getLatestQuotes() {
        return latestQuotes;
    }

    /**
     * @param latestQuotes the latestQuotes to set
     */
    public void setLatestQuotes(String latestQuotes) {
        this.latestQuotes = latestQuotes;
    }

    /**
     * @return the historicalQuotes
     */
    public String getHistoricalQuotes() {
        return historicalQuotes;
    }

    /**
     * @param historicalQuotes the historicalQuotes to set
     */
    public void setHistoricalQuotes(String historicalQuotes) {
        this.historicalQuotes = historicalQuotes;
    }

    /**
     * @return the latestMarketPairs
     */
    public String getLatestMarketPairs() {
        return latestMarketPairs;
    }

    /**
     * @param latestMarketPairs the latestMarketPairs to set
     */
    public void setLatestMarketPairs(String latestMarketPairs) {
        this.latestMarketPairs = latestMarketPairs;
    }

    /**
     * @return the latestOHLCV
     */
    public String getLatestOHLCV() {
        return latestOHLCV;
    }

    /**
     * @param latestOHLCV the latestOHLCV to set
     */
    public void setLatestOHLCV(String latestOHLCV) {
        this.latestOHLCV = latestOHLCV;
    }

    /**
     * @return the historicalOHLCV
     */
    public String getHistoricalOHLCV() {
        return historicalOHLCV;
    }

    /**
     * @param historicalOHLCV the historicalOHLCV to set
     */
    public void setHistoricalOHLCV(String historicalOHLCV) {
        this.historicalOHLCV = historicalOHLCV;
    }

    /**
     * @return the pricePerformanceStats
     */
    public String getPricePerformanceStats() {
        return pricePerformanceStats;
    }

    /**
     * @param pricePerformanceStats the pricePerformanceStats to set
     */
    public void setPricePerformanceStats(String pricePerformanceStats) {
        this.pricePerformanceStats = pricePerformanceStats;
    }

    /**
     * @return the customHeader
     */
    public String getCustomHeader() {
        return customHeader;
    }

    /**
     * @param customHeader the customHeader to set
     */
    public void setCustomHeader(String customHeader) {
        this.customHeader = customHeader;
    }

    /**
     * @return the httpHeader
     */
    public String getHttpHeader() {
        return httpHeader;
    }

    /**
     * @param httpHeader the httpHeader to set
     */
    public void setHttpHeader(String httpHeader) {
        this.httpHeader = httpHeader;
    }
}
