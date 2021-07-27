package com.chenwen.pool.connection;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.jw
 * @date 2021/7/27 15:23
 */
public class ConnectionManager {
    private static ConnectionPool pool;

    public ConnectionManager(DataSourceConfig config) {
        if (null == pool) {
            synchronized (this) {
                if (null == pool) {
                    try {
                        pool = new ConnectionPool(config);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    connectionIsLiveMonitor();
                }
            }
        }
    }

    private void connectionIsLiveMonitor() {
        new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
