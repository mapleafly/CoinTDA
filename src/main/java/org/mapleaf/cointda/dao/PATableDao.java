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
import org.mapleaf.cointda.bean.CoinQuotesLatestBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.bean.property.TradeDataFXC;
import org.mapleaf.cointda.pool.DBHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuelf
 */
public class PATableDao {

    private static final Logger logger = LogManager.getLogger(PATableDao.class.getName());

    public static List<TradeDataFXC> queryBy(
        String strCoinSymbol, String strStartDate, String strEndDate, String tradeType) {
        List<CoinQuotesLatestBean> lastList = TypePieChartDao.queryByTradeData();
        String sql =
            "select * from tab_tradeinfo where base_symbol=? "
                + "and trade_date>=? and trade_date<=? ";
        if (!tradeType.equals("全部")) {
            sql += "and sale_or_buy='" + tradeType + "'";
        }
        sql += " order by id DESC";
        Object[] params = new Object[3];
        params[0] = strCoinSymbol;
        params[1] = strStartDate;
        params[2] = strEndDate;
        List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql, params);
        List<TradeDataFXC> fxcList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return fxcList;
        }
        list.stream()
            .map(
                (bean) -> {
                    TradeDataFXC fxc = new TradeDataFXC();
                    fxc.setId(bean.getId());
                    fxc.setCoinId(bean.getBase_id());
                    fxc.setSymbolPairs(bean.getBase_symbol() + "/" + bean.getQuote_symbol());
                    for (CoinQuotesLatestBean data : lastList) {
                        if (data.getSymbol().equals(bean.getBase_symbol())) {
                            BigDecimal curPrice = new BigDecimal(data.getPrice());
                            BigDecimal payPrice = new BigDecimal(bean.getPrice());
                            String chg = curPrice.subtract(payPrice)
                                .divide(payPrice, 5, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("100"))
                                .setScale(2, RoundingMode.HALF_UP)
                                .toPlainString();
                            fxc.setChg(chg + "%");
                            break;
                        }
                    }
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

    /**
     * @param symbol 1
     * @Description: 根据简称查询当前coin价格信息
     * @return: org.mapleaf.cointda.bean.CoinQuotesLatestBean
     * @author: mapleaf
     * @date: 2020/6/23 18:42
     */
    public static CoinQuotesLatestBean queryBySymbol(String symbol) {
        String sql = "select * from tab_quotesLatest where symbol=?";
        return DBHelper.queryBean(CoinQuotesLatestBean.class, sql, symbol);
    }

    /**
     * 查询全部数据
     *
     * @return 返回用于页面显示的list
     */
    public static List<TradeDataFXC> queryAllFXC() {
        List<CoinQuotesLatestBean> lastList = TypePieChartDao.queryByTradeData();
        String sql = "select * from tab_tradeinfo order by trade_date DESC";
        List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql);
        List<TradeDataFXC> fxcList = new ArrayList<>();
        for (TradeDataBean bean : list) {
            TradeDataFXC fxc = new TradeDataFXC();
            fxc.setId(bean.getId());
            fxc.setCoinId(bean.getBase_id());
            fxc.setSymbolPairs(bean.getBase_symbol() + "/" + bean.getQuote_symbol());
            for (CoinQuotesLatestBean data : lastList) {
                if (data.getSymbol().equals(bean.getBase_symbol())) {
                    BigDecimal curPrice = new BigDecimal(data.getPrice());
                    BigDecimal payPrice = new BigDecimal(bean.getPrice());
                    String chg = curPrice.subtract(payPrice)
                        .divide(payPrice, 5, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP)
                        .toPlainString();
                    fxc.setChg(chg + "%");
                    break;
                }
            }
            fxc.setSaleOrBuy(bean.getSale_or_buy());
            fxc.setPrice(bean.getPrice());
            fxc.setBaseNum(bean.getBase_num());
            fxc.setQuoteNum(bean.getQuote_num());
            fxc.setDate(bean.getTrade_date());

            fxcList.add(fxc);
        }
        return fxcList;
    }
}
