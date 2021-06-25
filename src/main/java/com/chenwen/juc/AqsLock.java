package com.chenwen.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chen.jw
 * @date 2021/6/23 18:07
 */
public class AqsLock extends AbstractQueuedSynchronizer {
    private volatile Thread currentThread;


    public AqsLock() {
        setState(0);
    }

    @Override
    public boolean tryAcquire(int arg) {
        boolean b = compareAndSetState(0, 1);
        if (b) {
            currentThread = Thread.currentThread();
        }
        return b;
    }

    public void lock() {
        acquire(1);
    }

    @Override
    public boolean tryRelease(int arg) {
        if (ifCurrentThread() && compareAndSetState(1, arg)) {
            currentThread = null;
            return true;
        }
        return false;
    }

    public void unLock() {
        release(0);
    }


    private boolean ifCurrentThread() {
        if (null != currentThread) {
            return Thread.currentThread().equals(currentThread);
        }
        return false;
    }
}
