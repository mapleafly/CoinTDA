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
package org.mapleaf.cointda.pool;

/**
 *
 * @author xuelf
 */
public class InitTable {

    /**
     * Creates a new instance of InitTable
     */
    public InitTable() {

    }

    /**
     * 初始化数据库
     *
     *
     */
    public static void createTable() {
        String sql;

        ////////////////////////////////
//        sql = "drop table tab_curuse_coin";
//        DBHelper.dropTable(sql);
//        sql = "drop table tab_trade_data";
//        DBHelper.dropTable(sql);
//        sql = "drop table TAB_CoinMarketCap_id_map";
//        DBHelper.dropTable(sql);
//        sql = "drop table TAB_CoinMarketCap_listings";
//       DBHelper.dropTable(sql);
        /////////////////////////////////////
        //当前使用的交易品种
        if (!DBHelper.doesTableExist("tab_curuse_coin")) {
            sql = "CREATE TABLE tab_curuse_coin ("
                    + "id integer NOT NULL,"
                    + "symbol VARCHAR(20),"
                    + "cmc_rank integer,"
                    + "lastUpdated VARCHAR(100),"
                    + "primary key (id))";
            DBHelper.createTable(sql);
        }
        //交易数据表
        if (!DBHelper.doesTableExist("tab_trade_data")) {
            sql = "CREATE TABLE tab_trade_data ("
                    + "id int generated by default as identity,"
                    + "coin_id int NOT NULL,"
                    + "coin_symbol VARCHAR(20) NOT NULL,"
                    + "sale_or_buy VARCHAR(10) NOT NULL,"
                    + "price  DECIMAL(23,8) NOT NULL,"
                    + "num  DECIMAL(23,8) NOT NULL,"
                    + "total_price  DECIMAL(23,8) NOT NULL,"
                    + "trade_date VARCHAR(100) NOT NULL,"
                    + "primary key (id))";
            DBHelper.createTable(sql);
        }
 
        //最新品种价格表
        if (!DBHelper.doesTableExist("tab_CoinMarketCap_listings")) {
            sql = "CREATE TABLE tab_CoinMarketCap_listings  ("
                    + "id integer NOT NULL,"
                    + "name VARCHAR(100),"
                    + "symbol VARCHAR(20),"
                    + "slug VARCHAR(100),"
                    + "cmc_rank integer,"
                    + "date_added VARCHAR(100),"
                    + "platform_id integer,"
                    + "token_address VARCHAR(100),"
                    + "numMarketPairs integer,"
                    + "maxSupply DECIMAL(31,12),"
                    + "circulatingSupply DECIMAL(31,12),"
                    + "totalSupply DECIMAL(31,12),"
                    + "price DECIMAL(31,12),"
                    + "volume_24h DECIMAL(31,12),"
                    + "percent_change_1h DECIMAL(31,12),"
                    + "percent_change_24h DECIMAL(31,12),"
                    + "percent_change_7d DECIMAL(31,12),"
                    + "marketCap DECIMAL(31,12),"
                    + "lastUpdated VARCHAR(100),"
                    + "primary key (id))";
            DBHelper.createTable(sql);
        }
    
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        InitTable.createTable();
    }
}
