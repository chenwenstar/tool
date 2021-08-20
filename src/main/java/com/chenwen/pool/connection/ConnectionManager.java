package com.chenwen.pool.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.jw
 * @date 2021/7/27 15:23
 */
public class ConnectionManager {
    private ConnectionPool pool;

    public ConnectionManager(DataSourceConfig config) {
        try {
            pool = new ConnectionPool(config);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectionIsLiveMonitor();
    }

    public Connection getConn(){
        try {
            return pool.getConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean close(Connection connection){
        return pool.close(connection);
    }

    private void connectionIsLiveMonitor() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("后台运行检测");
                }
            }
        });
        thread.setName("poll monitor");
        thread.start();
    }




}
