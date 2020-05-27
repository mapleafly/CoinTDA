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
package org.mapleaf.cointda.bean.property;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author xuelf
 */
public class TradeDataFXC {

    //自增键
    private SimpleIntegerProperty id;
    //coin id
    private SimpleIntegerProperty coinId;
    //symbol交易对
    private final SimpleStringProperty symbolPairs;
    //买==b, 卖==s
    private final SimpleStringProperty saleOrBuy;
    private final SimpleStringProperty price;
    //base数量
    private final SimpleStringProperty baseNum;
    //quote数量
    private final SimpleStringProperty quoteNum;
    private final SimpleStringProperty date;

    public TradeDataFXC() {
        this(-1, -1, null, null, "-1", "-1", "-1", null);
    }

    public TradeDataFXC(Integer id, Integer coinId, String symbolPairs,
            String saleOrBuy, String price, String baseNum, String quoteNum,
            String date) {
        this.id = new SimpleIntegerProperty(id);
        this.coinId = new SimpleIntegerProperty(coinId);
        this.symbolPairs = new SimpleStringProperty(symbolPairs);
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

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * @return the coinId
     */
    public Integer getCoinId() {
        return coinId.get();
    }

    public SimpleIntegerProperty coinIdProperty() {
        return coinId;
    }

    /**
     * @param coinId the coinId to set
     */
    public void setCoinId(Integer coinId) {
        this.coinId.set(coinId);
    }

    /**
     * @return the coinSymbol
     */
    public String getSymbolPairs() {
        return symbolPairs.get();
    }

    public SimpleStringProperty symbolPairsProperty() {
        return symbolPairs;
    }

    /**
     * @param symbolPairs
     */
    public void setSymbolPairs(String symbolPairs) {
        this.symbolPairs.set(symbolPairs);
    }

    /**
     * @return the saleOrBuy
     */
    public String getSaleOrBuy() {
        return saleOrBuy.get();
    }

    public SimpleStringProperty saleOrBuyProperty() {
        return saleOrBuy;
    }

    /**
     * @param saleOrBuy the saleOrBuy to set
     */
    public void setSaleOrBuy(String saleOrBuy) {
        this.saleOrBuy.set(saleOrBuy);
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price.set(price);
    }

    /**
     * @return the num
     */
    public String getBaseNum() {
        return baseNum.get();
    }

    public SimpleStringProperty baseNumProperty() {
        return baseNum;
    }

    /**
     * @param baseNum
     */
    public void setBaseNum(String baseNum) {
        this.baseNum.set(baseNum);
    }

    /**
     * @return the totalPrice
     */
    public String getQuoteNum() {
        return quoteNum.get();
    }

    public SimpleStringProperty quoteNumProperty() {
        return quoteNum;
    }

    /**
     * @param quoteNum
     */
    public void setQuoteNum(String quoteNum) {
        this.quoteNum.set(quoteNum);
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date.set(date);
    }
}
