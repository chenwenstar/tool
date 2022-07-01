package com.chenwen.pool.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * 复用同一个实例下 数据库连接
 * @author chen.jw
 * @date 2022/7/1 10:31
 */
public class HikariTest {
    private static final String URL="jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static void main(String[] args) throws InterruptedException {
        HikariConfig config=new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(URL);
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(3);
        config.setUsername("root");
        config.setPassword("123456789");
        config.setAutoCommit(true);

        HikariDataSource dataSource=new HikariDataSource(config);
        IntStream.range(0,10).forEach(x->{
            new Thread(()->{
                try {
                    Connection connection = dataSource.getConnection();
                    connection.setCatalog("test");
                    connection.prepareStatement("insert into user (username) values(\"test\")").execute();
                    connection.setCatalog("test1");
                    connection.prepareStatement("insert into user (username) values(\"test\")").execute();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }).start();
        });
        new CountDownLatch(1).await();
    }
}
