package com.chenwen.common;

/**
 * @author chen.jw
 * @date 2021/8/20 17:45
 */
public class AddFunctionTest {
    public static void main(String[] args) {

        AddFunction addFunction = (c, d) -> {
            return c + d;
        };
        int add = addFunction.add(2, 3);
        System.out.println(add);
    }


}
