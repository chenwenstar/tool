package com.chenwen.pool.connection;

import com.mysql.cj.jdbc.ConnectionImpl;

/**
 * mysql
 * @author chen.jw
 * @date 2021/8/9 11:26
 */
public class Connection extends ConnectionImpl {
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
