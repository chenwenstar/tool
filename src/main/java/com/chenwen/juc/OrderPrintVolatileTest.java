package com.chenwen.juc;

/**
 * @author chen.jw
 * @date 2021/8/31 11:33
 */
public class OrderPrintVolatileTest {
    static volatile boolean state=true;
    static volatile int order=0;

    public static void main(String[] args) throws InterruptedException {
        Thread c=new Thread(()->{
            while(state){
                if(order%3==2){
                    System.out.println("C----"+order);
                    order++;
                }
            }
        });

        Thread b=new Thread(()->{
            while(state){
                if(order%3==1){
                    System.out.println("B----"+order);
                    order++;
                }
            }
        });

        Thread a=new Thread(()->{
            while(state){
                if(order%3==0){
                    System.out.println("A----"+order);
                    order++;
                }
            }
        });
        a.start();
        b.start();
        c.start();
    }
}
