/**
 * @Title: RowFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.comparison;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.BaseFilterHandler;
import com.madiot.hbatis.filter.FilterConfigException;
import com.madiot.hbatis.type.ColumnType;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;

import java.util.Map;

/**
 * @ClassName: RowFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class RowFilterHandler extends BaseComparisonFilterHandler {
    @Override
    public Filter getCurrentFilter(ColumnType columnType, CompareFilter.CompareOp compareFilterOp, Comparable comparator, Map<String, ParamProxy> params) {
        if (compareFilterOp == null) {
            throw new FilterConfigException("RowFilter config error, cause: compareFilterOp can't be null");
        }
        if (comparator instanceof ByteArrayComparable) {
            return new RowFilter(compareFilterOp, (ByteArrayComparable) comparator);
        } else {
            throw new FilterConfigException("RowFilter config error, cause: comparator type must be ByteArrayComparable");
        }
    }
}
