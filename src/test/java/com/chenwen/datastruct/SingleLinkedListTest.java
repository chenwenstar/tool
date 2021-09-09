package com.chenwen.datastruct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * @author chen.jw
 * @date 2021/9/9 16:08
 */
public class SingleLinkedListTest {
    static SingleLinkedList<String> linkedList;

    static {
        linkedList = new SingleLinkedList<>();
    }

    @Test
    @Order(1)
    void addNodes() {
        for (int i = 0; i < 10000; i++) {
            linkedList.addNode(String.valueOf(i));
        }
        Assertions.assertEquals(10000, linkedList.getSize());
    }

    @Test
    void removeNode() {
        String object = "9997";
        boolean ifSuccess = linkedList.removeNode(object);
        System.out.println(ifSuccess);

    }
}
