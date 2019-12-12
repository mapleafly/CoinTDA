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

/**
 *
 * @author xuelf
 */
public class CoinMarketCapIdBean {

    private Integer cId;
    private String name;
    private String symbol;
    private String slug;
    private Integer is_active;
    private Integer rank;
    private String first_historical_data;
    private String last_historical_data;
    private Integer pId;
    private String token_address;

    public CoinMarketCapIdBean() {

    }

    /**
     * @return the cId
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * @param cId the cId to set
     */
    public void setcId(Integer cId) {
        this.cId = cId;
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
     * @return the is_active
     */
    public Integer getIs_active() {
        return is_active;
    }

    /**
     * @param is_active the is_active to set
     */
    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    /**
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * @return the first_historical_data
     */
    public String getFirst_historical_data() {
        return first_historical_data;
    }

    /**
     * @param first_historical_data the first_historical_data to set
     */
    public void setFirst_historical_data(String first_historical_data) {
        this.first_historical_data = first_historical_data;
    }

    /**
     * @return the last_historical_data
     */
    public String getLast_historical_data() {
        return last_historical_data;
    }

    /**
     * @param last_historical_data the last_historical_data to set
     */
    public void setLast_historical_data(String last_historical_data) {
        this.last_historical_data = last_historical_data;
    }

    /**
     * @return the pId
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * @param pId the pId to set
     */
    public void setpId(Integer pId) {
        this.pId = pId;
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
        return "CoinMarketCapId [id:" + getcId()
                + ",name:" + getName()
                + ",symbol:" + getSymbol()
                + ",slug:" + getSlug()
                + ",is_active:" + getIs_active()
                + ",rank:" + getRank()
                + ",first_historical_data:" + getFirst_historical_data()
                + ",last_historical_data:" + getLast_historical_data()
                + ",platform_id:" + getpId()
                + ",token_address:" + getToken_address()
                + "]";
    }

}
