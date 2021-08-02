package com.chenwen.pool.connection;

/**
 * @author chen.jw
 * @date 2021/7/27 18:40
 */
public class PoolTest {
    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPwd("123456789");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setMinConnectionNum(3);
        dataSourceConfig.setMaxConnectionNum(10);
        ConnectionManager connectionManager = new ConnectionManager(dataSourceConfig);
    }
}
