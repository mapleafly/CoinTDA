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

import org.cointda.bean.CoinMarketCapListingBean;
import org.cointda.pool.DBHelper;

import java.util.List;

/**
 * @author xuelf
 */
public class CoinListingDao {
    /**
     * @param bean 1
     * @Description: 修改一条数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:51
     */
    public static int update(CoinMarketCapListingBean bean) {
        String sql =
            "update tab_CoinMarketCap_listings set "
                + "name=?,symbol=?,slug=?,cmc_rank=?,date_added=?,"
                + "platform_id=?,token_address=?,numMarketPairs=?,maxSupply=?,"
                + "circulatingSupply=?,totalSupply=?,price=?,volume_24h=?,"
                + "percent_change_1h=?,percent_change_24h=?,"
                + "percent_change_7d=?,marketCap=?,lastUpdated=?"
                + " where id=?";

        Object[] param = new Object[19];
        param[0] = bean.getName();
        param[1] = bean.getSymbol();
        param[2] = bean.getSlug();
        param[3] = bean.getCmc_rank();
        param[4] = bean.getDate_added();
        param[5] = bean.getPlatform_id();
        param[6] = bean.getToken_address();
        param[7] = bean.getNumMarketPairs();
        param[8] = bean.getMaxSupply();
        param[9] = bean.getCirculatingSupply();
        param[10] = bean.getTotalSupply();
        param[11] = bean.getPrice();
        param[12] = bean.getVolume_24h();
        param[13] = bean.getPercent_change_1h();
        param[14] = bean.getPercent_change_24h();
        param[15] = bean.getPercent_change_7d();
        param[16] = bean.getMarketCap();
        param[17] = bean.getLastUpdated();
        param[18] = bean.getId();

        return DBHelper.update(sql, param);
    }

    /**
     * @param bean 1
     * @Description: 插入一条数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:51
     */
    public static int insert(CoinMarketCapListingBean bean) {
        String sql =
            "insert into tab_CoinMarketCap_listings"
                + "(id,name,symbol,slug,cmc_rank,date_added,platform_id,"
                + "token_address,numMarketPairs,maxSupply,circulatingSupply,"
                + "totalSupply,price,volume_24h,percent_change_1h,"
                + "percent_change_24h,percent_change_7d,marketCap,lastUpdated)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return DBHelper.update(sql, getInsertParam(bean));
    }

    private static Object[] getInsertParam(CoinMarketCapListingBean bean) {
        Object[] param = new Object[19];
        param[0] = bean.getId();
        param[1] = bean.getName();
        param[2] = bean.getSymbol();
        param[3] = bean.getSlug();
        param[4] = bean.getCmc_rank();
        param[5] = bean.getDate_added();
        param[6] = bean.getPlatform_id();
        param[7] = bean.getToken_address();
        param[8] = bean.getNumMarketPairs();
        param[9] = bean.getMaxSupply();
        param[10] = bean.getCirculatingSupply();
        param[11] = bean.getTotalSupply();
        param[12] = bean.getPrice();
        param[13] = bean.getVolume_24h();
        param[14] = bean.getPercent_change_1h();
        param[15] = bean.getPercent_change_24h();
        param[16] = bean.getPercent_change_7d();
        param[17] = bean.getMarketCap();
        param[18] = bean.getLastUpdated();

        return param;
    }

    /**
     * @param list 1
     * @Description: 批量插入数据
     * @return: int[]
     * @author: mapleaf
     * @date: 2020/6/23 17:52
     */
    public static int[] batchInsert(List<CoinMarketCapListingBean> list) {
        String sql =
            "insert into tab_CoinMarketCap_listings"
                + "(id,name,symbol,slug,cmc_rank,date_added,platform_id,"
                + "token_address,numMarketPairs,maxSupply,circulatingSupply,"
                + "totalSupply,price,volume_24h,percent_change_1h,"
                + "percent_change_24h,percent_change_7d,marketCap,lastUpdated)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[][] params = new Object[list.size()][];
        // 组织params
        for (int i = 0; i < list.size(); i++) {
            CoinMarketCapListingBean bean = list.get(i);
            params[i] = getInsertParam(bean);
        }
        return DBHelper.batch(sql, params);
    }

    /**
     * @param bean 1
     * @Description: 删除一条数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:52
     */
    public static int delete(CoinMarketCapListingBean bean) {
        String sql = "delete from tab_CoinMarketCap_listings" + " where id=?";
        return DBHelper.update(sql, bean.getId());
    }

    /**
     * @Description: 删除全部数据
     * @return: boolean
     * @author: mapleaf
     * @date: 2020/6/23 17:52
     */
    public static boolean truncate() {
        String sql = "TRUNCATE TABLE tab_CoinMarketCap_listings";
        return DBHelper.update(sql) == 0;
    }

    /**
     * @param id 1
     * @Description: 查询一条数据
     * @return: org.cointda.bean.CoinMarketCapListingBean
     * @author: mapleaf
     * @date: 2020/6/23 17:53
     */
    public static CoinMarketCapListingBean queryBean(int id) {
        String sql = "select * from tab_CoinMarketCap_listings where id=?";
        return DBHelper.queryBean(CoinMarketCapListingBean.class, sql, id);
    }

    /**
     * @Description: 查询全部数据
     * @return: java.util.List<org.cointda.bean.CoinMarketCapListingBean>
     * @author: mapleaf
     * @date: 2020/6/23 17:53
     */
    public static List<CoinMarketCapListingBean> queryAll() {
        String sql = "select * from tab_CoinMarketCap_listings";
        return DBHelper.queryList(CoinMarketCapListingBean.class, sql);
    }

    public static void main(String[] args) {
        //        CoinListingCollector c = new CoinListingCollector();
        //        List<CoinMarketCapListingBean> list = c.getCoinMarketListing();
        //        logger.info(list.size());
        //        //CoinListingDao dao = new CoinListingDao();
        //        CoinListingDao.truncate();
        //        logger.info("QueryAll().size == " + CoinListingDao.queryAll().size());
        //        //logger.info(CoinListingDao.batchInsert(list).length);
        //        logger.info(CoinListingDao.insert(list.get(0)));
        //        logger.info("QueryAll().size == " + CoinListingDao.queryAll().size());
        //        CoinMarketCapListingBean coin = CoinListingDao.queryBean(1);
        //        logger.info(coin);
        //        CoinListingDao.delete(coin);
        //        logger.info("QueryAll().size == " + CoinListingDao.queryAll().size());

    }
}
