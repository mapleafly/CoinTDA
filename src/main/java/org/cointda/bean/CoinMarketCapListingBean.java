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

/**
 * @author xuelf
 */
public class CoinMarketCapListingBean {

    //CoinMarketCap定义的id
    private Integer id;
    //coin名称
    private String name;
    //简称(符号)
    private String symbol;
    //标称  The web URL friendly shorthand version of this cryptocurrency name
    private String slug;
    //The cryptocurrency's CoinMarketCap rank by market cap.
    private Integer cmc_rank;
    //首次加入CoinMarketCap的时间
    private String date_added;
    //平台id，如果是基于某个平台的token，比如基于eth的很多coin和token
    private Integer platform_id;
    //在平台中的创建地址,如果platform_id 为null，这里也是null
    private String token_address;
    //全部市场的相关交易对
    private Integer numMarketPairs;
    //最终的数量
    private String maxSupply;
    //当前正在流通的硬币的大概数量
    private String circulatingSupply;
    //当前大约存在的硬币总数（减去已被可验证燃烧的所有硬币）
    private String totalSupply;
    //整个市场的最新平均交易价格
    private String price;
    //24小时交易量
    private String volume_24h;
    //每种货币1小时的交易价格百分比变化
    private String percent_change_1h;
    //每种货币的24小时交易价格百分比变化
    private String percent_change_24h;
    //每种货币7天交易价格的百分比变化
    private String percent_change_7d;
    //CoinMarketCap计算的市值
    private String marketCap;
    //最后更新时间
    private String lastUpdated;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the numMarketPairs
     */
    public Integer getNumMarketPairs() {
        return numMarketPairs;
    }

    /**
     * @param numMarketPairs the numMarketPairs to set
     */
    public void setNumMarketPairs(Integer numMarketPairs) {
        this.numMarketPairs = numMarketPairs;
    }

    /**
     * @return the maxSupply
     */
    public String getMaxSupply() {
        return maxSupply;
    }

    /**
     * @param maxSupply the maxSupply to set
     */
    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    /**
     * @return the circulatingSupply
     */
    public String getCirculatingSupply() {
        return circulatingSupply;
    }

    /**
     * @param circulatingSupply the circulatingSupply to set
     */
    public void setCirculatingSupply(String circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    /**
     * @return the totalSupply
     */
    public String getTotalSupply() {
        return totalSupply;
    }

    /**
     * @param totalSupply the totalSupply to set
     */
    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the percent_change_1h
     */
    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    /**
     * @param percent_change_1h the percent_change_1h to set
     */
    public void setPercent_change_1h(String percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    /**
     * @return the percent_change_24h
     */
    public String getPercent_change_24h() {
        return percent_change_24h;
    }

    /**
     * @param percent_change_24h the percent_change_24h to set
     */
    public void setPercent_change_24h(String percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    /**
     * @return the percent_change_7d
     */
    public String getPercent_change_7d() {
        return percent_change_7d;
    }

    /**
     * @param percent_change_7d the percent_change_7d to set
     */
    public void setPercent_change_7d(String percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    /**
     * @return the marketCap
     */
    public String getMarketCap() {
        return marketCap;
    }

    /**
     * @param marketCap the marketCap to set
     */
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    /**
     * @return the lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the volume_24h
     */
    public String getVolume_24h() {
        return volume_24h;
    }

    /**
     * @param volume_24h the volume_24h to set
     */
    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug the slug to set
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return the cmc_rank
     */
    public Integer getCmc_rank() {
        return cmc_rank;
    }

    /**
     * @param cmc_rank the cmc_rank to set
     */
    public void setCmc_rank(Integer cmc_rank) {
        this.cmc_rank = cmc_rank;
    }

    /**
     * @return the date_added
     */
    public String getDate_added() {
        return date_added;
    }

    /**
     * @param date_added the date_added to set
     */
    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    /**
     * @return the platform_id
     */
    public Integer getPlatform_id() {
        return platform_id;
    }

    /**
     * @param platform_id the platform_id to set
     */
    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    /**
     * @return the token_address
     */
    public String getToken_address() {
        return token_address;
    }

    /**
     * @param token_address the token_address to set
     */
    public void setToken_address(String token_address) {
        this.token_address = token_address;
    }

    @Override
    public String toString() {
        return "CoinMarketListing [id:" + getId()
            + ",name:" + this.getName()
            + ",symbol:" + this.getSymbol()
            + ",slug:" + this.getSlug()
            + ",cmc_rank:" + this.getCmc_rank()
            + ",date_added:" + this.getDate_added()
            + ",platform_id:" + this.getPlatform_id()
            + ",token_address:" + this.getToken_address()
            + ",numMarketPairs:" + this.getNumMarketPairs()
            + ",max_supply:" + this.getMaxSupply()
            + ",circulating_supply:" + this.getCirculatingSupply()
            + ",total_supply:" + this.getTotalSupply()
            + ",price:" + this.getPrice()
            + ",volume_24h:" + this.getVolume_24h()
            + ",percent_change_1h:" + this.getPercent_change_1h()
            + ",percent_change_24h:" + this.getPercent_change_24h()
            + ",percent_change_7d:" + this.getPercent_change_7d()
            + ",market_cap:" + this.getMarketCap()
            + ",last_updated:" + this.getLastUpdated()
            + "]";
    }
}
