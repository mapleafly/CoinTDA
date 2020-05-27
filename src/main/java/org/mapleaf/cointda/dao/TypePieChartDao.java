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
package org.mapleaf.cointda.dao;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinQuotesLatestBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class TypePieChartDao {

    private static final Logger logger = LogManager.getLogger(
            TypePieChartDao.class.getName());

    public static List<TradeDataBean> queryAllTradeData() {
        String sql = "select * from tab_tradeinfo";
        return DBHelper.queryList(TradeDataBean.class, sql);
    }
    
    /**
     * 找出有交易记录的coin的现价
     * @return 
     */
    public static List<CoinQuotesLatestBean> queryByTradeData(){
          String sql = "select * from tab_quotesLatest where id in"
                  + " (select base_id from tab_tradeinfo group by base_id ) "
                  + "order by cmc_rank";
         return DBHelper.queryList(CoinQuotesLatestBean.class, sql);
    }

}
