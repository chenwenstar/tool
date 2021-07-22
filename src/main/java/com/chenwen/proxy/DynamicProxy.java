package com.chenwen.proxy;

import com.chenwen.common.Hello;
import com.chenwen.common.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chen.jw
 * @date 2021/7/21 17:50
 */
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy------>start");
        Object invoke = method.invoke(target, args);
        System.out.println("proxy------>end");
        return invoke;
    }

    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy();
        dynamicProxy.setTarget(new HelloImpl());
        Hello o = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), dynamicProxy);
        Hello o1 = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), dynamicProxy);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass() == o1.getClass());
        for(Method method:o.getClass().getDeclaredMethods()){
            System.out.println(method);
        }
        o.sayHello();
    }
}
