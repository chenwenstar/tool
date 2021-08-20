package com.chenwen.pool.connection;

import com.chenwen.juc.MyThreadPoolExecutor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.jw
 * @date 2021/7/27 18:40
 */
@Slf4j
public class PoolTest implements Runnable {
    static ConnectionManager connectionManager;
    final static DataSourceConfig dataSourceConfig = new DataSourceConfig();

    static {
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPwd("123456789");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setAutoCommit(true);
        dataSourceConfig.setMinConnectionNum(3);
        dataSourceConfig.setMaxConnectionNum(10);
    }

    @Override
    public void run() {
        Connection conn = connectionManager.getConn();
        try {

            PreparedStatement preparedStatement = conn.prepareStatement("insert into user (username) values(\"test\")");
            log.info(Thread.currentThread().getName());
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
            TimeUnit.SECONDS.sleep(5);
            connectionManager.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        connectionManager = new ConnectionManager(dataSourceConfig);
        for (int i = 0; i < 100; i++) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new PoolTest()).start();
        }
    }


}
