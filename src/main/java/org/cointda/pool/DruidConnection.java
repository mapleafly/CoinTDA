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
package org.cointda.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xuelf
 */
@Slf4j
public class DruidConnection {
    private static Properties properties = null;
    private static DruidDataSource dataSource = null;
    private static volatile DruidConnection instatce = null;

    static {
        try {
            // 设定为jar包的绝对路径 在IDE中运行时为project的绝对路径
            String filePath = System.getProperty("user.dir");
            InputStream ipstream =
                new BufferedInputStream(new FileInputStream(filePath + "/conf/druid.properties"));
            properties = new Properties();
            properties.load(ipstream);
            dataSource = getDatasource();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private DruidPooledConnection connection = null;

    // 私有构造函数,防止实例化对象
    private DruidConnection() {
    }

    /**
     * @Description: 用简单单例模式确保只返回一个链接对象
     * @return: org.cointda.pool.DruidConnection
     * @author: mapleaf
     * @date: 2020/6/23 18:40
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

    // 加载数据源
    private static DruidDataSource getDatasource() {
        DruidDataSource source = null;
        try {
            source = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return source;
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
            log.error(e.getMessage());
        }
        return connection;
    }
}
