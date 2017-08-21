/**
 * @Title: DataSourceFactory.java
 * @Package com.madiot.hbatis.datasource
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.datasource;

import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * @ClassName: DataSourceFactory
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public interface TableFactory {

    public Table getTable(String tableName);
}
