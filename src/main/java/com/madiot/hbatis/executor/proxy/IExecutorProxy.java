/**
 * @Title: IExecutorProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.executor.proxy;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import org.apache.hadoop.hbase.client.Operation;

/**
 * @ClassName: IExecutorProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public interface IExecutorProxy {
    void addElement(ColumnProxy element);

    Operation getOperation();
}
