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
package org.cointda.bean;

import lombok.Data;

/**
 * @author lif
 */
@Data
public class CoinQuotesLatestBean {

    //CoinMarketCap定义的id
    private Integer id;
    //coin名称
    private String name;
    //简称(符号)
    private String symbol;
    //标称  The web URL friendly shorthand version of this cryptocurrency name
    private String slug;
    //coin在市场中的交易对数量
    private Integer num_market_pairs;
    //首次加入CoinMarketCap的时间
    private String date_added;
    //最终的数量
    private String max_supply;
    //当前正在流通的硬币的大概数量
    private String circulating_supply;
    //当前大约存在的硬币总数（减去已被可验证燃烧的所有硬币）
    private String total_supply;
    //是否激活，1-yes  0=no
    private Integer is_active;
    //平台id，如果是基于某个平台的token，比如基于eth的很多coin和token
    private Integer platform_id;
    //在平台中的创建地址,如果platform_id 为null，这里也是null
    private String token_address;
    //The cryptocurrency's CoinMarketCap rank by market cap.
    private Integer cmc_rank;
    private Integer is_fiat;
    //最后更新时间
    private String lastUpdated;
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
    private String market_cap;

    ///**
    // * @return the id
    // */
    //public Integer getId() {
    //    return id;
    //}
    //
    ///**
    // * @param id the id to set
    // */
    //public void setId(Integer id) {
    //    this.id = id;
    //}
    //
    ///**
    // * @return the name
    // */
    //public String getName() {
    //    return name;
    //}
    //
    ///**
    // * @param name the name to set
    // */
    //public void setName(String name) {
    //    this.name = name;
    //}
    //
    ///**
    // * @return the symbol
    // */
    //public String getSymbol() {
    //    return symbol;
    //}
    //
    ///**
    // * @param symbol the symbol to set
    // */
    //public void setSymbol(String symbol) {
    //    this.symbol = symbol;
    //}
    //
    ///**
    // * @return the slug
    // */
    //public String getSlug() {
    //    return slug;
    //}
    //
    ///**
    // * @param slug the slug to set
    // */
    //public void setSlug(String slug) {
    //    this.slug = slug;
    //}
    //
    ///**
    // * @return the num_market_pairs
    // */
    //public Integer getNum_market_pairs() {
    //    return num_market_pairs;
    //}
    //
    ///**
    // * @param num_market_pairs the num_market_pairs to set
    // */
    //public void setNum_market_pairs(Integer num_market_pairs) {
    //    this.num_market_pairs = num_market_pairs;
    //}
    //
    ///**
    // * @return the date_added
    // */
    //public String getDate_added() {
    //    return date_added;
    //}
    //
    ///**
    // * @param date_added the date_added to set
    // */
    //public void setDate_added(String date_added) {
    //    this.date_added = date_added;
    //}
    //
    ///**
    // * @return the max_supply
    // */
    //public String getMax_supply() {
    //    return max_supply;
    //}
    //
    ///**
    // * @param max_supply the max_supply to set
    // */
    //public void setMax_supply(String max_supply) {
    //    this.max_supply = max_supply;
    //}
    //
    ///**
    // * @return the circulating_supply
    // */
    //public String getCirculating_supply() {
    //    return circulating_supply;
    //}
    //
    ///**
    // * @param circulating_supply the circulating_supply to set
    // */
    //public void setCirculating_supply(String circulating_supply) {
    //    this.circulating_supply = circulating_supply;
    //}
    //
    ///**
    // * @return the total_supply
    // */
    //public String getTotal_supply() {
    //    return total_supply;
    //}
    //
    ///**
    // * @param total_supply the total_supply to set
    // */
    //public void setTotal_supply(String total_supply) {
    //    this.total_supply = total_supply;
    //}
    //
    ///**
    // * @return the is_active
    // */
    //public Integer getIs_active() {
    //    return is_active;
    //}
    //
    ///**
    // * @param is_active the is_active to set
    // */
    //public void setIs_active(Integer is_active) {
    //    this.is_active = is_active;
    //}
    //
    ///**
    // * @return the platform_id
    // */
    //public Integer getPlatform_id() {
    //    return platform_id;
    //}
    //
    ///**
    // * @param platform_id the platform_id to set
    // */
    //public void setPlatform_id(Integer platform_id) {
    //    this.platform_id = platform_id;
    //}
    //
    ///**
    // * @return the token_address
    // */
    //public String getToken_address() {
    //    return token_address;
    //}
    //
    ///**
    // * @param token_address the token_address to set
    // */
    //public void setToken_address(String token_address) {
    //    this.token_address = token_address;
    //}
    //
    ///**
    // * @return the cmc_rank
    // */
    //public Integer getCmc_rank() {
    //    return cmc_rank;
    //}
    //
    ///**
    // * @param cmc_rank the cmc_rank to set
    // */
    //public void setCmc_rank(Integer cmc_rank) {
    //    this.cmc_rank = cmc_rank;
    //}
    //
    ///**
    // * @return the is_fiat
    // */
    //public Integer getIs_fiat() {
    //    return is_fiat;
    //}
    //
    ///**
    // * @param is_fiat the is_fiat to set
    // */
    //public void setIs_fiat(Integer is_fiat) {
    //    this.is_fiat = is_fiat;
    //}
    //
    ///**
    // * @return the lastUpdated
    // */
    //public String getLastUpdated() {
    //    return lastUpdated;
    //}
    //
    ///**
    // * @param lastUpdated the lastUpdated to set
    // */
    //public void setLastUpdated(String lastUpdated) {
    //    this.lastUpdated = lastUpdated;
    //}
    //
    ///**
    // * @return the price
    // */
    //public String getPrice() {
    //    return price;
    //}
    //
    ///**
    // * @param price the price to set
    // */
    //public void setPrice(String price) {
    //    this.price = price;
    //}
    //
    ///**
    // * @return the volume_24h
    // */
    //public String getVolume_24h() {
    //    return volume_24h;
    //}
    //
    ///**
    // * @param volume_24h the volume_24h to set
    // */
    //public void setVolume_24h(String volume_24h) {
    //    this.volume_24h = volume_24h;
    //}
    //
    ///**
    // * @return the percent_change_1h
    // */
    //public String getPercent_change_1h() {
    //    return percent_change_1h;
    //}
    //
    ///**
    // * @param percent_change_1h the percent_change_1h to set
    // */
    //public void setPercent_change_1h(String percent_change_1h) {
    //    this.percent_change_1h = percent_change_1h;
    //}
    //
    ///**
    // * @return the percent_change_24h
    // */
    //public String getPercent_change_24h() {
    //    return percent_change_24h;
    //}
    //
    ///**
    // * @param percent_change_24h the percent_change_24h to set
    // */
    //public void setPercent_change_24h(String percent_change_24h) {
    //    this.percent_change_24h = percent_change_24h;
    //}
    //
    ///**
    // * @return the percent_change_7d
    // */
    //public String getPercent_change_7d() {
    //    return percent_change_7d;
    //}
    //
    ///**
    // * @param percent_change_7d the percent_change_7d to set
    // */
    //public void setPercent_change_7d(String percent_change_7d) {
    //    this.percent_change_7d = percent_change_7d;
    //}
    //
    ///**
    // * @return the market_cap
    // */
    //public String getMarket_cap() {
    //    return market_cap;
    //}
    //
    ///**
    // * @param market_cap the market_cap to set
    // */
    //public void setMarket_cap(String market_cap) {
    //    this.market_cap = market_cap;
    //}
    //
    //@Override
    //public String toString() {
    //    return "CoinQuotesLatestBean ["
    //        + "id:" + getId()
    //        + ",name:" + this.getName()
    //        + ",symbol:" + this.getSymbol()
    //        + ",slug:" + this.getSlug()
    //        + ",num_market_pairs:" + this.getNum_market_pairs()
    //        + ",date_added:" + this.getDate_added()
    //        + ",max_supply:" + this.getMax_supply()
    //        + ",circulating_supply:" + this.getCirculating_supply()
    //        + ",total_supply:" + this.getTotal_supply()
    //        + ",is_active:" + this.getIs_active()
    //        + ",platform_id:" + this.getPlatform_id()
    //        + ",token_address:" + this.getToken_address()
    //        + ",cmc_rank:" + this.getCmc_rank()
    //        + ",is_fiat:" + this.getIs_fiat()
    //        + ",last_updated:" + this.getLastUpdated()
    //        + ",price:" + this.getPrice()
    //        + ",volume_24h:" + this.getVolume_24h()
    //        + ",percent_change_1h:" + this.getPercent_change_1h()
    //        + ",percent_change_24h:" + this.getPercent_change_24h()
    //        + ",percent_change_7d:" + this.getPercent_change_7d()
    //        + ",market_cap:" + this.getMarket_cap()
    //        + "]";
    //}
}
