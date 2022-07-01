package com.chenwen.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.jw
 * @date 2021/8/2 10:47
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize) {
        super(corePoolSize, maxPoolSize, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "thread" + r.hashCode());
            }
        });
    }
}
