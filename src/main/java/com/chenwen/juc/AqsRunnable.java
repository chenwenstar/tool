package com.chenwen.juc;

/**
 * @author chen.jw
 * @date 2021/6/23 18:34
 */
public class AqsRunnable implements Runnable {
    private final static AqsLock AQS_LOCK = new AqsLock();

    private static int state = 0;

    public void run() {
        AQS_LOCK.lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程:" + Thread.currentThread() + ":" + state++);
        AQS_LOCK.unLock();
    }
}
