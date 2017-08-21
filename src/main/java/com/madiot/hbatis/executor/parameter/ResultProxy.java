/**
 * @Title: CellProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.executor.parameter;

import com.madiot.hbatis.type.ColumnType;
import org.apache.hadoop.hbase.Cell;

import java.util.Map;

/**
 * @ClassName: CellProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ResultProxy {

    private Map<String, Cell> cells;

    public ResultProxy() {

    }


    public byte[] getValue(ColumnType columnType) {
        return null;
    }
}
