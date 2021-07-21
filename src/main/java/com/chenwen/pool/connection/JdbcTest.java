package com.chenwen.pool.connection;

import org.apache.commons.lang3.time.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chen.jw
 * @date 2021/6/25 14:19
 */
public class JdbcTest {
    private static final String URL="jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME="root";
    private static final String PWD="123456789";

    static void connect(String sql){
        try {
            //类加载
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Connection connection = DriverManager.getConnection(URL, USERNAME, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            stopWatch.stop();
            System.out.println(stopWatch.getTime());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JdbcTest.connect("insert into user (username) values(\"test\")");
    }

}
