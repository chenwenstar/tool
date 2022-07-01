package com.chenwen.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chen.jw
 * @date 2021/9/15 10:59
 */
public class ThreadLocalTest implements Runnable{
    private ThreadLocal<String> user=new ThreadLocal<>();
    private ThreadLocal<String> user1=new ThreadLocal<>();

    @Override
    public void run() {
        user.set("hello");
        user1.set("world");
        new User().sayHello();
        user.get();
    }

    public static class User{
        public void sayHello(){
            System.out.println();
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new ThreadLocalTest());
    }


}
