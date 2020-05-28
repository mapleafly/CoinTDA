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

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author xuelf
 */
public class DruidConnection {

    private static final Logger logger = LogManager.getLogger(DruidConnection.class.getName());

    private static Properties properties = null;
    private static DruidDataSource dataSource = null;
    private volatile static DruidConnection instatce = null;
    private DruidPooledConnection connection = null;

    //私有构造函数,防止实例化对象
    private DruidConnection() {

    }

    static {
        try {
            //设定为jar包的绝对路径 在IDE中运行时为project的绝对路径
            String filePath = System.getProperty("user.dir");
            InputStream ipstream = new BufferedInputStream(new FileInputStream(filePath + "/conf/druid.properties"));
            properties = new Properties();
            properties.load(ipstream);
            dataSource = getDatasource();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 用简单单例模式确保只返回一个链接对象
     *
     * @return
     */
    public static DruidConnection getInstace() {
        if (instatce == null) {
            synchronized (DruidConnection.class) {
                if (instatce == null) {
                    instatce = new DruidConnection();
                }
            }
        }
        return instatce;
    }

    // 返回一个数据源
    public DataSource getDataSource() {
        return dataSource;
    }

    // 返回一个链接
    public DruidPooledConnection getConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    // 加载数据源
    private static DruidDataSource getDatasource() {
        DruidDataSource source = null;
        try {
            source = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return source;
    }

}
