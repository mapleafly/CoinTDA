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
package org.lifxue.cointda.bean;

import java.math.BigDecimal;

/**
 *
 * @author xuelf
 */
public class TradeDataBean {
    
    //自增id
    private Integer id;
    //coin id
    private Integer coin_id;
    // coin 简称
    private String coin_symbol;
    //买或者卖
    private String sale_or_buy;
    //买入或卖出价格
    private BigDecimal price;
    //买入或卖出数量
    private BigDecimal num;
    //买入或卖出总价
    private BigDecimal total_price;
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
     * @return the coin_id
     */
    public Integer getCoin_id() {
        return coin_id;
    }

    /**
     * @param coin_id the coin_id to set
     */
    public void setCoin_id(Integer coin_id) {
        this.coin_id = coin_id;
    }

    /**
     * @return the coin_symbol
     */
    public String getCoin_symbol() {
        return coin_symbol;
    }

    /**
     * @param coin_symbol the coin_symbol to set
     */
    public void setCoin_symbol(String coin_symbol) {
        this.coin_symbol = coin_symbol;
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
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the num
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * @return the total_price
     */
    public BigDecimal getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
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
                + ",coin_id:" + this.getCoin_id()
                + ",coin_symbol:" + this.getCoin_symbol()
                + ",sale_or_buy:" + this.getSale_or_buy()
                + ",price:" + this.getPrice()
                + ",num:" + this.getNum()
                + ",total_price:" + this.getTotal_price()
                + ",trade_date:" + this.getTrade_date()
                + "]";
    }
}
