package com.chenwen.juc;

/**
 * @author chen.jw
 * @date 2021/8/31 17:58
 */
public class Worker implements Runnable{
    private Thread thread;

    Worker(){
        this.thread= new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("aaaaa");
    }


    public static void main(String[] args) {

        Worker worker = new Worker();
        worker.thread.start();
    }
}
