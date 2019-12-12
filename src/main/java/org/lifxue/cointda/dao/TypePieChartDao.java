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
package org.lifxue.cointda.dao;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.bean.CoinMarketCapListingBean;
import org.lifxue.cointda.bean.TradeDataBean;
import org.lifxue.cointda.pool.DBUtilsHelper;

/**
 *
 * @author xuelf
 */
public class TypePieChartDao {

    private static final Logger logger = LogManager.getLogger(
            TypePieChartDao.class.getName());

    public static List<TradeDataBean> queryAllTradeData() {
        String sql = "select * from tab_trade_data";
        return DBUtilsHelper.queryList(TradeDataBean.class, sql);
    }
    
    public static List<CoinMarketCapListingBean> queryByTradeData(){
          String sql = "select * from tab_CoinMarketCap_listings where id in"
                  + " (select coin_id from tab_trade_data group by coin_id ) "
                  + "order by cmc_rank";
         return DBUtilsHelper.queryList(CoinMarketCapListingBean.class, sql);
    }

}
