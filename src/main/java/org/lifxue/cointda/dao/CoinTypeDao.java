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
import org.lifxue.cointda.models.CoinType;

/**
 *
 * @author xuelf
 */
public class CoinTypeDao {

    private static final Logger logger = LogManager.getLogger(CoinTypeDao.class.getName());

    public CoinTypeDao() {

    }

    /**
     * 插入CoinType数据
     *
     * @param coinType
     * @return
     */
    public boolean insert(CoinType coinType) {
        String shortName = coinType.getShortName();
        String fullName = coinType.getFullName();
        String cnName = coinType.getCnName();
        String sql = "insert into TAB_COINTYPE (SHORT_NAME,FULL_NAME,CN_NAME) values ('" 
                + shortName + "','" + fullName + "','" 
                + cnName + "')";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return (dbjd.insert(sql) != -1);
    }

   
    /**
     * 更新价格数据数据
     *
     * @param coinType
     * @return
     */
    public boolean update(CoinType coinType) {
        String shortName = coinType.getShortName();
        double price = coinType.getPrice();
        String date = coinType.getDate();
        return update(shortName, price, date);
    }
    
    /**
     * 更新价格数据
     * @param shortName
     * @param price
     * @param date
     * @return 
     */
    public boolean update(String shortName, double price, String date){
        String sql = "update TAB_COINTYPE set PRICE=" + price 
                + ",C_DATE='" + date 
                + "' where SHORT_NAME='" + shortName + "'";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return dbjd.update(sql);
        
    }

    /**
     * 删除数据
     *
     * @param coinType
     * @return
     */
    public boolean delete(CoinType coinType) {
        return delete(coinType.getShortName());
    }

    /**
     *删除数据
     * @param shortName
     * @return
     */
    public boolean delete(String shortName) {
        String sql = "delete from TAB_COINTYPE where SHORT_NAME='" + shortName + "'";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        return dbjd.delete(sql);
    }

    /**
     * 查询单个
     * @param shortName
     * @return
     */
    public CoinType QueryOne(String shortName) {
        String sql = "SELECT * FROM TAB_COINTYPE where SHORT_NAME='" + shortName + "'";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        CoinType ct = new CoinType();
        try {
            if (rs.next()) {
                ct.setShortName(rs.getString(1));
                ct.setFullName(rs.getString(2));
                ct.setCnName(rs.getString(3));
                ct.setPrice(rs.getDouble(4));
                ct.setDate(rs.getString(5));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            dbjd.close();
        }
        return ct;
    }

     /**
     * 查询全部简称
     * @return
     */
    public List<String> QueryAllShortName() {
        List<String> list = new ArrayList<String>();
        String sql = "SELECT SHORT_NAME FROM TAB_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(rs.getString("SHORT_NAME"));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            dbjd.close();
        }
        return list;
    }
    
    /**
     * 查询全部
     * @return
     */
    public List<CoinType> QueryAll() {
        List<CoinType> list = new ArrayList<CoinType>();
        String sql = "SELECT * FROM TAB_COINTYPE";
        DerbyJdbcDao dbjd = DerbyJdbcDao.getInstance();
        ResultSet rs = dbjd.executeQuery(sql);
        try {
            while (rs.next()) {
                CoinType ct = new CoinType();
                ct.setShortName(rs.getString(1));
                ct.setFullName(rs.getString(2));
                ct.setCnName(rs.getString(3));
                ct.setPrice(rs.getDouble(4));
                ct.setDate(rs.getString(5));
                list.add(ct);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            dbjd.close();
        }
        return list;
    }

}
