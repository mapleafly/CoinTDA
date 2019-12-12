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

import java.util.ArrayList;

/**
 *
 * @author xuelf
 */
public class CoinMarketCapIdMap {
    private CoinMarketCapStatus status;
    private ArrayList<CoinMarketCapIdBean> data;

    /**
     * @return the status
     */
    public CoinMarketCapStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(CoinMarketCapStatus status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public ArrayList<CoinMarketCapIdBean> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(ArrayList<CoinMarketCapIdBean> data) {
        this.data = data;
    }
    
}
