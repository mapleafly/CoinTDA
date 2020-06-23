/*
 * Copyright 2020 lif.
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinQuotesLatestBean;
import org.mapleaf.cointda.pool.DBHelper;

import java.util.List;

/** @author lif */
public class CoinQuotesLatestDao {

  private static final Logger logger = LogManager.getLogger(CoinQuotesLatestDao.class.getName());

  /**
   * @Description: 修改一条数据
   *
   * @param bean 1
   * @return: int
   * @author: mapleaf
   * @date: 2020/6/23 17:57
   */
  public static int update(CoinQuotesLatestBean bean) {
    String sql =
        "update tab_quotesLatest set "
            + "name=?,symbol=?,slug=?,num_market_pairs=?,date_added=?,"
            + "max_supply=?,circulating_supply=?,total_supply=?,"
            + "is_active=?,platform_id=?,token_address=?,cmc_rank=?,"
            + "is_fiat=?,lastUpdated=?,price=?,volume_24h=?,"
            + "percent_change_1h=?,percent_change_24h=?,"
            + "percent_change_7d=?,market_cap=? "
            + "where id=?";

    Object[] param = new Object[21];
    param[0] = bean.getName();
    param[1] = bean.getSymbol();
    param[2] = bean.getSlug();
    param[3] = bean.getNum_market_pairs();
    param[4] = bean.getDate_added();
    param[5] = bean.getMax_supply();
    param[6] = bean.getCirculating_supply();
    param[7] = bean.getTotal_supply();
    param[8] = bean.getIs_active();
    param[9] = bean.getPlatform_id();
    param[10] = bean.getToken_address();
    param[11] = bean.getCmc_rank();
    param[12] = bean.getIs_fiat();
    param[13] = bean.getLastUpdated();
    param[14] = bean.getPrice();
    param[15] = bean.getVolume_24h();
    param[16] = bean.getPercent_change_1h();
    param[17] = bean.getPercent_change_24h();
    param[18] = bean.getPercent_change_7d();
    param[19] = bean.getMarket_cap();
    param[20] = bean.getId();

    return DBHelper.update(sql, param);
  }

  /**
   * @Description: 插入一条数据
   *
   * @param bean 1
   * @return: int
   * @author: mapleaf
   * @date: 2020/6/23 17:58
   */
  public static int insert(CoinQuotesLatestBean bean) {
    String sql =
        "insert into tab_quotesLatest"
            + "(id,name,symbol,slug,num_market_pairs,date_added,"
            + "max_supply,circulating_supply,total_supply,"
            + "is_active,platform_id,token_address,cmc_rank,"
            + "is_fiat,lastUpdated,price,volume_24h,"
            + "percent_change_1h,percent_change_24h,"
            + "percent_change_7d,market_cap)"
            + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    return DBHelper.update(sql, getInsertParam(bean));
  }

  private static Object[] getInsertParam(CoinQuotesLatestBean bean) {
    Object[] param = new Object[21];
    param[0] = bean.getId();
    param[1] = bean.getName();
    param[2] = bean.getSymbol();
    param[3] = bean.getSlug();
    param[4] = bean.getNum_market_pairs();
    param[5] = bean.getDate_added();
    param[6] = bean.getMax_supply();
    param[7] = bean.getCirculating_supply();
    param[8] = bean.getTotal_supply();
    param[9] = bean.getIs_active();
    param[10] = bean.getPlatform_id();
    param[11] = bean.getToken_address();
    param[12] = bean.getCmc_rank();
    param[13] = bean.getIs_fiat();
    param[14] = bean.getLastUpdated();
    param[15] = bean.getPrice();
    param[16] = bean.getVolume_24h();
    param[17] = bean.getPercent_change_1h();
    param[18] = bean.getPercent_change_24h();
    param[19] = bean.getPercent_change_7d();
    param[20] = bean.getMarket_cap();

    return param;
  }

  /**
   * @Description: 批量插入数据
   *
   * @param list 1
   * @return: int[]
   * @author: mapleaf
   * @date: 2020/6/23 17:58
   */
  public static int[] batchInsert(List<CoinQuotesLatestBean> list) {
    String sql =
        "insert into tab_quotesLatest"
            + "(id,name,symbol,slug,num_market_pairs,date_added,"
            + "max_supply,circulating_supply,total_supply,"
            + "is_active,platform_id,token_address,cmc_rank,"
            + "is_fiat,lastUpdated,price,volume_24h,"
            + "percent_change_1h,percent_change_24h,"
            + "percent_change_7d,market_cap)"
            + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    Object[][] params = new Object[list.size()][];
    // 组织params
    for (int i = 0; i < list.size(); i++) {
      CoinQuotesLatestBean bean = list.get(i);
      params[i] = getInsertParam(bean);
    }

    return DBHelper.batch(sql, params);
  }

  /**
   * @Description: 删除一条数据
   *
   * @param bean 1
   * @return: int
   * @author: mapleaf
   * @date: 2020/6/23 17:58
   */
  public static int delete(CoinQuotesLatestBean bean) {
    String sql = "delete from tab_quotesLatest" + " where id=?";
    return DBHelper.update(sql, bean.getId());
  }

  /**
   * @Description: 删除全部数据
   *
   * @return: boolean
   * @author: mapleaf
   * @date: 2020/6/23 17:59
   */
  public static boolean truncate() {
    String sql = "TRUNCATE TABLE tab_quotesLatest";
    return DBHelper.update(sql) == 0;
  }

  /**
   * @Description: 查询一条数据
   *
   * @param id 1
   * @return: org.mapleaf.cointda.bean.CoinQuotesLatestBean
   * @author: mapleaf
   * @date: 2020/6/23 17:59
   */
  public static CoinQuotesLatestBean queryBean(int id) {
    String sql = "select * from tab_quotesLatest where id=?";
    return DBHelper.queryBean(CoinQuotesLatestBean.class, sql, id);
  }

  /**
   * @Description: 查询全部数据
   *
   * @return: java.util.List<org.mapleaf.cointda.bean.CoinQuotesLatestBean>
   * @author: mapleaf
   * @date: 2020/6/23 17:59
   */
  public static List<CoinQuotesLatestBean> queryAll() {
    String sql = "select * from tab_quotesLatest";
    return DBHelper.queryList(CoinQuotesLatestBean.class, sql);
  }

  //    public static void main(String[] args) {
  //        CoinQuotesLatestCollector c = new CoinQuotesLatestCollector();
  //        List<CoinQuotesLatestBean> list = c.getQuotesLatest("id", "1,2");
  //        logger.info(list.size());
  //        CoinQuotesLatestDao.truncate();
  //        logger.info("QueryAll().size == " + CoinQuotesLatestDao.queryAll().size());
  //        //logger.info(CoinQuotesLatestDao.batchInsert(list).length);
  //        logger.info(CoinQuotesLatestDao.insert(list.get(0)));
  //        logger.info("QueryAll().size == " + CoinQuotesLatestDao.queryAll().size());
  //        CoinQuotesLatestBean coin = CoinQuotesLatestDao.queryBean(1);
  //        logger.info(coin);
  //        CoinQuotesLatestDao.delete(coin);
  //        logger.info("QueryAll().size == " + CoinQuotesLatestDao.queryAll().size());
  //
  //    }
}
