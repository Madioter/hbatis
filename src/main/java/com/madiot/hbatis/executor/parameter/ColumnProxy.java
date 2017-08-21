/**
 * @Title: ColumnElement.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.executor.parameter;

import com.madiot.hbatis.type.ColumnType;

/**
 * @ClassName: ColumnElement
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class ColumnProxy {

    private ParamProxy paramProxy;

    private VersionProxy timestamp;

    private ColumnType columnType;

    public ColumnProxy(ParamProxy paramProxy, VersionProxy timestamp, ColumnType columnType) {
        this.paramProxy = paramProxy;
        this.timestamp = timestamp;
        this.columnType = columnType;
    }

    public ColumnProxy(ParamProxy paramProxy, ColumnType columnType) {
        this.paramProxy = paramProxy;
        this.columnType = columnType;
    }

    public byte[] getValue() {
        return paramProxy.getByteArray(null);
    }

    public VersionProxy getTimestamp() {
        return timestamp;
    }

    public ColumnType getColumnType() {
        return columnType;
    }
}
