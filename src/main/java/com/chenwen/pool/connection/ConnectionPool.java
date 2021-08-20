package com.chenwen.pool.connection;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen.jw
 * @date 2021/7/27 14:22
 */
@Slf4j
public class ConnectionPool {
    private static final int DEFAULT_MIN_CONNECTION_NUM = 2;
    private static final int DEFAULT_MAX_CONNECTION_NUM = 6;
    private DataSourceConfig dataSourceConfig;
    //队列
    private BlockingQueue<Connection> connectionQueue;
    /**
     * 1: running 运行
     * 2: growing 扩容
     * 3: shutting 关闭
     **/
    public AtomicInteger status = new AtomicInteger();

    public ConnectionPool(DataSourceConfig dataSourceConfig) throws SQLException, ClassNotFoundException, InterruptedException {
        this.dataSourceConfig = dataSourceConfig;
        connectionQueue = new ArrayBlockingQueue<>(dataSourceConfig.getMaxConnectionNum());
        init();
    }

    /**
     * 初始化连接
     */
    private void init() throws ClassNotFoundException, SQLException, InterruptedException {
        //类加载
        Class.forName(dataSourceConfig.getDriverName());
        Connection connection;
        for (int i = 0; i < dataSourceConfig.getMinConnectionNum(); i++) {
            connection = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPwd());
            connection.setAutoCommit(dataSourceConfig.isAutoCommit());
            connectionQueue.put(connection);
        }
        log.info("create conn count:{}", dataSourceConfig.getMinConnectionNum());
        status.compareAndSet(0, 1);
    }

    /**
     * 检验参数合法性
     */
    private void verifyDataSourceParam() {
        if (dataSourceConfig.getMinConnectionNum() < 0 || dataSourceConfig.getMaxConnectionNum() < 0) {
            throw new RuntimeException("error param");
        }
    }

    public Connection getConnection() throws InterruptedException {
        return connectionQueue.take();
    }

    public boolean close(Connection connection){
        return connectionQueue.add(connection);
    }
}
