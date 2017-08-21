/**
 * @Title: ScanProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.executor.proxy;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import org.apache.hadoop.hbase.client.Operation;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName: ScanProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class ScanProxy implements IExecutorProxy {

    private Scan scan;

    public ScanProxy(Integer batch, Integer caching) {
        this.scan = new Scan();
        if (batch != null) {
            scan.setBatch(batch);
        }
        if (caching != null) {
            scan.setCaching(caching);
        }
    }

    public ScanProxy(Scan scan) {
        this.scan = scan;
    }

    @Override
    public void addElement(ColumnProxy element) {
        if (element != null && element.getColumnType() != null && element.getColumnType().getQualifier() != null) {
            this.scan.addColumn(element.getColumnType().getFamilyBytes(), element.getColumnType().getQualifierBytes());
        } else {
            this.scan.addFamily(element.getColumnType().getFamilyBytes());
        }
    }

    @Override
    public Scan getOperation() {
        return scan;
    }

    public void setLimit(byte[] startRow, byte[] stopRow) {
        if (startRow != null && startRow.length > 0) {
            scan.setStartRow(startRow);
        }
        if (stopRow != null && stopRow.length > 0) {
            scan.setStopRow(stopRow);
        }
    }

    public void addFilterList(FilterList filterList) {
        this.scan.setFilter(filterList);
    }

    public void addFilter(Filter filter) {
        this.scan.setFilter(filter);
    }
}
