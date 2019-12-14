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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author xuelf
 */
public class DBHelper {

    private static final org.apache.logging.log4j.Logger logger
            = LogManager.getLogger(DBHelper.class.getName());
    private static Connection connection = null;
    private static DataSource ds = null;

    //获取元数据
    public static DataSource getDatasource() {
        if (ds == null) {
            ds = DruidConnection.getInstace().getDataSource();
        }
        return ds;
    }

    //获取链接
    public static Connection getConnection() {
        connection = DruidConnection.getInstace().getConnection();
        return connection;
    }

    //归还资源
    public static void release() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    public static QueryRunner getRunner() {
        ds = getDatasource();
        return new QueryRunner(ds);
    }

    /**
     *
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {
        QueryRunner run = DBHelper.getRunner();
        try {
            return run.update(sql, params);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }

        return -1;
    }

    /**
     *
     * Cloudscape:相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()
     * DB2:相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()
     * DB2_MF:相当于selectKey的SQL为：SELECT IDENTITY_VAL_LOCAL() FROM
     * SYSIBM.SYSDUMMY1 Derby:相当于selectKey的SQL为：VALUES IDENTITY_VAL_LOCAL()
     * HSQLDB:相当于selectKey的SQL为：CALL IDENTITY()
     * Informix:相当于selectKey的SQL为：select * dbinfo('sqlca.sqlerrd1') from
     * systables where tabid=1 MySql:相当于selectKey的SQL为：SELECT LAST_INSERT_ID()
     * SqlServer:相当于selectKey的SQL为：SELECT SCOPE_IDENTITY()
     * SYBASE:相当于selectKey的SQL为：SELECT @@IDENTITY
     *
     * @param sql
     * @param params
     * @return
     */
    public static BigDecimal insert(String sql, Object... params) {
        QueryRunner run = DBHelper.getRunner();
        try {
            if (run.update(sql, params) == 1) {
                return (BigDecimal) run.query(
                        "VALUES IDENTITY_VAL_LOCAL()",
                        new ScalarHandler(1)
                );
            }
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }

        return new BigDecimal("-1");
    }

    /**
     *
     * @param sql
     * @param params
     * @return
     */
    public static int[] batch(String sql, Object[][] params) {
        QueryRunner run = DBHelper.getRunner();
        try {
            return run.batch(sql, params);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
        //获取链接
        QueryRunner runner = DBHelper.getRunner();
        try {
            //返回查询值
            return (T) runner.query(sql, rsh, params);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    public static <T> T queryColumn(String sql, Object... params) {
        QueryRunner run = DBHelper.getRunner();
        try {
            return (T) run.query(sql, new ColumnListHandler(1), params);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    public static <T> List<T> queryList(Class<T> clazz, String sql, Object... params) {
        return (List<T>) query(sql, new BeanListHandler<>(clazz,
                new BasicRowProcessor(new GenerousBeanProcessor())),
                params);
    }

    public static <T> T queryBean(Class<T> clazz, String sql, Object... params) {
        return (T) DBHelper.query(sql,
                new BeanHandler<>(clazz,
                        new BasicRowProcessor(new GenerousBeanProcessor())),
                params);
    }

    public static int createTable(String sql) {
        QueryRunner run = DBHelper.getRunner();
        try {
            return run.execute(sql);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }
        return -1;
    }

    public static int dropTable(String sql) {
        QueryRunner run = DBHelper.getRunner();
        try {
            return run.execute(sql);
        } catch (SQLException ex) {
            logger.error(ex.toString());
        }
        return -1;
    }

    public static Boolean doesTableExist(String tablename) {
        Connection con = DBHelper.getConnection();
        HashSet<String> set = new HashSet<>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet res = meta.getTables(null, null, null,
                    new String[]{"TABLE"})) {
                while (res.next()) {
                    set.add(res.getString("TABLE_NAME"));
                }
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        //System.out.println(set);  
        return set.contains(tablename.toUpperCase());
    }
}
