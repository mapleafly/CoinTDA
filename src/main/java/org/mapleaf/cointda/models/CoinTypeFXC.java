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
package org.mapleaf.cointda.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author xuelf
 */
public class CoinTypeFXC {

    private final SimpleStringProperty id;
    private final SimpleBooleanProperty select;
    private final SimpleStringProperty name;
    private final SimpleStringProperty symbol;
    private final SimpleStringProperty rank;
    private final SimpleStringProperty price;
    private final SimpleStringProperty date;

    public CoinTypeFXC() {
        this(null, false, null, null, null, "0", null);
    }

    public CoinTypeFXC(String id, boolean select, String name, String symbol, String rank, String price, String date) {
        this.id = new SimpleStringProperty(id);
        this.select = new SimpleBooleanProperty(select);
        this.name = new SimpleStringProperty(name);
        this.symbol = new SimpleStringProperty(symbol);
        this.rank = new SimpleStringProperty(rank);
        this.price = new SimpleStringProperty(price);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * @return the name
     */
    public boolean getSelect() {
        return select.get();
    }

    public SimpleBooleanProperty selectProperty() {
        return select;
    }

    /**
     * @param select
     */
    public void setSelect(boolean select) {
        this.select.set(select);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    /**
     * @return the rank
     */
    public String getRank() {
        return rank.get();
    }

    public SimpleStringProperty rankProperty() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(String rank) {
        this.rank.set(rank);
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

    @Override
    public String toString() {
        return "CoinType [id:" + getId()
                + ",select:" + getSelect()
                + ",name:" + getName()
                + ",symbol:" + getSymbol()
                + ",rank:" + getRank()
                + ",price:" + this.getPrice()
                + ",last updated:" + this.getDate()
                + "]";
    }
}
