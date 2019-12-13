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

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.bean.CoinMarketCapListingBean;
import org.lifxue.cointda.models.CoinTypeFXC;
import org.lifxue.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class CoinTypeDao {

    private static final Logger logger = LogManager.getLogger(
            CoinTypeDao.class.getName());

    public CoinTypeDao() {

    }

    /**
     * 查询全部数据
     *
     * @return 返回 list
     */
    public static List<CoinTypeFXC> queryAll() {
        String sql = "select * from tab_CoinMarketCap_listings order by cmc_rank";
        List<CoinMarketCapListingBean> list = DBHelper.queryList(
                CoinMarketCapListingBean.class, sql);
        List<CoinTypeFXC> ctList = new ArrayList<>();
        list.stream().map((bean) -> {
            CoinTypeFXC coin = new CoinTypeFXC();
            coin.setId(bean.getId().toString());
            coin.setName(bean.getName());
            coin.setSymbol(bean.getSymbol());
            coin.setRank(bean.getCmc_rank().toString());
            coin.setPrice(bean.getPrice() == null ? "0" : bean.getPrice().toString());
            coin.setDate(bean.getLastUpdated());
            return coin;
        }).forEachOrdered((coin) -> {
            ctList.add(coin);
        });
        return ctList;
    }

    /**
     * 查询当前被选中可使用的coin
     * @return 
     */
    public static List<CoinTypeFXC> queryCurAll() {
        String sql = "select * from tab_CoinMarketCap_listings where id"
                + " in (select id from tab_curuse_coin) order by cmc_rank";
        List<CoinMarketCapListingBean> list = DBHelper.queryList(
                CoinMarketCapListingBean.class, sql);
        List<CoinTypeFXC> ctList = new ArrayList<>();
        list.stream().map((bean) -> {
            CoinTypeFXC coin = new CoinTypeFXC();
            coin.setId(bean.getId().toString());
            coin.setName(bean.getName());
            coin.setSymbol(bean.getSymbol());
            coin.setRank(bean.getCmc_rank().toString());
            coin.setPrice(bean.getPrice() == null ? "0" : bean.getPrice().toString());
            coin.setDate(bean.getLastUpdated());
            return coin;
        }).forEachOrdered((coin) -> {
            ctList.add(coin);
        });
        return ctList;
    }

    public static void main(String[] args) {
        CoinTypeDao.queryAll().forEach(action -> logger.info("CoinType::" + action));
    }
}
