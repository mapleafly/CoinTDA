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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapIdBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.bean.property.TradeDataFXC;
import org.mapleaf.cointda.pool.DBHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** @author xuelf */
public class TradeDataDao {

  private static final Logger logger = LogManager.getLogger(TradeDataDao.class.getName());

  public TradeDataDao() {}

  /**
   * @Description: 插入一条数据
   *
   * @param bean 1
   * @return: java.lang.Integer 返回插入行的id
   * @author: mapleaf
   * @date: 2020/6/23 18:16
   */
  public static Integer insert(TradeDataBean bean) {
    String sql =
        "insert into tab_tradeinfo"
            + "(base_id,base_symbol,quote_id,quote_symbol,"
            + "sale_or_buy,price,base_num,quote_num,trade_date) "
            + "values (?,?,?,?,?,?,?,?,?)";

    BigDecimal dec = DBHelper.insert(sql, getInsertParam(bean));

    return Integer.valueOf(dec.toBigInteger().toString());
  }

  private static Object[] getInsertParam(TradeDataBean bean) {
    Object[] param = new Object[9];
    param[0] = bean.getBase_id();
    param[1] = bean.getBase_symbol();
    param[2] = bean.getQuote_id();
    param[3] = bean.getQuote_symbol();
    param[4] = bean.getSale_or_buy();
    param[5] = bean.getPrice();
    param[6] = bean.getBase_num();
    param[7] = bean.getQuote_num();
    param[8] = bean.getTrade_date();

    return param;
  }
  /**
   * @Description: 批量插入数据
   *
   * @param list 1
   * @return: int[]
   * @author: mapleaf
   * @date: 2020/6/23 18:16
   */
  public static int[] batchInsert(List<String[]> list) {
    String sql =
        "insert into tab_tradeinfo"
            + "(base_id,base_symbol,quote_id,quote_symbol,"
            + "sale_or_buy,price,base_num,quote_num,trade_date) "
            + "values (?,?,?,?,?,?,?,?,?)";

    Object[][] params = new Object[list.size()][];
    // 组织params
    for (int i = 0; i < list.size(); i++) {
      String[] bean = list.get(i);
      Object[] param = new Object[9];
      param[0] = bean[1];
      param[1] = bean[2];
      param[2] = bean[3];
      param[3] = bean[4];
      param[4] = bean[5];
      param[5] = bean[6];
      param[6] = bean[7];
      param[7] = bean[8];
      param[8] = bean[9];

      params[i] = param;
    }

    return DBHelper.batch(sql, params);
  }

  /**
   * @Description: 修改一条数据
   *
   * @param bean 1
   * @return: int
   * @author: mapleaf
   * @date: 2020/6/23 18:17
   */
  public static int update(TradeDataBean bean) {
    String sql =
        "update tab_tradeinfo set "
            + "base_id=?,base_symbol=?,quote_id=?,quote_symbol=?,"
            + "sale_or_buy=?,price=?,base_num=?,quote_num=?,trade_date=? "
            + "where id=?";

    Object[] param = new Object[10];
    param[0] = bean.getBase_id();
    param[1] = bean.getBase_symbol();
    param[2] = bean.getQuote_id();
    param[3] = bean.getQuote_symbol();
    param[4] = bean.getSale_or_buy();
    param[5] = bean.getPrice();
    param[6] = bean.getBase_num();
    param[7] = bean.getQuote_num();
    param[8] = bean.getTrade_date();
    param[9] = bean.getId();

    return DBHelper.update(sql, param);
  }

  /**
   * @Description: 删除一条数据
   *
   * @param bean 1
   * @return: int
   * @author: mapleaf
   * @date: 2020/6/23 18:17
   */
  public static int delete(TradeDataBean bean) {
    return delete(bean.getId());
  }

  public static int delete(Integer id) {
    String sql = "delete from tab_tradeinfo" + " where id=?";
    return DBHelper.update(sql, id);
  }

  /**
   * @Description: 删除全部数据
   *
   * @return: boolean
   * @author: mapleaf
   * @date: 2020/6/23 18:17
   */
  public static boolean truncate() {
    String sql = "TRUNCATE TABLE tab_tradeinfo";
    return DBHelper.update(sql) == 0;
  }

