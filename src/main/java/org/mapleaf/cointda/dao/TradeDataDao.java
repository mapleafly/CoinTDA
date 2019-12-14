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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.CurUsedCoinBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.models.TradeDataFXC;
import org.mapleaf.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class TradeDataDao {

    private static final Logger logger = LogManager.getLogger(
            TradeDataDao.class.getName());

    public TradeDataDao() {

    }

    /**
     * 插入一条数据
     *
     * @param bean
     * @return 更新的行数
     */
    public static Integer insert(TradeDataBean bean) {
        String sql = "insert into tab_trade_data"
                + "(coin_id,coin_symbol,sale_or_buy,price,num,total_price,"
                + "trade_date)"
                + " values (?,?,?,?,?,?,?)";

        Object[] param = new Object[7];
        param[0] = bean.getCoin_id();
        param[1] = bean.getCoin_symbol();
        param[2] = bean.getSale_or_buy();
        param[3] = bean.getPrice();
        param[4] = bean.getNum();
        param[5] = bean.getTotal_price();
        param[6] = bean.getTrade_date();
        BigDecimal dec = DBHelper.insert(sql, param);

        return Integer.valueOf(dec.toBigInteger().toString());
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    public static int[] batchInsert(List<TradeDataBean> list) {
        String sql = "insert into tab_trade_data"
                + "(coin_id,coin_symbol,sale_or_buy,price,num,total_price,"
                + "trade_date)"
                + " values (?,?,?,?,?,?,?)";

        Object[][] params = new Object[list.size()][];
        //组织params
        for (int i = 0; i < list.size(); i++) {
            TradeDataBean bean = list.get(i);
            Object[] param = new Object[7];
            param[0] = bean.getCoin_id();
            param[1] = bean.getCoin_symbol();
            param[2] = bean.getSale_or_buy();
            param[3] = bean.getPrice();
            param[4] = bean.getNum();
            param[5] = bean.getTotal_price();
            param[6] = bean.getTrade_date();

            params[i] = param;
        }

        return DBHelper.batch(sql, params);
    }

    /**
     * 修改一条数据
     *
     * @param bean
     * @return
     */
    public static int update(TradeDataBean bean) {
        String sql = "update tab_trade_data set "
                + "coin_id=?,coin_symbol=?,sale_or_buy=?,price=?,num=?,"
                + "total_price=?,trade_date=?"
                + " where id=?";

        Object[] param = new Object[8];
        param[0] = bean.getCoin_id();
        param[1] = bean.getCoin_symbol();
        param[2] = bean.getSale_or_buy();
        param[3] = bean.getPrice();
        param[4] = bean.getNum();
        param[5] = bean.getTotal_price();
        param[6] = bean.getTrade_date();
        param[7] = bean.getId();

        return DBHelper.update(sql, param);
    }

    /**
     * 删除一条数据
     *
     * @param bean
     * @return
     */
    public static int delete(TradeDataBean bean) {
        String sql = "delete from tab_trade_data"
                + " where id=?";
        return DBHelper.update(sql, bean.getId());
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    public static boolean truncate() {
        String sql = "TRUNCATE TABLE tab_trade_data";
        return DBHelper.update(sql) == 0;
    }

    /**
     * 查询一条数据
     *
     * @param id
     * @return 返回CoinMarketCapListingBean
     */
    public static TradeDataBean queryBean(Integer id) {
        String sql = "select * from tab_trade_data where id=?";
        return DBHelper.queryBean(TradeDataBean.class, sql, id);
    }

    /**
     * 根据简称查询coin信息
     *
     * @param symbol
     * @return
     */
    public static CoinMarketCapListingBean queryCoinBySymbol(String symbol) {
        String sql = "select * from tab_CoinMarketCap_listings where symbol=?";
        return DBHelper.queryBean(
                CoinMarketCapListingBean.class, sql, symbol);

    }

    /**
     * 查询全部Coin的简称
     *
     * @return 返回 简称list
     */
    public static List<String> queryAllSymbol() {
        String sql = "select * from tab_curuse_coin order by cmc_rank";
        List<CurUsedCoinBean> list = DBHelper.queryList(
                CurUsedCoinBean.class, sql);
        List<String> ctList = new ArrayList<>();
        list.forEach((bean) -> {
            ctList.add(bean.getSymbol());
        });
        return ctList;
    }

    /**
     * 查询全部交易数据
     *
     * @return 返回 list
     */
    public static List<TradeDataBean> queryAll() {
        String sql = "select * from tab_trade_data order by id";
        return DBHelper.queryList(TradeDataBean.class, sql);
    }

    public static List<TradeDataFXC> queryAllFXC(String symbol) {
        String sql = "select * from tab_trade_data where coin_symbol=? order by id";
        List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql, symbol);
        List<TradeDataFXC> fxcList = new ArrayList<>();
        list.stream().map((bean) -> {
            TradeDataFXC fxc = new TradeDataFXC();
            fxc.setId(bean.getId());
            fxc.setCoinId(bean.getCoin_id());
            fxc.setCoinSymbol(bean.getCoin_symbol());
            fxc.setSaleOrBuy(bean.getSale_or_buy());
            fxc.setPrice(bean.getPrice().toString());
            fxc.setNum(bean.getNum().toString());
            fxc.setTotalPrice(bean.getTotal_price().toString());
            fxc.setDate(bean.getTrade_date());
            return fxc;
        }).forEachOrdered((fxc) -> {
            fxcList.add(fxc);
        });
        return fxcList;
    }

    /**
     * 查询全部交易数据
     *
     * @return 返回用于页面显示的list
     */
    public static List<TradeDataFXC> queryAllFXC() {
        List<TradeDataBean> list = queryAll();
        List<TradeDataFXC> fxcList = new ArrayList<>();
        list.stream().map((bean) -> {
            TradeDataFXC fxc = new TradeDataFXC();
            fxc.setId(bean.getId());
            fxc.setCoinId(bean.getCoin_id());
            fxc.setCoinSymbol(bean.getCoin_symbol());
            fxc.setSaleOrBuy(bean.getSale_or_buy());
            fxc.setPrice(bean.getPrice().toString());
            fxc.setNum(bean.getNum().toString());
            fxc.setTotalPrice(bean.getTotal_price().toString());
            fxc.setDate(bean.getTrade_date());
            return fxc;
        }).forEachOrdered((fxc) -> {
            fxcList.add(fxc);
        });
        return fxcList;
    }

    public static void main(String[] args) {
        TradeDataBean bean = new TradeDataBean();
        List<TradeDataBean> list = new ArrayList<>();
        bean.setCoin_id(1);
        bean.setCoin_symbol("BTC");
        bean.setSale_or_buy("买");
        bean.setPrice(new BigDecimal("7455.001"));
        bean.setNum(new BigDecimal("1.000"));
        bean.setTotal_price(new BigDecimal("7455.001"));
        bean.setTrade_date("2019-12-09");
        for (int i = 0; i < 15; i++) {
            list.add(bean);
        }
        logger.info(list.size());
        //CoinListingDao dao = new CoinListingDao();
        TradeDataDao.truncate();
        logger.info("QueryAll().size == " + TradeDataDao.queryAll().size());
        logger.info(TradeDataDao.batchInsert(list).length);
        //logger.info(TradeDataDao.insert(bean));
        logger.info("QueryAll().size == " + TradeDataDao.queryAll().size());
        //TradeDataBean coin2 = TradeDataDao.queryBean(1);
        //logger.info(coin2);
        //TradeDataDao.delete(coin2);
        logger.info("QueryAll().size == " + TradeDataDao.queryAll().get(0));
        logger.info("QueryAll().size == " + TradeDataDao.queryAll().get(1));
        logger.info("QueryAll().size == " + TradeDataDao.queryAll().get(14));

    }
}
