/**
 * @Title: GetProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.executor.proxy;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import org.apache.hadoop.hbase.client.Get;

/**
 * @ClassName: GetProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class GetProxy implements IExecutorProxy {

    private Get get;

    public GetProxy(Get get) {
        this.get = get;
    }

    public GetProxy(byte[] rowkey) {
        this.get = new Get(rowkey);
    }

    @Override
    public void addElement(ColumnProxy element) {

    }

    @Override
    public Get getOperation() {
        return get;
    }
}
