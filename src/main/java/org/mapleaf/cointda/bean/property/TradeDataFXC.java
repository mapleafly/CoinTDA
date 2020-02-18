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
    private final SimpleStringProperty coinSymbol;
    //买==b, 卖==s
    private final SimpleStringProperty saleOrBuy;
    private final SimpleStringProperty price;
    //数量
    private final SimpleStringProperty num;
    //总价
    private final SimpleStringProperty totalPrice;
    private final SimpleStringProperty date;

    public TradeDataFXC() {
        this(-1, -1, null, null, "-1", "-1", "-1", null);
    }

    public TradeDataFXC(Integer id, Integer coinId, String coinSymbol,
            String saleOrBuy, String price, String num, String totalPrice,
            String date) {
        this.id = new SimpleIntegerProperty(id);
        this.coinId = new SimpleIntegerProperty(coinId);
        this.coinSymbol = new SimpleStringProperty(coinSymbol);
        this.saleOrBuy = new SimpleStringProperty(saleOrBuy);
        this.price = new SimpleStringProperty(price);
        this.num = new SimpleStringProperty(num);
        this.totalPrice = new SimpleStringProperty(totalPrice);
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
    public String getCoinSymbol() {
        return coinSymbol.get();
    }

    public SimpleStringProperty coinSymbolProperty() {
        return coinSymbol;
    }

    /**
     * @param coinSymbol the coinSymbol to set
     */
    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol.set(coinSymbol);
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
    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(String num) {
        this.num.set(num);
    }

    /**
     * @return the totalPrice
     */
    public String getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleStringProperty totalPriceProperty() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
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
