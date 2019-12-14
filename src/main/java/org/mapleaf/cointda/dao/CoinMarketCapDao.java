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
import org.mapleaf.cointda.bean.CoinMarketCapIdBean;
import org.mapleaf.cointda.crypto.CoinIDMapCollector;
import org.mapleaf.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class CoinMarketCapDao {

    private static final Logger logger = LogManager.getLogger(CoinMarketCapDao.class.getName());

    /**
     * 修改一条数据
     * @param id
     * @return 
     */
    public static int update(CoinMarketCapIdBean id) {
        String sql = "update TAB_CoinMarketCap_id_map set "
                + "name=?,symbol=?,slug=?,is_active=?,rank=?,"
                + "first_historical_data=?,last_historical_data=?,"
                + "p_id=?,token_address=?"
                + " where c_id=?";

        Object[] param = new Object[10];
        param[0] = id.getName();
        param[1] = id.getSymbol();
        param[2] = id.getSlug();
        param[3] = id.getIs_active();
        param[4] = id.getRank();
        param[5] = id.getFirst_historical_data();
        param[6] = id.getLast_historical_data();
        param[7] = id.getpId();
        param[8] = id.getToken_address();
        param[9] = id.getcId();

        return DBHelper.update(sql, param);
    }

    /**
     * 插入一条数据
     *
     * @param id
     * @return
     */
    public static int insert(CoinMarketCapIdBean id) {
        String sql = "insert into TAB_CoinMarketCap_id_map"
                + "(c_id,name,symbol,slug,is_active,rank,"
                + "first_historical_data,last_historical_data,p_id,token_address)"
                + " values (?,?,?,?,?,?,?,?,?,?)";

        Object[] param = new Object[10];
        param[0] = id.getcId();
        param[1] = id.getName();
        param[2] = id.getSymbol();
        param[3] = id.getSlug();
        param[4] = id.getIs_active();
        param[5] = id.getRank();
        param[6] = id.getFirst_historical_data();
        param[7] = id.getLast_historical_data();
        param[8] = id.getpId();
        param[9] = id.getToken_address();

        return DBHelper.update(sql, param);
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    public static int[] batchInsert(List<CoinMarketCapIdBean> list) {
        String sql = "insert into TAB_CoinMarketCap_id_map"
                + "(c_id,name,symbol,slug,is_active,rank,"
                + "first_historical_data,last_historical_data,p_id,token_address)"
                + " values (?,?,?,?,?,?,?,?,?,?)";

        Object[][] params = new Object[list.size()][];
        //组织params
        for (int i = 0; i < list.size(); i++) {
            CoinMarketCapIdBean id = list.get(i);
            Object[] param = new Object[10];
            param[0] = id.getcId();
            param[1] = id.getName();
            param[2] = id.getSymbol();
            param[3] = id.getSlug();
            param[4] = id.getIs_active();
            param[5] = id.getRank();
            param[6] = id.getFirst_historical_data();
            param[7] = id.getLast_historical_data();
            param[8] = id.getpId();
            param[9] = id.getToken_address();

            params[i] = param;
        }

        return DBHelper.batch(sql, params);
    }

    public static int delete(CoinMarketCapIdBean id){
        String sql = "delete from TAB_CoinMarketCap_id_map"
                + " where c_id=?";
        return DBHelper.update(sql, id.getcId());
    }
    /**
     * 删除全部数据
     *
     * @return
     */
    public static boolean truncate() {
        String sql = "TRUNCATE TABLE TAB_CoinMarketCap_id_map";
        return DBHelper.update(sql) == 0;
    }

    /**
     * 查询一条数据
     *
     * @param cid
     * @return 返回CoinMarketCapIdBean
     */
    public static CoinMarketCapIdBean queryBean(int cid) {
        String sql = "select * from TAB_CoinMarketCap_id_map where c_id=?";
        return DBHelper.queryBean(CoinMarketCapIdBean.class, sql, cid);
    }

    /**
     * 查询全部数据
     *
     * @return 返回 list
     */
    public static List<CoinMarketCapIdBean> queryAll() {
        String sql = "select * from TAB_CoinMarketCap_id_map";
        return DBHelper.queryList(CoinMarketCapIdBean.class, sql);
    }

    public static void main(String[] args) {
        CoinIDMapCollector c = new CoinIDMapCollector();
        List<CoinMarketCapIdBean> list = c.getCoinMarketCapIds();
        logger.info(list.size());
        CoinMarketCapDao dao = new CoinMarketCapDao();
        CoinMarketCapDao.truncate();
        logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
        logger.info(CoinMarketCapDao.batchInsert(list).length);
        //logger.info(CoinMarketCapDao.insert(list.get(0)));
        logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
         CoinMarketCapIdBean coin = CoinMarketCapDao.queryBean(2781);
         logger.info(coin);
         //CoinMarketCapDao.delete(coin);
        //logger.info("QueryAll().size == " + CoinMarketCapDao.queryAll().size());
         
        
    }
}
