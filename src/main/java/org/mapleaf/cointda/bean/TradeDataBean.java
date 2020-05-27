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
package org.mapleaf.cointda.bean;


/**
 *
 * @author xuelf
 */
public class TradeDataBean {
    
    //自增id
    private Integer id;
    //base coin id
    private Integer base_id;
    // base coin 简称
    private String base_symbol;
    //quote coin id
    private Integer quote_id;
    // quote coin 简称
    private String quote_symbol;
    //买或者卖
    private String sale_or_buy;
    //买入或卖出价格
    private String price;
    //基准货币买入或卖出数量
    private String base_num;
    //计价货币数量
    private String quote_num;
    //交易时间
    private String trade_date;

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
     * @return the base_id
     */
    public Integer getBase_id() {
        return base_id;
    }

    /**
     * @param base_id the base_id to set
     */
    public void setBase_id(Integer base_id) {
        this.base_id = base_id;
    }

    /**
     * @return the base_symbol
     */
    public String getBase_symbol() {
        return base_symbol;
    }

    /**
     * @param base_symbol the base_symbol to set
     */
    public void setBase_symbol(String base_symbol) {
        this.base_symbol = base_symbol;
    }

    /**
     * @return the quote_id
     */
    public Integer getQuote_id() {
        return quote_id;
    }

    /**
     * @param quote_id the quote_id to set
     */
    public void setQuote_id(Integer quote_id) {
        this.quote_id = quote_id;
    }

    /**
     * @return the quote_symbol
     */
    public String getQuote_symbol() {
        return quote_symbol;
    }

    /**
     * @param quote_symbol the quote_symbol to set
     */
    public void setQuote_symbol(String quote_symbol) {
        this.quote_symbol = quote_symbol;
    }

    /**
     * @return the sale_or_buy
     */
    public String getSale_or_buy() {
        return sale_or_buy;
    }

    /**
     * @param sale_or_buy the sale_or_buy to set
     */
    public void setSale_or_buy(String sale_or_buy) {
        this.sale_or_buy = sale_or_buy;
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
     * @return the base_num
     */
    public String getBase_num() {
        return base_num;
    }

    /**
     * @param base_num the base_num to set
     */
    public void setBase_num(String base_num) {
        this.base_num = base_num;
    }

    /**
     * @return the quote_num
     */
    public String getQuote_num() {
        return quote_num;
    }

    /**
     * @param quote_num the quote_num to set
     */
    public void setQuote_num(String quote_num) {
        this.quote_num = quote_num;
    }
    
    /**
     * @return the trade_date
     */
    public String getTrade_date() {
        return trade_date;
    }

    /**
     * @param trade_date the trade_date to set
     */
    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }
    
    @Override
    public String toString(){
         return "Trade_Data [id:" + getId()
                + ",baseCoin_id:" + this.getBase_id()
                + ",baseCoin_symbol:" + this.getBase_symbol()
                + ",quoteCoin_id:" + this.getQuote_id()
                + ",quoteCoin_symbol:" + this.getQuote_symbol()
                + ",sale_or_buy:" + this.getSale_or_buy()
                + ",price:" + this.getPrice()
                + ",base_num:" + this.getBase_num()
                + ",quote_num:" + this.getQuote_num()
                + ",trade_date:" + this.getTrade_date()
                + "]";
    }


}
