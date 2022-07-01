package com.chenwen.common;

import java.util.stream.IntStream;

/**
 * @author chen.jw
 * @date 2021/8/20 17:45
 */
public class AddFunctionTest {
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(x -> {
            add();
        });
    }

    public static void add() {
        AddFunction addFunction = (c, d) -> {
            return c + d;
        };

        int add = addFunction.add(2, 3);
        System.out.println(addFunction.getClass());
    }
}
