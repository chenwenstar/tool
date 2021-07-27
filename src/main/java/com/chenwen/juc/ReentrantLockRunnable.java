package com.chenwen.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen.jw
 * @date 2021/6/24 16:39
 */
public class ReentrantLockRunnable implements Runnable {
    private final static ReentrantLock reentrantlock=new ReentrantLock();

    private static int state=0;

    @Override
    public void run() {
        reentrantlock.lock();
        try {
            Thread.sleep(100);
            System.out.println("当前线程:" + Thread.currentThread() + ":" + state++);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantlock.unlock();
        }
    }
}
