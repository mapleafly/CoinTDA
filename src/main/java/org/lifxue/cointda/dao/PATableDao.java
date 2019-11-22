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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.jdbc.DerbyJdbcDao;
import org.lifxue.cointda.models.TradeData;

/**
 *
 * @author xuelf
 */
public class PATableDao {

    private static final Logger logger = LogManager.getLogger(PATableDao.class.getName());

     /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public TradeData QueryById(int id) {
        String sql = "SELECT * FROM TAB_TRADEDATA where TD_ID=" + id;
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        TradeData td = new TradeData();
        try {
            if (rs.next()) {
                td.setId(rs.getInt(1));
                td.setCoinType(rs.getString(2));
                td.setSaleOrBuy(rs.getString(3));
                td.setPrice(rs.getDouble(4));
                td.setNum(rs.getDouble(5));
                td.setTotalPrice(rs.getDouble(6));
                td.setDate(rs.getString(7));
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return td;
    }
    
     /**
     * 查询某类全部
     *
     * @param strCoinType
     * @param strStartDate
     * @param strEndDate
     * @return
     */
    public List<TradeData> QueryByTypeAndDate(String strCoinType, String strStartDate, String strEndDate) {
        List list = new ArrayList();
        String sql = "SELECT * FROM TAB_TRADEDATA where "
                + "TD_COINTYPE='"+ strCoinType+ "' "
                + "and TD_DATE>='"+strStartDate+"' "
                + "and TD_DATE <='"+strEndDate+"'"
                + "  ORDER BY TD_ID";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                TradeData td = new TradeData();
                td.setId(rs.getInt(1));
                td.setCoinType(rs.getString(2));
                td.setSaleOrBuy(rs.getString(3));
                td.setPrice(rs.getDouble(4));
                td.setNum(rs.getDouble(5));
                td.setTotalPrice(rs.getDouble(6));
                td.setDate(rs.getString(7));
                list.add(td);
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return list;
    }
    
    /**
     * 查询某类全部
     *
     * @param strCoinType
     * @return
     */
    public List<TradeData> QueryByType(String strCoinType) {
        List list = new ArrayList();
        String sql = "SELECT * FROM TAB_TRADEDATA where TD_COINTYPE='"+ strCoinType+ "' ORDER BY TD_ID";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                TradeData td = new TradeData();
                td.setId(rs.getInt(1));
                td.setCoinType(rs.getString(2));
                td.setSaleOrBuy(rs.getString(3));
                td.setPrice(rs.getDouble(4));
                td.setNum(rs.getDouble(5));
                td.setTotalPrice(rs.getDouble(6));
                td.setDate(rs.getString(7));
                list.add(td);
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return list;
    }
    
     /**
     * 查询全部
     *
     * @return
     */
    public List<TradeData> QueryAll() {
        List list = new ArrayList();
        String sql = "SELECT * FROM TAB_TRADEDATA ORDER BY TD_ID";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                TradeData td = new TradeData();
                td.setId(rs.getInt(1));
                td.setCoinType(rs.getString(2));
                td.setSaleOrBuy(rs.getString(3));
                td.setPrice(rs.getDouble(4));
                td.setNum(rs.getDouble(5));
                td.setTotalPrice(rs.getDouble(6));
                td.setDate(rs.getString(7));
                list.add(td);
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return list;
    }
    
    /**
     * 查询各个品种的买入总量
     *
     * @return
     */
    public List<Map<String, Double>> QueryBuyNum() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_NUM) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='买' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String, Double>();
                map.put(rs.getString(1), rs.getDouble(2));
                list.add(map);
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return list;
    }

    /**
     * 查询各个品种的卖出总量
     *
     * @return
     */
    public List<Map<String, Double>> QuerySaleNum() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_NUM) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='卖' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String, Double>();
                map.put(rs.getString(1), rs.getDouble(2));
                list.add(map);
            }
        } catch (SQLException ex) {
            logger.debug(ex);
            dbjd.close();
        }
        return list;
    }
}