  /**
   * @Description: 查询一条数据
   *
   * @param id 1
   * @return: org.mapleaf.cointda.bean.TradeDataBean
   * @author: mapleaf
   * @date: 2020/6/23 18:17
   */
  public static TradeDataBean queryBean(Integer id) {
    String sql = "select * from tab_tradeinfo where id=?";
    return DBHelper.queryBean(TradeDataBean.class, sql, id);
  }

  /**
   * @Description: 根据简称查询coin信息
   *
   * @param symbol 1
   * @return: org.mapleaf.cointda.bean.CoinMarketCapIdBean
   * @author: mapleaf
   * @date: 2020/6/23 18:18
   */
  public static CoinMarketCapIdBean queryCoinBySymbol(String symbol) {
    String sql = "select * from TAB_CoinMarketCap_id_map where symbol=?";
    return DBHelper.queryBean(CoinMarketCapIdBean.class, sql, symbol);
  }

  /**
   * 查询全部交易数据
   *
   * @return 返回 list
   */
  public static List<TradeDataBean> queryAll() {
    String sql = "select * from tab_tradeinfo order by id";
    return DBHelper.queryList(TradeDataBean.class, sql);
  }

  public static List<TradeDataFXC> queryAllFXC(String symbol) {
    String sql = "select * from tab_tradeinfo where base_symbol=? order by id DESC";
    List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql, symbol);
    List<TradeDataFXC> fxcList = new ArrayList<>();
    list.stream()
        .map(
            (bean) -> {
              TradeDataFXC fxc = new TradeDataFXC();
              fxc.setId(bean.getId());
              fxc.setCoinId(bean.getBase_id());
              fxc.setSymbolPairs(bean.getBase_symbol() + "/" + bean.getQuote_symbol());
              fxc.setSaleOrBuy(bean.getSale_or_buy());
              fxc.setPrice(bean.getPrice());
              fxc.setBaseNum(bean.getBase_num());
              fxc.setQuoteNum(bean.getQuote_num());
              fxc.setDate(bean.getTrade_date());
              return fxc;
            })
        .forEachOrdered((fxc) -> fxcList.add(fxc));
    return fxcList;
  }

  public static List<TradeDataFXC> queryAllFXCForCash(String symbol) {
    String sql = "select * from tab_tradeinfo where base_symbol=? order by id DESC";
    List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql, symbol);
    List<TradeDataFXC> fxcList = new ArrayList<>();
    list.stream()
        .map(
            (bean) -> {
              TradeDataFXC fxc = new TradeDataFXC();
              fxc.setId(bean.getId());
              fxc.setCoinId(bean.getBase_id());
              fxc.setSymbolPairs(bean.getBase_symbol());
              if (bean.getSale_or_buy().equals("卖")) {
                fxc.setSaleOrBuy("入金");
              } else {
                fxc.setSaleOrBuy("出金");
              }
              fxc.setPrice(bean.getPrice());
              fxc.setBaseNum(bean.getBase_num());
              fxc.setQuoteNum(bean.getQuote_num());
              fxc.setDate(bean.getTrade_date());
              return fxc;
            })
        .forEachOrdered((fxc) -> fxcList.add(fxc));
    return fxcList;
  }

  public static void main(String[] args) {
    TradeDataBean bean = new TradeDataBean();
    List<TradeDataBean> list = new ArrayList<>();
    bean.setBase_id(1);
    bean.setBase_symbol("BTC");
    bean.setQuote_id(825);
    bean.setQuote_symbol("USTD");
    bean.setSale_or_buy("买");
    bean.setPrice("7455.001");
    bean.setBase_num("1.000");
    bean.setQuote_num("7455.001");
    bean.setTrade_date("2019-12-09");
    for (int i = 0; i < 15; i++) {
      list.add(bean);
    }
    logger.info(list.size());
    // CoinListingDao dao = new CoinListingDao();
    TradeDataDao.truncate();
    // logger.info(TradeDataDao.batchInsert(list).length);
    // logger.info(TradeDataDao.insert(bean));
    // TradeDataBean coin2 = TradeDataDao.queryBean(1);
    // logger.info(coin2);
    // TradeDataDao.delete(coin2);

  }
}
