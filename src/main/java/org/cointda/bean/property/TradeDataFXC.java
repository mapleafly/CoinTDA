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
package org.cointda.bean.property;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author xuelf
 */
public class TradeDataFXC {

    // symbol交易对
    private final SimpleStringProperty symbolPairs;
    //当前涨跌幅
    private final SimpleStringProperty chg;
    // 买==b, 卖==s
    private final SimpleStringProperty saleOrBuy;
    private final SimpleStringProperty price;
    // base数量
    private final SimpleStringProperty baseNum;
    // quote数量
    private final SimpleStringProperty quoteNum;
    private final SimpleStringProperty date;
    // 自增键
    private SimpleIntegerProperty id;
    // coin id
    private SimpleIntegerProperty coinId;

    public TradeDataFXC() {
        this(-1, -1, null, null, null, "-1", "-1", "-1", null);
    }

    public TradeDataFXC(
        Integer id,
        Integer coinId,
        String symbolPairs,
        String chg,
        String saleOrBuy,
        String price,
        String baseNum,
        String quoteNum,
        String date) {
        this.id = new SimpleIntegerProperty(id);
        this.coinId = new SimpleIntegerProperty(coinId);
        this.symbolPairs = new SimpleStringProperty(symbolPairs);
        this.chg = new SimpleStringProperty(chg);
        this.saleOrBuy = new SimpleStringProperty(saleOrBuy);
        this.price = new SimpleStringProperty(price);
        this.baseNum = new SimpleStringProperty(baseNum);
        this.quoteNum = new SimpleStringProperty(quoteNum);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * @return the coinId
     */
    public Integer getCoinId() {
        return coinId.get();
    }

    /**
     * @param coinId the coinId to set
     */
    public void setCoinId(Integer coinId) {
        this.coinId.set(coinId);
    }

    public SimpleIntegerProperty coinIdProperty() {
        return coinId;
    }

    /**
     * @return the coinSymbol
     */
    public String getSymbolPairs() {
        return symbolPairs.get();
    }

    /**
     * @param symbolPairs 1
     * @Description: set symbolPairs
     * @return: void
     * @author: mapleaf
     * @date: 2020/6/23 17:34
     */
    public void setSymbolPairs(String symbolPairs) {
        this.symbolPairs.set(symbolPairs);
    }

    /**
     * @Description: set symbolPairs
     * @return: javafx.beans.property.SimpleStringProperty
     * @author: mapleaf
     * @date: 2020/6/23 18:53
     */
    public SimpleStringProperty symbolPairsProperty() {
        return symbolPairs;
    }

    public String getChg() {
        return chg.get();
    }

    public void setChg(String chg) {
        this.chg.set(chg);
    }

    public SimpleStringProperty chgProperty() {
        return chg;
    }

    /**
     * @return the saleOrBuy
     */
    public String getSaleOrBuy() {
        return saleOrBuy.get();
    }

    /**
     * @param saleOrBuy the saleOrBuy to set
     */
    public void setSaleOrBuy(String saleOrBuy) {
        this.saleOrBuy.set(saleOrBuy);
    }

    public SimpleStringProperty saleOrBuyProperty() {
        return saleOrBuy;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price.get();
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price.set(price);
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    /**
     * @return the num
     */
    public String getBaseNum() {
        return baseNum.get();
    }

    /**
     * @param baseNum 1
     * @Description: set baseNum
     * @return: void
     * @author: mapleaf
     * @date: 2020/6/23 17:36
     */
    public void setBaseNum(String baseNum) {
        this.baseNum.set(baseNum);
    }

    /**
     * @Description: set baseNum
     * @return: javafx.beans.property.SimpleStringProperty
     * @author: mapleaf
     * @date: 2020/6/23 18:54
     */
    public SimpleStringProperty baseNumProperty() {
        return baseNum;
    }

    /**
     * @return the totalPrice
     */
    public String getQuoteNum() {
        return quoteNum.get();
    }

    /**
     * @param quoteNum 1
     * @Description: set quoteNum
     * @return: void
     * @author: mapleaf
     * @date: 2020/6/23 17:36
     */
    public void setQuoteNum(String quoteNum) {
        this.quoteNum.set(quoteNum);
    }

    public SimpleStringProperty quoteNumProperty() {
        return quoteNum;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }
}
