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
public class CurUsedCoinBean {

    private Integer id;
    private String symbol;
    private Integer cmc_rank;
    private String lastUpdated;

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
     * @return the cmc_rank
     */
    public Integer getCmc_rank() {
        return cmc_rank;
    }

    /**
     * @param cmc_rank the cmc_rank to set
     */
    public void setCmc_rank(Integer cmc_rank) {
        this.cmc_rank = cmc_rank;
    }

    /**
     * @return the lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "CurUsedCoinBean [id:" + getId()
                + ",symbol:" + getSymbol()
                + ",cmc_rank:" + this.getCmc_rank()
                + ",last_updated:" + this.getLastUpdated()
                + "]";
    }

}
