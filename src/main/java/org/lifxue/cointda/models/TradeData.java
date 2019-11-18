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
package org.lifxue.cointda.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author xuelf
 */
public class TradeData {

    //键
    private int id;
    private final SimpleStringProperty coinType;
    //买==b, 卖==s
    private final SimpleStringProperty saleOrBuy;
    private final SimpleDoubleProperty price;
    //数量
    private final SimpleDoubleProperty num;
    //总价
    private final SimpleDoubleProperty totalPrice;
    private final SimpleStringProperty date;

    public TradeData() {
        this(null, null, -1, -1, -1, null);
    }

    public TradeData(String coinType, String saleOrBuy, double price, double num, double totalPrice, String date) {
        this.coinType = new SimpleStringProperty(coinType);
        this.saleOrBuy = new SimpleStringProperty(saleOrBuy);
        this.price = new SimpleDoubleProperty(price);
        this.num = new SimpleDoubleProperty(num);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the coinType
     */
    public String getCoinType() {
        return coinType.get();
    }

    public SimpleStringProperty coinTypeProperty() {
        return coinType;
    }

    /**
     * @param coinType the coinType to set
     */
    public void setCoinType(String coinType) {
        this.coinType.set(coinType);
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
    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * @return the num
     */
    public double getNum() {
        return num.get();
    }

    public SimpleDoubleProperty numProperty() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(double num) {
        this.num.set(num);
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
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
