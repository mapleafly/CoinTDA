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
package org.lifxue.cointda.dao.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author xuelf
 */
public class DerbyJdbcDao {

    private static final Logger logger = LogManager.getLogger(DerbyJdbcDao.class.getName());

    private String framework = "embedded";
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "CoinTDADB";
    private String username = "admin";
    private String password = "admin";

    private Connection conn = null;
    private static DerbyJdbcDao m_instance = null;

    /**
     * Creates a new instance of DerbyJdbcDao
     */
    private DerbyJdbcDao() {
        try {
            Properties props = new Properties();
            props.put("user", username);
            props.put("password", password);

            //Class.forName(driver).newInstance();
            //Class.forName(driver).getDeclaredConstructor().newInstance();
            Class<?> clazz = Class.forName(driver);
            clazz.getConstructor().newInstance();
            conn = DriverManager.getConnection(protocol
                    + dbName + ";create=true", props);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            logger.debug("exception thrown:");
            if (e instanceof SQLException) {
                logger.debug(e.toString());
            } else {
                logger.debug(e.toString());
            }
        } catch (IllegalArgumentException | SecurityException | NoSuchMethodException | InvocationTargetException ex) {
            logger.debug(ex.toString());
        }
    }

    public static DerbyJdbcDao getInstance() {
        synchronized (DerbyJdbcDao.class) {
            if (m_instance == null) {
                m_instance = new DerbyJdbcDao();
            }
        }
        return m_instance;
    }

    /**
     * 创建表
     *
     *
     * @param sql
     * @return
     */
    public boolean createTable(String sql) {
        return execute(sql);
    }

    /**
     * 删除表
     *
     * @param sql
     * @return
     */
    public boolean dropTable(String sql) {
        return execute(sql);
        //s.execute("drop table derbyDB");
    }

    /**
     * 插入数据
     *
     * @param sql
     * @return
     */
    public int insert(String sql) {
        return EexecuteInsert(sql);
    }

    /**
     * 修改数据
     *
     * @param sql
     * @return
     */
    public boolean update(String sql) {
        return execute(sql);
    }

    /**
     * 删除数据
     *
     * @param sql
     * @return
     */
    public boolean delete(String sql) {
        return execute(sql);
    }

    public int EexecuteInsert(String sql) {
        int key = -1;
        try {
            if (conn == null) {
                return key;
            }
            conn.setAutoCommit(false);
            try (Statement s = conn.createStatement()) {
                s.execute(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    key = rs.getInt(1);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            logger.debug("exception thrown:");
            if (e instanceof SQLException) {
                logger.debug(e.toString());
            } else {
                logger.debug(e.toString());
            }
            key = -1;
        }
        return key;

    }
    
    public boolean execute(String sql) {
        boolean isOK = true;
        try {
            if (conn == null) {
                return false;
            }
            conn.setAutoCommit(false);
            try (Statement s = conn.createStatement()) {
                s.execute(sql);
            }
            conn.commit();
        } catch (SQLException e) {
            logger.debug("exception thrown:");
            if (e instanceof SQLException) {
                logger.debug(e.toString());
            } else {
                logger.debug(e.toString());
            }
            isOK = false;
        }
        return isOK;

    }

    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            Statement s = conn.createStatement();
            rs = s.executeQuery(sql);

            conn.commit();
        } catch (SQLException e) {
            logger.debug("exception thrown:");
            if (e instanceof SQLException) {
                logger.debug(e.toString());
            } else {
                logger.debug(e.toString());
            }
        }
        return rs;
    }

    public void close() {
        try {
            if (conn == null) {
                return;
            }
            conn.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
    }
}
