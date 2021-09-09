package com.chenwen.datastruct;

import lombok.Data;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 单链表
 *
 * @author chen.jw
 * @date 2021/9/9 14:35
 */
@Data
public class SingleLinkedList<T> {
    /**
     * header of list
     */
    private LinkedNode<T> header;
    /**
     * size
     */
    private int size;

    /**
     * header insert
     */
    public void addNode(T object) {
        // first node is null
        if (null == this.header) {
            this.header = new LinkedNode<>(object, null);
        }
        this.header = new LinkedNode<>(object, this.header);
        size++;
    }

    /**
     * get last
     *
     * @return last node
     */
    public LinkedNode getLastNode() {
        LinkedNode node = this.header.next;
        if (null == node) {
            return this.header;
        }
        while (null != node.getNext()) {
            node = node.next;
        }
        return node;
    }

    /**
     * remove
     */
    public boolean removeNode(T object) {
        LinkedNode node = this.header;
        LinkedNode preNode = node;
        while (null != node) {
            Object data = node.getData();
            if (object.hashCode() == data.hashCode() && data.equals(object)) {
                preNode.next = node.next;
                node = null;
                size--;
                return true;
            }
            preNode = node;
            node = node.next;
        }
        return false;
    }

    /**
     * if exit
     *
     * @param object
     * @return true or false
     */
    public boolean ifNodeExit(T object) {
        return true;
    }

    @Data
    private static class LinkedNode<T> {
        private T data;
        private LinkedNode next;

        public LinkedNode(T object) {
            this.data = object;
        }

        public LinkedNode(T object, LinkedNode node) {
            this.data = object;
            this.next = node;
        }
    }
}
