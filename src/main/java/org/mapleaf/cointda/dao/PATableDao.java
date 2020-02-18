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

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.bean.property.TradeDataFXC;
import org.mapleaf.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class PATableDao {

    private static final Logger logger = LogManager.getLogger(PATableDao.class.getName());

     
    public static List<TradeDataFXC> queryBy(String strCoinSymbol, 
            String strStartDate, String strEndDate){
          String sql = "select * from tab_trade_data where coin_symbol=? "
                  + "and trade_date>=? and trade_date<=? order by id";
          Object[] params = new Object[3];
          params[0] = strCoinSymbol;
          params[1] = strStartDate;
          params[2] = strEndDate;
        List<TradeDataBean> list = DBHelper.queryList(TradeDataBean.class, sql, params);
        List<TradeDataFXC> fxcList = new ArrayList<>();
        if(list == null || list.isEmpty()){
            return fxcList;
        }
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
     * 根据简称查询coin信息
     * @param symbol
     * @return 
     */
    public static CoinMarketCapListingBean queryBySymbol(String symbol) {
        String sql = "select * from tab_CoinMarketCap_listings where symbol=?";
        return DBHelper.queryBean(
                CoinMarketCapListingBean.class, sql, symbol);

    }
    
     /**
     * 查询全部数据
     *
     * @return 返回 list
     */
    public static List<String> queryAllSymbol() {
        String sql = "select distinct coin_symbol from tab_trade_data";
        return DBHelper.queryColumn(sql);
    }
    
     /**
     * 查询全部数据
     * @return 返回用于页面显示的list
     */
    public static List<TradeDataFXC> queryAllFXC(){
        List<TradeDataBean> list = TradeDataDao.queryAll();
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
    
    public static void main(String[] s){
        PATableDao.queryAllSymbol().forEach(action ->logger.info(action));
    }
   
}
