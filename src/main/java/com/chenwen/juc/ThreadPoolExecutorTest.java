package com.chenwen.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.jw
 * @date 2021/6/24 17:59
 */
public class ThreadPoolExecutorTest {
    private static final int TOTAL = 100;

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000));
        for (int i = 0; i < TOTAL; i++) {
            threadPoolExecutor.execute(new AqsRunnable());
        }
        System.out.println("---------------");
        threadPoolExecutor.shutdown();
    }
}
