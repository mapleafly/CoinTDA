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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cointda.bean.CoinMarketCapIdBean;
import org.cointda.pool.DBHelper;

import java.util.List;

/**
 * @author xuelf
 */
public class CoinMarketCapDao {

    private static final Logger logger = LogManager.getLogger(CoinMarketCapDao.class.getName());

    /**
     * @param bean 1
     * @Description: 修改一条数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:00
     */
    public static int update(CoinMarketCapIdBean bean) {
        String sql =
            "update TAB_CoinMarketCap_id_map set "
                + "name=?,symbol=?,slug=?,is_active=?,rank=?,"
                + "first_historical_data=?,last_historical_data=?,"
                + "platform_id=?,token_address=?"
                + " where id=?";

        Object[] param = new Object[10];
        param[0] = bean.getName();
        param[1] = bean.getSymbol();
        param[2] = bean.getSlug();
        param[3] = bean.getIs_active();
        param[4] = bean.getRank();
        param[5] = bean.getFirst_historical_data();
        param[6] = bean.getLast_historical_data();
        param[7] = bean.getPlatform_id();
        param[8] = bean.getToken_address();
        param[9] = bean.getId();

        return DBHelper.update(sql, param);
    }

    /**
     * @param bean 1
     * @Description: 修改一条数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:00
     */
    public static int insert(CoinMarketCapIdBean bean) {
        String sql =
            "insert into TAB_CoinMarketCap_id_map"
                + "(id,name,symbol,slug,is_active,rank,"
                + "first_historical_data,last_historical_data,"
                + "platform_id,token_address)"
                + " values (?,?,?,?,?,?,?,?,?,?)";

        return DBHelper.update(sql, getInsertParam(bean));
    }

    /**
     * @param list 1
     * @Description: 批量插入数据
     * @return: int[]
     * @author: mapleaf
     * @date: 2020/6/23 17:01
     */
    public static int[] batchInsert(List<CoinMarketCapIdBean> list) {
        String sql =
            "insert into TAB_CoinMarketCap_id_map"
                + "(id,name,symbol,slug,is_active,rank,"
                + "first_historical_data,last_historical_data,"
                + "platform_id,token_address)"
                + " values (?,?,?,?,?,?,?,?,?,?)";

        Object[][] params = new Object[list.size()][];
        // 组织params
        for (int i = 0; i < list.size(); i++) {
            CoinMarketCapIdBean bean = list.get(i);
            params[i] = getInsertParam(bean);
        }

        return DBHelper.batch(sql, params);
    }

    /**
     * @param bean 1
     * @Description: 删除一条coinMarket数据
     * @return: int
     * @author: mapleaf
     * @date: 2020/6/23 17:14
     */
    public static int delete(CoinMarketCapIdBean bean) {
        String sql = "delete from TAB_CoinMarketCap_id_map" + " where id=?";
        return DBHelper.update(sql, bean.getId());
    }

    /**
     * @Description: 删除全部数据
     * @return: boolean
     * @author: mapleaf
     * @date: 2020/6/23 17:01
     */
    public static boolean truncate() {
        String sql = "TRUNCATE TABLE TAB_CoinMarketCap_id_map";
        return DBHelper.update(sql) == 0;
    }

    /**
     * @param id 1
     * @Description: 查询一条数据
     * @return: org.cointda.bean.CoinMarketCapIdBean
     * @author: mapleaf
     * @date: 2020/6/23 17:02
     */
    public static CoinMarketCapIdBean queryBean(int id) {
        String sql = "select * from TAB_CoinMarketCap_id_map where id=?";
        return DBHelper.queryBean(CoinMarketCapIdBean.class, sql, id);
    }

    /**
     * @param bean 1
     * @Description: 设置插入数据时的参数
     * @return: java.lang.Object[]
     * @author: mapleaf
     * @date: 2020/6/23 17:11
     */
    private static Object[] getInsertParam(CoinMarketCapIdBean bean) {
        Object[] param = new Object[10];
        param[0] = bean.getId();
        param[1] = bean.getName();
        param[2] = bean.getSymbol();
        param[3] = bean.getSlug();
        param[4] = bean.getIs_active();
        param[5] = bean.getRank();
        param[6] = bean.getFirst_historical_data();
        param[7] = bean.getLast_historical_data();
        param[8] = bean.getPlatform_id();
        param[9] = bean.getToken_address();

        return param;
    }

    /**
     * @Description: 查询全部数据
     * @return: java.util.List<org.cointda.bean.CoinMarketCapIdBean>
     * @author: mapleaf
     * @date: 2020/6/23 17:15
     */
    public static List<CoinMarketCapIdBean> queryAll() {
        String sql = "select * from TAB_CoinMarketCap_id_map";
        return DBHelper.queryList(CoinMarketCapIdBean.class, sql);
    }

    //  public static void main(String[] args) {
    //    CoinIDMapCollector c = new CoinIDMapCollector();
    //    List<CoinMarketCapIdBean> list = c.getCoinMarketCapIds();
    //    logger.info(list.size());
    //    CoinMarketCapDao dao = new CoinMarketCapDao();
    //    CoinMarketCapDao.truncate();
    //    logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
    //    logger.info(CoinMarketCapDao.batchInsert(list).length);
    //    // logger.info(CoinMarketCapDao.insert(list.get(0)));
    //    logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
    //    CoinMarketCapIdBean coin = CoinMarketCapDao.queryBean(2781);
    //    logger.info(coin);
    //    CoinMarketCapDao.delete(coin);
    //    logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
    //  }
}
