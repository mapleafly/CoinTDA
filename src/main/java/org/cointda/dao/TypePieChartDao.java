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
package org.cointda.dao;

import org.cointda.bean.CoinQuotesLatestBean;
import org.cointda.bean.TradeDataBean;
import org.cointda.pool.DBHelper;

import java.util.List;

/**
 * @author xuelf
 */
public class TypePieChartDao {
    public static List<TradeDataBean> queryAllTradeData() {
        String sql = "select * from tab_tradeinfo";
        return DBHelper.queryList(TradeDataBean.class, sql);
    }

    /**
     * @Description: 找出有交易记录的coin的现价
     * @return: java.util.List<org.cointda.bean.CoinQuotesLatestBean>
     * @author: mapleaf
     * @date: 2020/6/23 18:23
     */
    public static List<CoinQuotesLatestBean> queryByTradeData() {
        String sql =
            "select * from tab_quotesLatest where id in"
                + " (select base_id from tab_tradeinfo group by base_id ) "
                + "order by cmc_rank";
        return DBHelper.queryList(CoinQuotesLatestBean.class, sql);
    }
}
