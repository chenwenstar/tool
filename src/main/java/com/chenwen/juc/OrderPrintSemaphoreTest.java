package com.chenwen.juc;

import java.util.concurrent.Semaphore;

/**
 * @author chen.jw
 * @date 2021/8/31 15:45
 */
public class OrderPrintSemaphoreTest {
    static volatile boolean state = true;

    public static void main(String[] args) throws InterruptedException {
        final Semaphore as = new Semaphore(1);
        final Semaphore bs= new Semaphore(1);
        final Semaphore cs = new Semaphore(1);
        Thread a = new Thread(() -> {
            while (state) {
                try {
                    as.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                bs.release();
            }
        });

        Thread b = new Thread(() -> {
            while (state) {
                try {
                    bs.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                cs.release();
            }
        });

        Thread c = new Thread(() -> {
            while (state) {
                try {
                    cs.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
                as.release();
            }
        });
        a.start();
        bs.acquire();
        cs.acquire();
        b.start();
        c.start();

    }
}
