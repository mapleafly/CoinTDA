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
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.jdbc.DerbyJdbcDao;
import org.lifxue.cointda.models.TradeData;

/**
 *
 * @author xuelf
 */
public class TradeDataDao {

    private static final Logger logger = LogManager.getLogger(TradeDataDao.class.getName());

    public TradeDataDao() {

    }

    /**
     * 插入数据
     *
     * @param tradeData
     * @return
     */
    public int insert(TradeData tradeData) {
        String coinType = tradeData.getCoinType();
        String saleOrBuy = tradeData.getSaleOrBuy();
        double price = tradeData.getPrice();
        double num = tradeData.getNum();
        double totalPrice = tradeData.getTotalPrice();
        String date = tradeData.getDate();

        String sql = "insert into TAB_TRADEDATA(TD_COINTYPE,TD_SALEORBUY,"
                + "TD_PRICE,TD_NUM,TD_TOTALPRICE,TD_DATE) values ('" + coinType
                + "','" + saleOrBuy
                + "'," + price
                + "," + num
                + "," + totalPrice
                + ",'" + date + "')";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return dbjd.insert(sql);
    }

    /**
     * 修改数据
     *
     * @param tradeData
     * @return
     */
    public boolean update(TradeData tradeData) {
        int id = tradeData.getId();
        String coinType = tradeData.getCoinType();
        String saleOrBuy = tradeData.getSaleOrBuy();
        double price = tradeData.getPrice();
        double num = tradeData.getNum();
        double totalPrice = tradeData.getTotalPrice();
        String date = tradeData.getDate();
        String sql = "update TAB_TRADEDATA set TD_COINTYPE='" + coinType
                + "',TD_SALEORBUY='" + saleOrBuy
                + "',TD_PRICE=" + price
                + ",TD_NUM=" + num
                + ",TD_TOTALPRICE=" + totalPrice
                + ",TD_DATE='" + date
                + "' where TD_ID=" + id;
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return dbjd.update(sql);
    }

    /**
     * 删除数据
     *
     * @param tradeData
     * @return
     */
    public boolean delete(TradeData tradeData) {
        return delete(tradeData.getId());
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        String sql = "delete from TAB_TRADEDATA where TD_ID=" + id;
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return dbjd.delete(sql);
    }

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public TradeData QueryOne(int id) {
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

}
