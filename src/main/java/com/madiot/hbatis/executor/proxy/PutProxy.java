/**
 * @Title: PutProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.executor.proxy;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import org.apache.hadoop.hbase.client.Put;

/**
 * @ClassName: PutProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class PutProxy implements IExecutorProxy {

    private Put put;

    public PutProxy(Put put) {
        this.put = put;
    }

    public PutProxy(byte[] rowKey) {
        this.put = new Put(rowKey);
    }

    public PutProxy(byte[] rowKey, long ls) {
        this.put = new Put(rowKey, ls);
    }

    @Override
    public void addElement(ColumnProxy columnElement) {
        Long version = null;
        if (columnElement.getTimestamp() != null) {
            version = columnElement.getTimestamp().getValue();
        }
        if (version != null) {
            this.put.addColumn(columnElement.getColumnType().getFamilyBytes(), columnElement.getColumnType().getQualifierBytes(),
                    version, columnElement.getValue());
        } else {
            this.put.addColumn(columnElement.getColumnType().getFamilyBytes(), columnElement.getColumnType().getQualifierBytes(),
                    columnElement.getValue());
        }
    }

    @Override
    public Put getOperation() {
        return put;
    }
}
