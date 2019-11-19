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

/**
 *
 * @author xuelf
 */
public class TypePieChartDao {
     private static final Logger logger = LogManager.getLogger(TypePieChartDao.class.getName());
     
     /**
     * 查询各个品种的买入总价
     *
     * @return
     */
    public List<Map<String,Double>> QueryBuyTotal() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_TOTALPRICE) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='买' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String,Double>();
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
     * 查询各个品种的卖出总价
     *
     * @return
     */
    public List<Map<String,Double>> QuerySaleTotal() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_TOTALPRICE) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='卖' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String,Double>();
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
     * 查询各个品种的买入总量
     *
     * @return
     */
    public List<Map<String,Double>> QueryBuyNum() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_NUM) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='买' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String,Double>();
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
    public List<Map<String,Double>> QuerySaleNum() {
        List list = new ArrayList();
        String sql = "SELECT TD_COINTYPE,SUM(TD_NUM) FROM TAB_TRADEDATA WHERE TD_SALEORBUY='卖' GROUP BY TD_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                Map map = new HashMap<String,Double>();
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
