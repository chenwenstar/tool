package com.chenwen.pool.connection;

import lombok.Data;

/**
 * @author chen.jw
 * @date 2021/7/27 14:23
 */
@Data
public class DataSourceConfig {
    private String url;
    private String username;
    private String pwd;
    private String driverName;
    private boolean ifAutoCommit;
    private int minConnectionNum;
    private int maxConnectionNum;
    private long time;
}
