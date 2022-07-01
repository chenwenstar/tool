package com.chenwen.pool.connection;

import com.chenwen.juc.MyThreadPoolExecutor;
import com.chenwen.juc.ThreadLocalTest;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chen.jw
 * @date 2021/9/15 17:16
 */
public class JdbcTransactionTest implements Runnable{
    private static final String URL="jdbc:mysql://localhost:3306/blog?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME="root";
    private static final String PWD="123456789";


    private String sql;

    public JdbcTransactionTest(String sql) {
        this.sql = sql;
    }

    @Override
    public void run() {
        connect(sql);
    }

    void connect(String sql){
        try {
            //类加载
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PWD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            String sql1="insert log (name,buy_num)values(\""+Thread.currentThread().getName()+"\",1 )";
            connection.prepareStatement(sql1).execute();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
            if(i!=1){
                throw new SQLException("库存不足");
            }
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(20, 20);
        StringBuilder sql=new StringBuilder("update book set stock=stock-1 where id=1 and stock>0");
        for(int i=0;i<1000;i++){
            myThreadPoolExecutor.execute(new JdbcTransactionTest(sql.toString()));
        }
    }


}
