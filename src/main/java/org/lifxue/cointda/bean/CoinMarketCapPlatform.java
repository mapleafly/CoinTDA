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
public class CoinMarketCapPlatform {

    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private String token_address;

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

    public String toString() {
        return "CoinMarketCapPlatform[id:" + id + 
                ",name:" + name + 
                ",symbol:" + symbol + 
                ",slug:" + slug + 
                ",token_address:" + token_address + 
                "]";
    }

}
