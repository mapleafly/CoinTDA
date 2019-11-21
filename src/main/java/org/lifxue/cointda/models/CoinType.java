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
import javafx.beans.property.StringProperty;

/**
 *
 * @author xuelf
 */
public class CoinType {

    private final SimpleStringProperty shortName;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty cnName;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty date;

    public CoinType() {
        this(null, null, null, 0, null);
    }

    public CoinType(String shortName, String fullName, String cnName) {
        this(shortName, fullName, cnName, 0, null);
    }
    
    public CoinType(String shortName, String fullName, String cnName, double price, String date) {
        this.shortName = new SimpleStringProperty(shortName);
        this.fullName = new SimpleStringProperty(fullName);
        this.cnName = new SimpleStringProperty(cnName);
        this.price = new SimpleDoubleProperty(price);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName.get();
    }

    public SimpleStringProperty shortNameProperty() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * @return the cnName
     */
    public String getCnName() {
        return cnName.get();
    }

    public SimpleStringProperty cnNameProperty() {
        return cnName;
    }

    /**
     * @param cnName the cnName to set
     */
    public void setCnName(String cnName) {
        this.cnName.set(cnName);
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
